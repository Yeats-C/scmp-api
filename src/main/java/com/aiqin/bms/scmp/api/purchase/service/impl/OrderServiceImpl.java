package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.*;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.converter.order.OrderToOutBoundConverter;
import com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoDTO;
import com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoItemDTO;
import com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoItemProductBatchDTO;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckout;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.outbound.OutboundReqVo;
import com.aiqin.bms.scmp.api.product.service.OutboundService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
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
import com.aiqin.bms.scmp.api.purchase.service.OrderService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
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
        return changeStatus(reqVO);
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
    public Boolean delivery(List<DeliveryReqVO> reqVO, String orderCode) {
        //更新状态
        distribution(orderCode, ReturnOrderStatus.RETURN_COMPLETED.getStatusCode());
        int i = orderInfoItemMapper.updateBatchNumById(reqVO);
        if (i != reqVO.size()) {
            throw new BizException(ResultCode.CHANGE_ACTUAL_DELIVERY_NUM_FAILED);
        }
        return Boolean.TRUE;
    }

    @Override
    public OrderInfo selectByOrderCode(String orderCode) {
        return orderInfoMapper.selectByOrderCode2(orderCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse insertSaleOrder(OrderInfoReqVO vo) {
        if (null == vo) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Date date = Calendar.getInstance().getTime();
        // 数据处理
        List<OrderInfoItem> orderItems = Lists.newCopyOnWriteArrayList();
        List<OrderInfoLog> logs = Lists.newCopyOnWriteArrayList();
        List<OrderInfo> orders = Lists.newCopyOnWriteArrayList();
        OrderInfo info = BeanCopyUtils.copy(vo, OrderInfo.class);
        orders.add(info);
        List<OrderInfoItem> orderItem = BeanCopyUtils.copyList(vo.getProductList(), OrderInfoItem.class);
        orderItems.addAll(orderItem);
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
            }
            logs.add(log);
        }
        // 保存订单和订单商品信息
        saveData(orderItems, orders);
        //存日志
        saveLog(logs);
        // 调用销售单生成出库单信息
        this.insertOutbound(vo);
        return HttpResponse.success();
    }

    // 出库单参数填充
    private void insertOutbound(OrderInfoReqVO vo) {
        OutboundReqVo outboundReqVo = new OutboundReqVo();
        // 公司
        outboundReqVo.setCompanyCode(vo.getCompanyCode());
        outboundReqVo.setCompanyName(vo.getCompanyName());
        // 状态
        outboundReqVo.setOutboundStatusCode(vo.getOrderStatus().byteValue());
        outboundReqVo.setOutboundStatusName(vo.getOrderStatusName());
        // 出库类型
        outboundReqVo.setOutboundTypeCode(InOutStatus.CREATE_INOUT.getCode());
        outboundReqVo.setOutboundTypeName(InOutStatus.CREATE_INOUT.getName());
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
        outboundService.saveOutBoundInfo(outboundReqVo);
    }

}
