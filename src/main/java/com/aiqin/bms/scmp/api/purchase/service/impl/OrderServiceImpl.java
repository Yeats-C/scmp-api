package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.OrderStatus;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.ReturnOrderStatus;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
import com.aiqin.bms.scmp.api.product.domain.converter.order.OrderToOutBoundConverter;
import com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoDTO;
import com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoItemDTO;
import com.aiqin.bms.scmp.api.product.domain.dto.order.OrderInfoItemProductBatchDTO;
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
import com.aiqin.bms.scmp.api.purchase.service.OrderService;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
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
        OrderInfoLog log = new OrderInfoLog(null, info.getOrderCode(), info.getOrderStatus(),
                OrderStatus.getAllStatus().get(info.getOrderStatus()).getBackgroundOrderStatus(),
                OrderStatus.getAllStatus().get(info.getOrderStatus()).getStandardDescription(), null,
                info.getOperator(), date, info.getCompanyCode(), info.getCompanyName());
        logs.add(log);
        // 保存订单和订单商品信息
        saveData(orderItems, orders);
        //存日志
        saveLog(logs);
        // 调用出库单生成销售的出库单信息
        List<OrderInfoDTO> list = Lists.newArrayList();
        OrderInfoDTO dto = new OrderInfoDTO();
        List<OrderInfoItemDTO> dtoList = BeanCopyUtils.copyList(orderItem, OrderInfoItemDTO.class);
        dto.setItemList(dtoList);
        list.add(dto);
        this.sendOrderToOutBound(list);
        return HttpResponse.success();
    }

}
