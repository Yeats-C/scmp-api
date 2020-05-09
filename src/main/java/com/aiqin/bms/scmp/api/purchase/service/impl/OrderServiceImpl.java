package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuCheckoutDao;
import com.aiqin.bms.scmp.api.product.domain.converter.order.OrderToOutBoundConverter;
import com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoDTO;
import com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoItemDTO;
import com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoItemProductBatchDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckout;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundCallBackDetailRequest;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundCallBackRequest;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.service.OutboundService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoLog;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderInfoRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderListRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryOrderProductListRespVO;
import com.aiqin.bms.scmp.api.purchase.domain.response.order.QueryProductUniqueCodeListRespVO;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemProductBatchMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoLogMapper;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoMapper;
import com.aiqin.bms.scmp.api.purchase.service.OrderCallbackService;
import com.aiqin.bms.scmp.api.purchase.service.OrderService;
import com.aiqin.bms.scmp.api.util.*;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-13
 * @time: 17:36
 */
@Service
@Slf4j
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderInfoItemMapper orderInfoItemMapper;
    @Autowired
    private OrderInfoLogMapper orderInfoLogMapper;
    @Autowired
    private OutboundService outboundService;
    @Autowired
    private StockService stockService;
    @Autowired
    private OrderInfoItemProductBatchMapper orderInfoItemProductBatchMapper;
    @Autowired
    private ProductSkuCheckoutDao productSkuCheckoutDao;
    @Autowired
    private OrderCallbackService orderCallbackService;
    @Autowired
    private UrlConfig urlConfig;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(List<OrderInfoReqVO> reqVOs){
        //校验
        validateOrderData(reqVOs);
        Date date = new Date();
        //数据处理
        List<OrderInfoItem> orderItems = Lists.newCopyOnWriteArrayList();
        List<OrderInfoLog> logs = Lists.newCopyOnWriteArrayList();
        List<OrderInfo> orders = Lists.newCopyOnWriteArrayList();
        reqVOs.parallelStream().forEach(o->{
            OrderInfo info = BeanCopyUtils.copy(o, OrderInfo.class);
//            info.setCreateDate(date);
//            info.setOperator(CommonConstant.SYSTEM_AUTO);
//            info.setOperatorCode(CommonConstant.SYSTEM_AUTO_CODE);
//            info.setOperatorTime(date);
            orders.add(info);
            List<OrderInfoItem> orderItem = BeanCopyUtils.copyList(o.getProductList(), OrderInfoItem.class);
            orderItems.addAll(orderItem);
            //拼装日志信息
            OrderInfoLog log = new OrderInfoLog(null,info.getOrderCode(),info.getOrderStatus(), OrderStatus.getAllStatus().get(info.getOrderStatus()).getBackgroundOrderStatus(),OrderStatus.getAllStatus().get(info.getOrderStatus()).getStandardDescription(),null,info.getOperator(),date,info.getCompanyCode(),info.getCompanyName());
            logs.add(log);
        });
        //保存
        saveData(orderItems, orders);
        //存日志
        saveLog(logs);
        //异步调用库房接口推送订单信息
        OrderServiceImpl service = (OrderServiceImpl) AopContext.currentProxy();
        service.lockBatchStock(orders,orderItems,service);
        return true;
    }

    //@Async("myTaskAsyncPool")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendOrderToOutBound(List<OrderInfoDTO> dtos) {
        log.info("生成出库单信息参数：{}" + dtos);
        // 调用库房接口传出库单
        Date date = new Date();
        List<OutboundReqVo> outboundReqVoList = new OrderToOutBoundConverter().convert(dtos);
        Boolean b = outboundService.saveList(outboundReqVoList);
        //根据返回结果得到对应的状态
        Integer status = b?OrderStatus.WAITING_FOR_PICKING.getStatusCode():OrderStatus.WAITING_FOR_PICKING_FAILED.getStatusCode();
        List<OrderInfoLog> logs = Lists.newCopyOnWriteArrayList();
        //修改订单状态
        dtos.forEach(dto -> {
            ChangeOrderStatusReqVO changeOrderStatusReqVO = new ChangeOrderStatusReqVO();
            changeOrderStatusReqVO.setOrderStatus(status);
            changeOrderStatusReqVO.setOrderCode(dto.getOrderCode());
            changeOrderStatusReqVO.setOperator(CommonConstant.SYSTEM_AUTO);
            changeOrderStatusReqVO.setOperatorCode(CommonConstant.SYSTEM_AUTO_CODE);
            changeStatus(changeOrderStatusReqVO);
            OrderInfoLog log = new OrderInfoLog(null, dto.getOrderCode(), status, OrderStatus.getAllStatus().get(status).getBackgroundOrderStatus(), OrderStatus.getAllStatus().get(status).getStandardDescription(), b ? null : CommonConstant.CREATE_OUTBOUND_FAILED, changeOrderStatusReqVO.getOperator(), date, dto.getCompanyCode(), dto.getCompanyName());
            logs.add(log);
        });
        //存日志
        saveLog(logs);
    }

    /**
     * 数据处理
     * @author NullPointException
     * @date 2019/6/25
     * @param orders
     * @param orderItems
     * @param list
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoDTO>
     */
    private List<OrderInfoDTO> dealToOutBoundData(List<OrderInfo> orders, List<OrderInfoItem> orderItems, List<OrderInfoItemProductBatch> list) {
        List<OrderInfoDTO> dtos = Lists.newArrayList();
        orders.forEach(
                o -> {
                    OrderInfoDTO copy = BeanCopyUtils.copy(o, OrderInfoDTO.class);
                    List<OrderInfoItemProductBatchDTO> dtoList = BeanCopyUtils.copyList(list, OrderInfoItemProductBatchDTO.class);
                    List<OrderInfoItemDTO> itemDTOList = BeanCopyUtils.copyList(list, OrderInfoItemDTO.class);
                    copy.setBatchList(dtoList.stream().filter(i -> i.getOrderCode().equals(o.getOrderCode())).collect(Collectors.toList()));
                    copy.setItemList(itemDTOList.stream().filter(i -> i.getOrderCode().equals(o.getOrderCode())).collect(Collectors.toList()));
                    dtos.add(copy);
                }
        );
        return dtos;
    }

    @Override
    @Async("myTaskAsyncPool")
    @Transactional(rollbackFor = Exception.class)
    public void lockBatchStock(List<OrderInfo> orders, List<OrderInfoItem> orderItems,OrderServiceImpl service) {
        //TODO 调用库存接口锁库
        List<LockOrderItemBatchReqVO> vo = dealData(orders,orderItems);
        List<OrderInfoItemProductBatch> list = stockService.lockBatchStock(vo);
        if(CollectionUtils.isEmptyCollection(list)){
            throw new BizException(ResultCode.LOCK_BATCH_STOCK_FAILED);
        }
        saveLockBatch(list);
        List<OrderInfoDTO> orderInfoDTOS = dealToOutBoundData(orders, orderItems, list);
        service.sendOrderToOutBound(orderInfoDTOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLockBatch(List<OrderInfoItemProductBatch> list) {
        if(CollectionUtils.isEmptyCollection(list)){
            return;
        }
        int i = orderInfoItemProductBatchMapper.insertBatch(list);
        if(i!=list.size()){
            throw new BizException(ResultCode.SAVE_LOCK_BATCH_FAILED);
        }
    }
    /**
     * 拼装锁库数据
     * @author NullPointException
     * @date 2019/6/21
     * @param orders
     * @param orderItems
     * @return void
     */
    private List<LockOrderItemBatchReqVO> dealData(List<OrderInfo> orders, List<OrderInfoItem> orderItems) {
        Map<String, OrderInfo> collect = orders.stream().collect(Collectors.toMap(OrderInfo::getOrderCode, Function.identity()));
        List<LockOrderItemBatchReqVO> vos = Lists.newArrayList();
        for (OrderInfoItem orderItem : orderItems) {
            LockOrderItemBatchReqVO copy = BeanCopyUtils.copy(orderItem, LockOrderItemBatchReqVO.class);
            copy.setOriginalLineNum(orderItem.getProductLineNum());
            copy.setProductLineNum(null);
            copy.setTransportCenterCode(collect.get(orderItem.getOrderCode()).getTransportCenterCode());
            copy.setTransportCenterName(collect.get(orderItem.getOrderCode()).getTransportCenterName());
            copy.setWarehouseCode(collect.get(orderItem.getOrderCode()).getWarehouseCode());
            copy.setWarehouseName(collect.get(orderItem.getOrderCode()).getWarehouseName());
            vos.add(copy);
        }
        return vos;
    }

    @Override
    public void saveLog(List<OrderInfoLog> logs) {
        if(CollectionUtils.isEmptyCollection(logs)){
            return;
        }
        int i = orderInfoLogMapper.insertBatch(logs);
        if (i != logs.size()) {
            log.info("需要插入订单日志条数[{}]，实际插入订单日志的条数：[{}]",logs.size(),i);
            throw new BizException(ResultCode.LOG_SAVE_ERROR);
        }
    }

    /**
     * 校验参数
     * @author NullPointException
     * @date 2019/6/14
     * @param reqVOs
     * @return void
     */
    private void validateOrderData(List<OrderInfoReqVO> reqVOs) {
        //TODO 这里需要参数校验

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveData(List<OrderInfoItem> infoItems, List<OrderInfo> info){
        int insert = orderInfoMapper.insertBatch(info);
        if (insert != info.size()) {
            log.error("订单主表插入影响条数：[{}]", insert);
            throw new BizException(ResultCode.ORDER_SAVE_FAILURE);
        }
        int i = orderInfoItemMapper.insertBatch(infoItems);
        if(i!=infoItems.size()){
            log.error("订单附表插入影响条数：[{}]", insert);
            throw new BizException(ResultCode.ORDER_SAVE_FAILURE);
        }
    }

    @Override
    public BasePage<QueryOrderListRespVO> list(QueryOrderListReqVO reqVO) {
        reqVO.setCompanyCode(getUser().getCompanyCode());
        PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
        List<QueryOrderListRespVO> list = orderInfoMapper.selectListByQueryVO(reqVO);
        return PageUtil.getPageList(reqVO.getPageNo(),list);
    }

    @Override
    public QueryOrderInfoRespVO view(String orderCode) {
        return orderInfoMapper.selectByOrderCode(orderCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeStatus(ChangeOrderStatusReqVO reqVO) {
        Date date = new Date();
        //先查后改
        OrderInfo order = orderInfoMapper.selectByOrderCode2(reqVO.getOrderCode());
        if (Objects.isNull(order)) {
            throw new BizException(ResultCode.CAN_NOT_FIND_ORDER);
        }
        //校验
        order.setOrderStatus(reqVO.getOrderStatus());
        order.setOperator(reqVO.getOperator());
        order.setOperatorCode(reqVO.getOperatorCode());
        order.setOperatorTime(date);
        order.setRemake(reqVO.getRemark());
        order.setDeliveryTime(reqVO.getDeliveryTime());
        //更新
        updateByOrderCode(order);
        //存日志
        OrderInfoLog log = new OrderInfoLog(null,reqVO.getOrderCode(),reqVO.getOrderStatus(), OrderStatus.getAllStatus().get(reqVO.getOrderStatus()).getBackgroundOrderStatus(),OrderStatus.getAllStatus().get(reqVO.getOrderStatus()).getStandardDescription(),null,reqVO.getOperator(),date,order.getCompanyCode(),order.getCompanyName());
        List<OrderInfoLog> logs = Lists.newArrayList();
        logs.add(log);
        saveLog(logs);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByOrderCode(OrderInfo order) {
        int i = orderInfoMapper.updateByOrderCode(order);
        if(i<1){
            throw new BizException(ResultCode.UPDATE_ORDER_STATUS_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean distribution(String orderCode,Integer status) {
        ChangeOrderStatusReqVO reqVO = new ChangeOrderStatusReqVO();
        reqVO.setOperator(getUser().getPersonName());
        reqVO.setOperatorCode(getUser().getPersonId());
        reqVO.setOrderCode(orderCode);
        reqVO.setOrderStatus(status);
        Boolean flag = changeStatus(reqVO);
        if(flag && Objects.equals(status,ReturnOrderStatus.RETURN_ABNORMALLY_TERMINATED.getStatusCode())){
           updateAiqinOrder(orderCode,null);
        }
        return flag;
    }

    @Override
    public void sendToSettlement() {

    }

    @Override
    public void sendStatusToSettlement() {

    }

    @Override
    public BasePage<QueryOrderProductListRespVO> orderProductList(QueryOrderProductListReqVO reqVO) {
        PageHelper.startPage(reqVO.getPageNo(),reqVO.getPageSize());
        reqVO.setCompanyCode(getUser().getCompanyCode());
        List<QueryOrderProductListRespVO> list = orderInfoItemMapper.selectOrderProductList(reqVO);
        return PageUtil.getPageList(reqVO.getPageNo(),list);
    }

    @Override
    public BasePage<QueryProductUniqueCodeListRespVO> productUniqueCodeList(QueryProductUniqueCodeListReqVO reqVO) {
        PageHelper.startPage(reqVO.getPageNo(),reqVO.getPageSize());
        reqVO.setCompanyCode(getUser().getCompanyCode());
        List<QueryOrderProductListRespVO> list = orderInfoItemMapper.selectproductUniqueCodeList(reqVO);
        return PageUtil.getPageList(reqVO.getPageNo(),list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delivery(DeliveryReqVO reqVO) {
        if(null == reqVO){
            throw new BizException("参数信息错误");
        }
        if(StringUtils.isBlank(reqVO.getOrderCode())){
            throw new BizException("订单编号不能为空");
        }
        if(CollectionUtils.isEmptyCollection(reqVO.getItemList())){
            throw new BizException("发货明细不能为空");
        }
        if(null == reqVO.getDeliverAmount()){
            throw new BizException("运费信息不能为空");
        }
        ChangeOrderStatusReqVO changeOrderStatusReqVO = new ChangeOrderStatusReqVO();
        changeOrderStatusReqVO.setOperator(getUser().getPersonName());
        changeOrderStatusReqVO.setOperatorCode(getUser().getPersonId());
        changeOrderStatusReqVO.setOrderCode(reqVO.getOrderCode());
        changeOrderStatusReqVO.setOrderStatus(ReturnOrderStatus.RETURN_COMPLETED.getStatusCode());
        //更新状态
        changeStatus(changeOrderStatusReqVO);
        //更新运费信息
        orderInfoMapper.updateDevliverAmountByOrderCode(reqVO.getOrderCode(),reqVO.getDeliverAmount());
        //更新发货数量
        int i = orderInfoItemMapper.updateBatchNumById(reqVO.getItemList());
        updateAiqinOrder(reqVO.getOrderCode(),reqVO.getItemList().stream().map(DeliveryItemReqVo :: getId).distinct().collect(Collectors.toList()));
        return Boolean.TRUE;
    }

    private void updateAiqinOrder(String orderCode, List<Long> itemIds) {
        //根据订单编码获取最新的订单信息
        OrderInfo orderInfo = orderInfoMapper.selectByOrderCode2(orderCode);
        if(null == orderCode){
            throw new BizException(ResultCode.CAN_NOT_FIND_ORDER);
        }
        if(Objects.equals(orderInfo.getOrderTypeCode(),OrderType.DIRECT_DELIVERY.getNum()) ||
                Objects.equals(orderInfo.getOrderTypeCode(),OrderType.DIRECT_DELIVERY_FUCAI.getNum())) {
            OutboundCallBackRequest request = new OutboundCallBackRequest();
            request.setOderCode(orderCode);
            request.setOrderStatus(orderInfo.getOrderStatus());
            request.setOrderTypeCode(orderInfo.getOrderTypeCode());
            request.setPersonId(orderInfo.getOperatorCode());
            request.setPersonName(orderInfo.getOperator());
            if (Objects.equals(orderInfo.getOrderStatus(), ReturnOrderStatus.RETURN_COMPLETED.getStatusCode())) {
                request.setDeliverAmount(orderInfo.getDeliverAmount());
                request.setDeliveryTime(new Date());
                request.setDeliveryPerson(orderInfo.getOperator());
            }
            if (CollectionUtils.isNotEmptyCollection(itemIds)) {
                List<OutboundCallBackDetailRequest> detailList = Lists.newArrayList();
                //根据订单明细Id查询
                List<OrderInfoItem> orderInfoItems = orderInfoItemMapper.selectByIds(itemIds);
                orderInfoItems.forEach(item -> {
                    OutboundCallBackDetailRequest detailRequest = new OutboundCallBackDetailRequest();
                    detailRequest.setActualProductCount(item.getActualDeliverNum());
                    detailRequest.setLineCode(item.getProductLineNum());
                    detailRequest.setSkuCode(item.getSkuCode());
                    detailRequest.setSkuName(item.getSkuName());
                    detailList.add(detailRequest);
                });
                request.setDetailList(detailList);
                long sum = 0L;
                for (OrderInfoItem orderInfoItem : orderInfoItems) {
                    long actualDeliverNum = 0L;
                    if (null != orderInfoItem.getActualDeliverNum()) {
                        actualDeliverNum = orderInfoItem.getActualDeliverNum();;
                    }
                    sum += actualDeliverNum;
                }
                request.setActualTotalCount(sum);
            }
            orderCallbackService.updateAiqinOrder(request);
        }
    }

    @Override
    public OrderInfo selectByOrderCode(String orderCode) {
        return orderInfoMapper.selectByOrderCode2(orderCode);
    }

    @Override
    //@Transactional(rollbackFor = Exception.class)
    public HttpResponse insertSaleOrder(ErpOrderInfo request) {
        if (null == request) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 获取税率
        String key;
        Map<String, ProductSkuCheckout> product = new HashMap<>();
        for (ErpOrderItem detail : request.getItemList()) {
            key = String.format("%s", detail.getSkuCode());
            if (product.get(key) == null) {
                product.put(key, productSkuCheckoutDao.getInfo(detail.getSkuCode()));
            }
        }
        for (ErpOrderItem detail : request.getItemList()) {
            // set税率
            key = String.format("%s", detail.getSkuCode());
            if(detail.getTaxRate() == null){
                ProductSkuCheckout item = product.get(key);
                if(item == null || item.getOutputTaxRate() == null){
                    detail.setTaxRate(BigDecimal.ZERO);
                }else {
                    detail.setTaxRate(item.getOutputTaxRate());
                }
            }
        }
        // 转换erp参数
        OrderInfoReqVO vo = this.orderInfoRequestVo(request);
        Date date = Calendar.getInstance().getTime();
        // 数据处理
        List<OrderInfoItem> orderItems = Lists.newCopyOnWriteArrayList();
        List<OrderInfoLog> logs = Lists.newCopyOnWriteArrayList();
        List<OrderInfo> orders = Lists.newCopyOnWriteArrayList();
        List<OrderInfoItemProductBatch> orderBtachs = Lists.newCopyOnWriteArrayList();
        OrderInfo info = BeanCopyUtils.copy(vo, OrderInfo.class);
        orders.add(info);
        List<OrderInfoItem> orderItem = BeanCopyUtils.copyList(vo.getProductList(), OrderInfoItem.class);
        orderItems.addAll(orderItem);
        List<OrderInfoItemProductBatch> infoBtach = BeanCopyUtils.copyList(vo.getItemBatchList(), OrderInfoItemProductBatch.class);
        orderBtachs.addAll(infoBtach);
        // 调用销售单生成出库单信息
        String insertOutbound = this.insertOutbound(vo);
        // 拼装日志信息
        if(vo.getOrderType() != null){
            OrderInfoLog log;
            if(vo.getOrderTypeCode().equals(1) || vo.getOrderTypeCode().equals(3)){
               log = new OrderInfoLog(null, info.getOrderCode(), OrderStatus.WAITING_FOR_DISTRIBUTION.getStatusCode(),
                       OrderStatus.WAITING_FOR_DISTRIBUTION.getBackgroundOrderStatus(),
                       OrderStatus.WAITING_FOR_DISTRIBUTION.getExplain(), OrderStatus.WAITING_FOR_DISTRIBUTION.getStandardDescription(),
                       info.getCreateByName(), date, info.getCompanyCode(), info.getCompanyName());
            }else{
               log = new OrderInfoLog(null, info.getOrderCode(), OrderStatus.WAITING_FOR_PICKING.getStatusCode(),
                       OrderStatus.WAITING_FOR_PICKING.getBackgroundOrderStatus(),
                       OrderStatus.WAITING_FOR_PICKING.getExplain(), OrderStatus.WAITING_FOR_PICKING.getStandardDescription(),
                        info.getCreateByName(), date, info.getCompanyCode(), info.getCompanyName());

               // 配送的情况下 调用wms
                SaleSourcInfoSource saleSourcInfoSource = insertWms(request,insertOutbound);
                String url = urlConfig.WMS_API_URL+"/sale/source/outbound";
                HttpClient httpClient = HttpClient.post(url).json(saleSourcInfoSource).timeout(200000);
                HttpResponse orderDto = httpClient.action().result(HttpResponse.class);
                if (!orderDto.getCode().equals(MessageId.SUCCESS_CODE)) {
                    return HttpResponse.failure(null, "调用wms失败,原因：" + orderDto.getMessage());
                }
            }
            logs.add(log);
        }
        // 保存订单和订单商品信息
        //saveData(orderItems, orders);
        saveDatas(orderItems, orders, orderBtachs);
        //存日志
        saveLog(logs);
        return HttpResponse.success();
    }

    private void saveDatas(List<OrderInfoItem> infoItems, List<OrderInfo> info, List<OrderInfoItemProductBatch> orderBtachs) {
        int insert = orderInfoMapper.insertBatch(info);
        if (insert != info.size()) {
            log.error("订单主表插入影响条数：[{}]", insert);
            throw new BizException(ResultCode.ORDER_SAVE_FAILURE);
        }
        int i = orderInfoItemMapper.insertBatch(infoItems);
        if(i!=infoItems.size()){
            log.error("订单附表插入影响条数：[{}]", insert);
            throw new BizException(ResultCode.ORDER_SAVE_FAILURE);
        }
        int i1 = orderInfoItemProductBatchMapper.insertBatch(orderBtachs);
        if(i!=infoItems.size()){
            log.error("订单批次附表插入影响条数：[{}]", insert);
            throw new BizException(ResultCode.ORDER_SAVE_FAILURE);
        }
    }

    // 将数据传给wms
    private SaleSourcInfoSource insertWms(ErpOrderInfo request,String insertOutbound) {
        SaleSourcInfoSource ssis = new SaleSourcInfoSource();
        // 出库单信息
        ssis.setOrderCode(request.getOrderStoreCode());
        ssis.setOutboundOderCode(insertOutbound);
        ssis.setBity("OFFLINE");
        ssis.setWarehouseCode(request.getWarehouseCode());
        ssis.setFromType("销售单");
        ssis.setFromCode(request.getSourceCode());
        ssis.setPlatformCode("99");
        ssis.setPlatformName("独立网店");
        ssis.setIsUrgency("0");
        ssis.setDownDate(request.getCreateTime());
        ssis.setPayTime(request.getPaymentTime());
        ssis.setAuditTime(new Date());
        ssis.setIsDeliveryPay(false);
        ssis.setShopCode(request.getCustomerCode());
        ssis.setShopName(request.getCustomerName());
        ssis.setBunick(request.getCustomerName());
        ssis.setConsignee(request.getCustomerName());
        ssis.setProvinceName(request.getProvinceName());
        ssis.setCityName(request.getCityName());
        // 直辖市情况下 区/县为null
        if(request.getDistrictName() == null){
            ssis.setAreaName(request.getCityName());
        }else {
            ssis.setAreaName(request.getDistrictName());
        }
        ssis.setAddress(request.getReceiveAddress());
        ssis.setMobile(request.getReceiveMobile());
        ssis.setTel(request.getReceiveMobile());
        ssis.setOrderPrice(request.getTotalProductAmount().doubleValue());
        ssis.setAmountReceivable(0.0);
        ssis.setIsinvoice(true);
        ssis.setInvoiceName(request.getInvoiceTitle());
        ssis.setGoodsOwner(request.getReceivePerson()); // 货主 取收货人 要确认
        ssis.setRemark(request.getRemake());

        // 出库单商品信息
        List<SaleOutboundDetailedSource> plists = new ArrayList<>();
        List<ErpOrderItem> itemList = request.getItemList();
       if(itemList != null){
           for (ErpOrderItem list : itemList) {
               SaleOutboundDetailedSource sods = new SaleOutboundDetailedSource();
               sods.setSku(list.getSkuCode());
               sods.setQty(list.getProductCount().intValue());
               sods.setPrice(list.getPreferentialAmount().doubleValue());
               sods.setActualAmount(list.getTotalPreferentialAmount().doubleValue());
               sods.setGwf1(list.getOrderStoreCode());
               sods.setGwf2(list.getLineCode().toString());
               sods.setGwf3(list.getColorName());
               sods.setGwf4(list.getModelCode());
               sods.setGwf5(list.getUnitName());
               plists.add(sods);
           }
       }
        ssis.setSaleOutboundDetailedSource(plists);
        List<BatchWmsInfo> pBatchLists = new ArrayList<>();
        List<OrderInfoItemProductBatch> itemBatchLists = request.getItemBatchList();
        if(itemBatchLists != null){
            for (OrderInfoItemProductBatch itemBatchList : itemBatchLists) {
                BatchWmsInfo pBtachList = new BatchWmsInfo();
                pBtachList.setLineCode(itemBatchList.getProductLineNum().intValue());
                pBtachList.setSkuCode(itemBatchList.getSkuCode());
                pBtachList.setSkuName(itemBatchList.getSkuName());
                pBtachList.setBatchCode(itemBatchList.getBatchNumber());
                pBtachList.setProdcutDate(itemBatchList.getProductTime().toString());
                pBtachList.setBeOverdueData(itemBatchList.getBeOverdueData());
                pBtachList.setBatchRemark(itemBatchList.getBatchRemark());
                pBtachList.setActualTotalCount(itemBatchList.getActualDeliverNum());
                pBatchLists.add(pBtachList);
            }
        }
        ssis.setOrderInfoItemProductBatches(pBatchLists);
        return ssis;
    }

    private OrderInfoReqVO orderInfoRequestVo(ErpOrderInfo request){
        OrderInfoReqVO vo = new OrderInfoReqVO();
        BeanUtils.copyProperties(request, vo);
        vo.setCompanyCode(Global.COMPANY_09);
        vo.setCompanyName(Global.COMPANY_09_NAME);
        vo.setOrderOriginal(request.getCompanyName());
        vo.setOrderCategoryCode(request.getCompanyCode());
        vo.setOrderCode(request.getOrderStoreCode());
        vo.setOrderType(request.getOrderTypeName());
        vo.setOrderTypeCode(Integer.valueOf(request.getOrderTypeCode()));
        vo.setOrderStatusName(request.getOrderStatusDesc());
        vo.setBeLock(request.getOrderLock());
        vo.setBeException(request.getOrderException());
        vo.setBeDelete(request.getOrderDelete());
        vo.setDistributionMode(request.getDistributionModeName());
        vo.setConsignee(request.getReceivePerson());
        vo.setConsigneePhone(request.getReceiveMobile());
        vo.setProvinceCode(request.getProvinceId());
        vo.setCityCode(request.getCityId());
        vo.setDistrictCode(request.getDistrictId());
        vo.setDetailAddress(request.getReceiveAddress());
        vo.setPaymentTypeCode(request.getPaymentCode());
        vo.setPaymentType(request.getPaymentName());
        vo.setProductTotalAmount(request.getTotalProductAmount());
        vo.setInvoiceTypeCode(request.getInvoiceType().toString());
        vo.setInvoiceType(request.getInvoiceType() == 1 ? "不开" : (request.getInvoiceType() == 2 ? "增普" : "增专"));
        vo.setVolume(request.getTotalVolume());
        vo.setWeight(request.getTotalWeight());
        vo.setBeMasterOrder(request.getOrderLevel() == 0 ? 1 : 0);
        vo.setMasterOrderCode(request.getMainOrderCode());
        vo.setOrderOriginal(request.getOrderStoreCode());
        vo.setStoreTypeCode(request.getStoreType() == null ? "" : request.getStoreType().toString());
        vo.setOrderCategory(request.getOrderCategoryName());
        vo.setCreateById(request.getCreateById());
        vo.setCreateByName(request.getCreateByName());
        vo.setUpdateById(request.getCreateById());
        vo.setUpdateByName(request.getUpdateByName());
        List<OrderInfoItemReqVO> productList = Lists.newArrayList();
        OrderInfoItemReqVO product;
        Long productNum = 0L;
        BigDecimal totalChannelAmount = BigDecimal.ZERO;
        for(ErpOrderItem item : request.getItemList()){
            product = new OrderInfoItemReqVO();
            BeanUtils.copyProperties(item, product);
            product.setCompanyCode(Global.COMPANY_09);
            product.setCompanyName(Global.COMPANY_09_NAME);
            product.setOrderCode(item.getOrderStoreCode());
            product.setSpec(item.getProductSpec());
            product.setModel(item.getModelCode());
            product.setGivePromotion(item.getProductType());
            product.setPrice(item.getProductAmount());
            product.setNum(item.getProductCount());
            product.setAmount(item.getTotalProductAmount());
            product.setActivityApportionment(item.getTotalAcivityAmount());
            product.setPreferentialAllocation(item.getTotalPreferentialAmount());
            product.setProductLineNum(item.getLineCode());
            product.setPromotionLineNum(item.getGiftLineCode());
            BigDecimal amount = item.getPurchaseAmount() == null ? BigDecimal.ZERO : item.getPurchaseAmount();
            product.setChannelUnitPrice(amount);
            BigDecimal totalAmount = amount.multiply(BigDecimal.valueOf(item.getProductCount()));
            product.setTotalChannelPrice(totalAmount);
            totalChannelAmount = totalChannelAmount.add(totalAmount);
            product.setTax(item.getTaxRate());
            product.setCompanyCode(item.getCompanyCode());
            product.setCompanyName(item.getCompanyName());
            productNum += item.getProductCount();
            productList.add(product);
        }
        vo.setProductNum(productNum);
        vo.setProductChannelTotalAmount(totalChannelAmount);
        vo.setProductList(productList);
        return vo;
    }

    // 出库单参数填充
    private String insertOutbound(OrderInfoReqVO vo) {
        OutboundReqVo outboundReqVo = new OutboundReqVo();
        // 公司
        outboundReqVo.setCompanyCode(Global.COMPANY_09);
        outboundReqVo.setCompanyName(Global.COMPANY_09_NAME);
        // 状态
        outboundReqVo.setOutboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
        outboundReqVo.setOutboundStatusName(InOutStatus.CREATE_INOUT.getName());
        // 出库类型
        outboundReqVo.setOutboundTypeCode(OutboundTypeEnum.ORDER.getCode());
        outboundReqVo.setOutboundTypeName(OutboundTypeEnum.ORDER.getName());
        // 仓库
        outboundReqVo.setLogisticsCenterCode(vo.getTransportCenterCode());
        outboundReqVo.setLogisticsCenterName(vo.getTransportCenterName());
        // 库房
        outboundReqVo.setWarehouseCode(vo.getWarehouseCode());
        outboundReqVo.setWarehouseName(vo.getWarehouseName());
        // 供应商
        outboundReqVo.setSupplierCode(vo.getSupplierCode());
        outboundReqVo.setSupplierName(vo.getSupplierName());
        //原始单号
        outboundReqVo.setSourceOderCode(vo.getOrderCode());
        //预计出库数量
        outboundReqVo.setPreOutboundNum(vo.getProductNum());
        //预计主出库数量
        outboundReqVo.setPreMainUnitNum(vo.getProductNum());
        //预计含税总金额
        outboundReqVo.setPreTaxAmount(vo.getProductTotalAmount());
        // 地址
        outboundReqVo.setProvinceCode(vo.getProvinceCode());
        outboundReqVo.setProvinceName(vo.getProvinceName());
        outboundReqVo.setCityCode(vo.getCityCode());
        outboundReqVo.setCityName(vo.getCityName());
        outboundReqVo.setCountyCode(vo.getCityCode());
        outboundReqVo.setCountyName(vo.getDistrictName());
        outboundReqVo.setConsignee(vo.getConsignee());
        outboundReqVo.setConsigneeNumber(vo.getConsigneePhone());
        outboundReqVo.setConsigneeRate(vo.getZipCode());
        outboundReqVo.setDetailedAddress(vo.getDetailAddress());
        outboundReqVo.setCreateBy(vo.getCreateByName());
        outboundReqVo.setUpdateBy(vo.getCreateByName());
        // 商品详情
        List<OrderInfoItemReqVO> productList = vo.getProductList();
        List<OutboundProductReqVo> outboundProductList = Lists.newArrayList();
        BigDecimal noTaxTotalAmount = BigDecimal.ZERO;
        OutboundProductReqVo outboundProduct;
        for (OrderInfoItemReqVO product : productList) {
            outboundProduct = new OutboundProductReqVo();
            // sku
            outboundProduct.setSkuCode(product.getSkuCode());
            outboundProduct.setSkuName(product.getSkuName());
            // 图片地址
            outboundProduct.setPictureUrl(product.getPictureUrl());
            //规格
            outboundProduct.setNorms(product.getSpec());
            //单位
            outboundProduct.setUnitCode(product.getUnitCode());
            outboundProduct.setUnitName(product.getUnitName());
            //进货规格
            outboundProduct.setOutboundNorms(product.getSpec());
            //预计出库数量
            outboundProduct.setPreOutboundNum(product.getNum());
            //预计含税进价
            outboundProduct.setPreTaxPurchaseAmount(product.getPrice());
            //预计含税总价
            outboundProduct.setPreTaxAmount(product.getAmount());
            outboundProduct.setColorCode(product.getColorCode());
            outboundProduct.setColorName(product.getColorName());
            outboundProduct.setCreateBy(vo.getCreateByName());
            outboundProduct.setUpdateBy(vo.getCreateByName());
            outboundProduct.setPreOutboundMainNum(product.getNum());
            outboundProduct.setLinenum(product.getProductLineNum());
            //计算不含税单价
            BigDecimal noTaxPrice = Calculate.computeNoTaxPrice(product.getPrice(), product.getTax());
            outboundProduct.setOutboundBaseContent("1");
            outboundProduct.setOutboundBaseUnit("1");
            outboundProduct.setTax(product.getTax());
            //计算不含税总价 (现在是主单位数量 * 单价）
            BigDecimal noTaxTotalPrice = noTaxPrice.multiply(BigDecimal.valueOf(product.getNum())).setScale(4, BigDecimal.ROUND_HALF_UP);
            noTaxTotalAmount = noTaxTotalPrice;
            outboundProductList.add(outboundProduct);
        }
        //预计无税总金额
        outboundReqVo.setPreAmount(noTaxTotalAmount);
        // 税额
        outboundReqVo.setPreTax(outboundReqVo.getPreTaxAmount().subtract(noTaxTotalAmount));
        outboundReqVo.setList(outboundProductList);
        String outboundOderCode = outboundService.saveOutbound(outboundReqVo);
        return outboundOderCode;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse orderCancel(String orderCode,  String operatorId, String operatorName){
        if(StringUtils.isBlank(orderCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // TODO 调用DL 取消的销售单接口


        // 取消销售单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderCode(orderCode);
        orderInfo.setOrderStatus(OrderStatus.TRANSACTION_TERMINATED_ABNORMALLY.getStatusCode());
        orderInfo.setUpdateById(operatorId);
        orderInfo.setUpdateByName(operatorName);
        Integer count = orderInfoMapper.updateByOrderCode(orderInfo);

        // 添加取消订单日志
        OrderInfoLog orderInfoLog = new OrderInfoLog(null, orderCode, OrderStatus.TRANSACTION_TERMINATED_ABNORMALLY.getStatusCode(),
                OrderStatus.TRANSACTION_TERMINATED_ABNORMALLY.getBackgroundOrderStatus(),
                OrderStatus.TRANSACTION_TERMINATED_ABNORMALLY.getExplain(),
                OrderStatus.TRANSACTION_TERMINATED_ABNORMALLY.getStandardDescription(),
                operatorName, new Date(), Global.COMPANY_09, Global.COMPANY_09_NAME);
        orderInfoLogMapper.insert(orderInfoLog);
        if(count <=  0){
            log.info("取消订单失败！！！");
            return HttpResponse.success(false);
        }
        return HttpResponse.success(true);
    }
/*
    @Override
    public HttpResponse updateSaleOrder(WmsOrderInfo vo) {
        *//*
        耘链逻辑：接收信息后，根据状态。
        如果是“开始拣货”更新：出库单状态开始作业、出库开始时间；销售单状态开始拣货。
        如果是“扫描完成”更新：销售单状态扫描完成。
        如果是“已全部发货”更新：出库单状态完成，出库结束时间，如果有出库开始时间也更新；销售单状态已全部发货，发货时间
            WMS对接平台逻辑：根据WMS情况，可回传一次、两次或三次，
            分别在开始拣货时、扫描完成时和已全部发货时，并回传相应状态
         *//*
        if(Global.WMS_SALE_STATUS_1.equals(vo.getOrderStatus())){
            WmsPrcking(vo);
        }else if(Global.WMS_SALE_STATUS_2.equals(vo.getOrderStatus())){
            if(vo.getPickingDate() != null && vo.getPickingDate().toString().length() <= 0){
                WmsPrcking(vo);
            }
            ChangeOrderStatusReqVO changeOrderStatusReqVO = new ChangeOrderStatusReqVO();
            changeOrderStatusReqVO.setOrderStatus(OrderStatus.SCAN_COMPLETED.getStatusCode());
            changeOrderStatusReqVO.setOrderCode(vo.getOrderStoreCode());
            changeOrderStatusReqVO.setOperator(vo.getOutboundOperator());
            changeStatus(changeOrderStatusReqVO);
            OrderInfoLog log = new OrderInfoLog(null,vo.getOrderStoreCode(), OrderStatus.SCAN_COMPLETED.getStatusCode(), OrderStatus.getAllStatus().get(OrderStatus.SCAN_COMPLETED.getStatusCode()).getBackgroundOrderStatus(),OrderStatus.getAllStatus().get(OrderStatus.SCAN_COMPLETED.getStatusCode()).getStandardDescription(),null,vo.getOutboundOperator(),new Date(),null,null);
            List<OrderInfoLog> logs = Lists.newArrayList();
            logs.add(log);
            saveLog(logs);
        }else if(Global.WMS_SALE_STATUS_3.equals(vo.getOrderStatus())){
            // 拣货时间不为null/空
            if(vo.getPickingDate() != null && vo.getPickingDate().toString().length() <= 0){
                WmsPrcking(vo);
                // 扫描完成 更新状态扫描完成
                ChangeOrderStatusReqVO changeOrderStatusReqVO = new ChangeOrderStatusReqVO();
                changeOrderStatusReqVO.setOrderStatus(OrderStatus.SCAN_COMPLETED.getStatusCode());
                changeOrderStatusReqVO.setOrderCode(vo.getOrderStoreCode());
                changeOrderStatusReqVO.setOperator(vo.getOutboundOperator());
                changeStatus(changeOrderStatusReqVO);
                OrderInfoLog log = new OrderInfoLog(null,vo.getOrderStoreCode(), OrderStatus.SCAN_COMPLETED.getStatusCode(), OrderStatus.getAllStatus().get(OrderStatus.SCAN_COMPLETED.getStatusCode()).getBackgroundOrderStatus(),OrderStatus.getAllStatus().get(OrderStatus.SCAN_COMPLETED.getStatusCode()).getStandardDescription(),null,vo.getOutboundOperator(),new Date(),null,null);
                List<OrderInfoLog> logs = Lists.newArrayList();
                logs.add(log);
                saveLog(logs);
            }
            // 已全部发货
            ChangeOrderStatusReqVO changeOrderStatusReqVO = new ChangeOrderStatusReqVO();
            changeOrderStatusReqVO.setOrderStatus(OrderStatus.ALL_SHIPPED.getStatusCode());
            changeOrderStatusReqVO.setOrderCode(vo.getOrderStoreCode());
            changeOrderStatusReqVO.setOperator(vo.getOutboundOperator());
            changeOrderStatusReqVO.setDeliveryTime(vo.getOutboundDate());
            changeStatus(changeOrderStatusReqVO);
            OrderInfoLog log = new OrderInfoLog(null,vo.getOrderStoreCode(), OrderStatus.ALL_SHIPPED.getStatusCode(), OrderStatus.getAllStatus().get(OrderStatus.ALL_SHIPPED.getStatusCode()).getBackgroundOrderStatus(),OrderStatus.getAllStatus().get(OrderStatus.ALL_SHIPPED.getStatusCode()).getStandardDescription(),null,vo.getOutboundOperator(),new Date(),null,null);
            List<OrderInfoLog> logs = Lists.newArrayList();
            logs.add(log);
            saveLog(logs);
        }else {
            return HttpResponse.failure(ResultCode.NOT_HAVE_PARAM);
        }
        return HttpResponse.success(true);
    }*/

    private void WmsPrcking(WmsOrderInfo vo) {
        ChangeOrderStatusReqVO changeOrderStatusReqVO = new ChangeOrderStatusReqVO();
        changeOrderStatusReqVO.setOrderStatus(OrderStatus.PICKING.getStatusCode());
        changeOrderStatusReqVO.setOrderCode(vo.getOrderStoreCode());
        changeOrderStatusReqVO.setOperator(vo.getOutboundOperator());
        changeStatus(changeOrderStatusReqVO);
        //存日志
        OrderInfoLog log = new OrderInfoLog(null,vo.getOrderStoreCode(), OrderStatus.PICKING.getStatusCode(), OrderStatus.getAllStatus().get(OrderStatus.PICKING.getStatusCode()).getBackgroundOrderStatus(),OrderStatus.getAllStatus().get(OrderStatus.PICKING.getStatusCode()).getStandardDescription(),null,vo.getOutboundOperator(),new Date(),null,null);
        List<OrderInfoLog> logs = Lists.newArrayList();
        logs.add(log);
        saveLog(logs);
    }

}
