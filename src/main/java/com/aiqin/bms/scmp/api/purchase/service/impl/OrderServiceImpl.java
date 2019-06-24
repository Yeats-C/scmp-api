package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.OrderStatus;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.constant.CommonConstant;
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
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        List<OrderInfo> orders = Lists.newCopyOnWriteArrayList();
        List<OrderInfoItem> orderItems = Lists.newCopyOnWriteArrayList();
        List<OrderInfoLog> logs = Lists.newCopyOnWriteArrayList();
        reqVOs.parallelStream().forEach(o->{
            OrderInfo info = BeanCopyUtils.copy(o, OrderInfo.class);
            info.setCreateDate(date);
            info.setOperator(CommonConstant.SYSTEM_AUTO);
            info.setOperatorCode(CommonConstant.SYSTEM_AUTO_CODE);
            info.setOperatorTime(date);
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
    @Async("myTaskAsyncPool")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendOrderToOutBound(List<OrderInfo> orders, List<OrderInfoItem> orderItems,List<OrderInfoItemProductBatch> list) {
        //TODO 调用库房接口传入库单
//        outboundService.save();
    }

    @Override
    @Async("myTaskAsyncPool")
    @Transactional(rollbackFor = Exception.class)
    public void lockBatchStock(List<OrderInfo> orders, List<OrderInfoItem> orderItems,OrderServiceImpl service) {
        //TODO 调用库存接口锁库
        List<LockOrderItemBatchReqVO> vo = dealData(orders,orderItems);
        List<OrderInfoItemProductBatch> list = stockService.lockBatchStock(vo);
        saveLockBatch(list);
        service.sendOrderToOutBound(orders,orderItems,list);
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

}
