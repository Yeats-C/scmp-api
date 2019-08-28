package com.aiqin.bms.scmp.api.abutment.service.impl;

import com.aiqin.bms.scmp.api.abutment.domain.conts.StringConvertUtil;
import com.aiqin.bms.scmp.api.abutment.domain.request.*;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoDao;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemDao;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoDao;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoItemDao;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@Service
public class SapBaseDataServiceImpl implements SapBaseDataService {


    @Resource
    private OrderInfoDao orderInfoDao;
    @Resource
    private OrderInfoItemDao orderInfoItemDao;
    @Resource
    private ReturnOrderInfoDao returnOrderInfoDao;
    @Resource
    private ReturnOrderInfoItemDao returnOrderInfoItemDao;

    /**
     * 商品数据同步
     */
    public void productSynchronization() {
        SapProductSku sapProductSku = new SapProductSku();
        sapProductSku.setSapSkuCode("sku");
    }

    /**
     * 供应商数据同步
     */
    public void supplySynchronization() {

    }


    /**
     * 出入库数据同步
     */
    public void stockSynchronization() {

    }

    /**
     * 采购/退供数据同步
     */
    public void purchaseSynchronization() {
        //退供list
        List<RejectRecord> rejectRecords = Lists.newArrayList();
        List<Purchase> purchases = Lists.newArrayList();

        for (Purchase purchase : purchases) {


        }
    }

    /**
     * 销售入库/销售退货数据同步
     */
    public void saleSynchronization(SapOrderRequest sapOrderRequest) {
        //销售单list
        List<Order> orderList = Lists.newArrayList();
        orderInfoToOrder(orderList, sapOrderRequest);

    }

    /**
     * 销售订单
     *
     * @param orderList
     * @param sapOrderRequest
     */
    private void orderInfoToOrder(List<Order> orderList, SapOrderRequest sapOrderRequest) {
        Order order;
        List<OrderInfo> orderInfoList = orderInfoDao.listForSap(sapOrderRequest);
        if (CollectionUtils.isNotEmpty(orderInfoList)) {
            List<String> orderCodes = orderInfoList.stream().map(OrderInfo::getOrderCode).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(orderCodes);
            List<OrderInfoItem> orderInfoItems = orderInfoItemDao.listDetailForSap(sapOrderRequest);
            OrderDetail orderDetail;
            List<OrderDetail> orderDetails;
            Map<String, List<OrderDetail>> orderDetailMap = new HashMap<>();
            for (OrderInfoItem orderInfoItem : orderInfoItems) {
                orderDetail = new OrderDetail();
                orderDetail.setSkuCode(orderInfoItem.getSkuCode());
                orderDetail.setSkuName(orderInfoItem.getSkuName());
                orderDetail.setSkuDesc(StringConvertUtil.productDesc(orderInfoItem.getColorName(), orderInfoItem.getSpec(), orderInfoItem.getModel()));
                orderDetail.setUnit(orderInfoItem.getUnitName());
                orderDetail.setScatteredUnit(orderInfoItem.getZeroDisassemblyCoefficient());
                orderDetail.setChannelPrice(orderInfoItem.getChannelUnitPrice().toString());
                orderDetail.setGiftFlag(orderInfoItem.getGivePromotion());
                orderDetail.setSingleCount(orderInfoItem.getNum().intValue());
                orderDetail.setDeliveryCount(orderInfoItem.getActualDeliverNum().intValue());
                if (orderDetailMap.containsKey(orderInfoItem.getOrderCode())) {
                    orderDetails = orderDetailMap.get(orderInfoItem.getOrderCode());
                    orderDetails.add(orderDetail);
                    orderDetailMap.put(orderInfoItem.getOrderCode(), orderDetails);
                } else {
                    orderDetailMap.put(orderInfoItem.getOrderCode(), Collections.singletonList(orderDetail));
                }
            }
            for (OrderInfo orderInfo : orderInfoList) {
                order = new Order();
                order.setOrderId(String.format("%s-%s", orderInfo.getOrderCode(), orderInfo.getOrderTypeCode()));
                order.setOrderCode(orderInfo.getOrderCode());
                //单据类型
                order.setOrderType(orderInfo.getOrderTypeCode());
                order.setOrderTypeDesc(orderInfo.getOrderType());
                //支付方式
                order.setPayType(orderInfo.getPaymentTypeCode());
                order.setPayTypeDesc(orderInfo.getPaymentType());
                //支付时间
                order.setPayTime(orderInfo.getPaymentTime());
                //订单类别
                order.setOrderCategoryCode(orderInfo.getOrderCategoryCode());
                order.setOrderCategoryDesc(orderInfo.getOrderCategory());
                //仓库
                order.setStorageCode(orderInfo.getTransportCenterCode());
                order.setStorageName(orderInfo.getTransportCenterName());
                //库房
                order.setWarehouseCode(orderInfo.getWarehouseCode());
                order.setWarehouseName(orderInfo.getWarehouseName());
                //供应商
                order.setSupplierCode(orderInfo.getSupplierCode());
                order.setSupplierName(orderInfo.getSupplierName());
                order.setOrderCount(orderInfo.getProductNum().intValue());
                order.setWeight(orderInfo.getWeight().toString());
                order.setVolume(orderInfo.getVolume().toString());
                order.setInvoiceFlag(Integer.valueOf(orderInfo.getInvoiceType()));
                order.setInvoiceTitle(orderInfo.getInvoiceTitle());
                order.setDeliveryTime(orderInfo.getDeliveryTime());
                order.setFreightTime(orderInfo.getTransportTime());
                order.setReceiptTime(orderInfo.getReceivingTime());
                order.setCustomerCode(orderInfo.getCustomerCode());
                order.setCustomerName(orderInfo.getCustomerName());
                order.setReceiptUserName(orderInfo.getConsignee());
                order.setReceiptMobile(orderInfo.getConsigneePhone());
                order.setReceiptAddress(orderInfo.getDetailAddress());
                order.setPayChannelAmount(orderInfo.getProductChannelTotalAmount().toString());
                //渠道信息
                order.setOrderChannelCode(orderInfo.getOrderOriginal());
                order.setOrderChannelName(orderInfo.getOrderOriginalName());
                order.setCreateTime(orderInfo.getCreateTime());
                order.setCreateById(orderInfo.getCreateById());
                order.setCreateByName(orderInfo.getCreateByName());
                order.setOrderStatusDesc(orderInfo.getOrderStatus().toString());
                //目前只是完成订单先固定
                order.setOrderStatusDesc("完成");
                //详情信息
                order.setDetails(orderDetailMap.get(orderInfo.getOrderCode()));
                orderList.add(order);
            }
        }

    }

    private void returnInfoToOrder(List<Order> orderList, SapOrderRequest sapOrderRequest) {
        Order order;
        List<ReturnOrderInfo> returnList = returnOrderInfoDao.listForSap(sapOrderRequest);
        if (CollectionUtils.isNotEmpty(returnList)) {
            List<String> orderCodes = returnList.stream().map(ReturnOrderInfo::getOrderCode).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(orderCodes);
            List<ReturnOrderInfoItem> orderInfoItems = returnOrderInfoItemDao.listDetailForSap(sapOrderRequest);
            OrderDetail orderDetail;
            List<OrderDetail> orderDetails;
            Map<String, List<OrderDetail>> orderDetailMap = new HashMap<>();
            for (ReturnOrderInfoItem returnOrderInfoItem : orderInfoItems) {
                orderDetail = new OrderDetail();
                orderDetail.setSkuCode(returnOrderInfoItem.getSkuCode());
                orderDetail.setSkuName(returnOrderInfoItem.getSkuName());
                orderDetail.setSkuDesc(StringConvertUtil.productDesc(returnOrderInfoItem.getColorName(), returnOrderInfoItem.getSpec(), returnOrderInfoItem.getModel()));
                orderDetail.setUnit(returnOrderInfoItem.getUnitName());
                orderDetail.setScatteredUnit(returnOrderInfoItem.getZeroDisassemblyCoefficient());
                orderDetail.setChannelPrice(returnOrderInfoItem.getChannelUnitPrice().toString());
                orderDetail.setGiftFlag(returnOrderInfoItem.getGivePromotion());
                orderDetail.setSingleCount(returnOrderInfoItem.getNum().intValue());
                orderDetail.setDeliveryCount(returnOrderInfoItem.getActualInboundNum());
                if (orderDetailMap.containsKey(returnOrderInfoItem.getReturnOrderCode())) {
                    orderDetails = orderDetailMap.get(returnOrderInfoItem.getReturnOrderCode());
                    orderDetails.add(orderDetail);
                    orderDetailMap.put(returnOrderInfoItem.getReturnOrderCode(), orderDetails);
                } else {
                    orderDetailMap.put(returnOrderInfoItem.getReturnOrderCode(), Collections.singletonList(orderDetail));
                }
            }
            for (ReturnOrderInfo returnOrderInfo : returnList) {
                order = new Order();
                order.setOrderId(String.format("%s-%s", returnOrderInfo.getReturnOrderCode(), returnOrderInfo.getOrderTypeCode()));
                order.setOrderCode(returnOrderInfo.getOrderCode());
                //单据类型
                order.setOrderType(returnOrderInfo.getOrderTypeCode());
                order.setOrderTypeDesc(returnOrderInfo.getOrderType());
                //支付方式
                order.setPayType(returnOrderInfo.getPaymentTypeCode());
                order.setPayTypeDesc(returnOrderInfo.getPaymentType());
                //支付时间
                order.setPayTime(returnOrderInfo.getOperatorTime());
                //订单类别
                order.setOrderCategoryCode(returnOrderInfo.getReturnOrderTypeCode().toString());
                order.setOrderCategoryDesc(returnOrderInfo.getReturnOrderType());
                //仓库
                order.setStorageCode(returnOrderInfo.getTransportCenterCode());
                order.setStorageName(returnOrderInfo.getTransportCenterName());
                //库房
                order.setWarehouseCode(returnOrderInfo.getWarehouseCode());
                order.setWarehouseName(returnOrderInfo.getWarehouseName());
                //供应商
                order.setSupplierCode(returnOrderInfo.getSupplierCode());
                order.setSupplierName(returnOrderInfo.getSupplierName());
                order.setOrderCount(returnOrderInfo.getProductNum().intValue());
                order.setWeight(returnOrderInfo.getWeight().toString());
                order.setVolume(returnOrderInfo.getVolume().toString());
//                order.setInvoiceFlag(Integer.valueOf(returnOrderInfo.getInvoiceType()));
//                order.setInvoiceTitle(returnOrderInfo.getInvoiceTitle());
                order.setDeliveryTime(returnOrderInfo.getDeliveryTime());
                order.setFreightTime(returnOrderInfo.getDeliveryTime());
                order.setReceiptTime(returnOrderInfo.getReceivingTime());
                order.setCustomerCode(returnOrderInfo.getCustomerCode());
                order.setCustomerName(returnOrderInfo.getCustomerName());
                order.setReceiptUserName(returnOrderInfo.getConsignee());
                order.setReceiptMobile(returnOrderInfo.getConsigneePhone());
                order.setReceiptAddress(returnOrderInfo.getDetailAddress());
                order.setPayChannelAmount(returnOrderInfo.getProductChannelTotalAmount().toString());
                //渠道信息
                order.setOrderChannelCode(returnOrderInfo.getOrderOriginal());
                order.setOrderChannelName(returnOrderInfo.getOrderOriginalName());
                order.setCreateTime(returnOrderInfo.getCreateTime());
                order.setCreateById(returnOrderInfo.getCreateById());
                order.setCreateByName(returnOrderInfo.getCreateByName());
                order.setOrderStatusDesc(returnOrderInfo.getOrderStatus().toString());
                //目前只是完成订单先固定
                order.setOrderStatusDesc("完成");
                //详情信息
                order.setDetails(orderDetailMap.get(returnOrderInfo.getOrderCode()));
                orderList.add(order);
            }

        }
    }
}
