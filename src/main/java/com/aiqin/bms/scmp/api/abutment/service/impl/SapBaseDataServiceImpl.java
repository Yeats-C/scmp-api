package com.aiqin.bms.scmp.api.abutment.service.impl;

import com.aiqin.bms.scmp.api.abutment.domain.conts.StringConvertUtil;
import com.aiqin.bms.scmp.api.abutment.domain.request.*;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.ProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.Outbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.OutboundProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.mapper.InboundMapper;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.response.InnerValue;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoDao;
import com.aiqin.bms.scmp.api.purchase.mapper.OrderInfoItemDao;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoDao;
import com.aiqin.bms.scmp.api.purchase.mapper.ReturnOrderInfoItemDao;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger LOGGER = LoggerFactory.getLogger(SapBaseDataServiceImpl.class);

    @Resource
    private OrderInfoDao orderInfoDao;
    @Resource
    private OrderInfoItemDao orderInfoItemDao;
    @Resource
    private ReturnOrderInfoDao returnOrderInfoDao;
    @Resource
    private ReturnOrderInfoItemDao returnOrderInfoItemDao;
    @Resource
    private InboundDao inboundDao;
    @Resource
    private InboundProductDao inboundProductDao;
    @Resource
    private OutboundDao outboundDao;
    @Resource
    private OutboundProductDao outboundProductDao;
    @Resource
    private ProductSkuDao productSkuDao;

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
    public void stockSynchronization(SapOrderRequest sapOrderRequest) {

        List<Storage> storageList = Lists.newArrayList();


    }

    /**
     * 出库单转结算sap对接
     * @param sapOrderRequest
     */
    private void inboundToStock(List<Storage> storageList,SapOrderRequest sapOrderRequest){

    }

    /**
     * 入库单转结算sap对接
     * @param sapOrderRequest
     */
    private void outboundToStock(List<Storage> storageList,SapOrderRequest sapOrderRequest){
        Storage storage;
        List<Outbound> outboundList = outboundDao.listForSap(sapOrderRequest);
        List<String> orderCodes = outboundList.stream().map(Outbound::getOutboundOderCode).collect(Collectors.toList());
        sapOrderRequest.setOrderCodeList(orderCodes);
        List<OutboundProduct> outboundProducts = outboundProductDao.listDetailForSap(sapOrderRequest);
        List<String> skuCodes = outboundProducts.stream().map(OutboundProduct::getSkuCode).collect(Collectors.toList());
        List<ProductSkuInfo> productSkuList = productSkuDao.getSkuInfoByCodeList(skuCodes);
        Map<String,Long> productMap = productSkuList.stream().collect(Collectors.toMap(ProductSkuInfo::getSkuCode,ProductSkuInfo::getManufacturerGuidePrice));
        StorageDetail storageDetail;
        List<StorageDetail> storageDetailList;
        Map<String, List<StorageDetail> > storageDetailMap = new HashMap<>();
        for (OutboundProduct outboundProduct : outboundProducts) {
            storageDetail = new StorageDetail();
            storageDetail.setSkuCode(outboundProduct.getSkuCode());
            storageDetail.setSkuName(outboundProduct.getSkuName());
            storageDetail.setSkuDesc(StringConvertUtil.productDesc(outboundProduct.getColorName(), outboundProduct.getNorms(), outboundProduct.getModel()));
            storageDetail.setUnit(outboundProduct.getUnitName());
            //固定为1
            storageDetail.setUnitCount(1);
            storageDetail.setTradeExponent(1);
            storageDetail.setTaxRate(outboundProduct.getTax().intValue());
            storageDetail.setExpectCount(outboundProduct.getPreOutboundNum().intValue());
            storageDetail.setExpectMinUnitCount(outboundProduct.getPreOutboundNum().intValue());
            storageDetail.setExpectTaxPrice(outboundProduct.getPreTaxPurchaseAmount().intValue());
            storageDetail.setSingleCount(outboundProduct.getPraOutboundNum().intValue());
            storageDetail.setMinUnitCount(outboundProduct.getPraOutboundMainNum().intValue());
            storageDetail.setTaxRate(outboundProduct.getPraTaxPurchaseAmount().intValue());
            //厂商指导价
            storageDetail.setMinUnitCount(productMap.containsKey(outboundProduct.getSkuCode())?productMap.get(outboundProduct.getSkuCode()).intValue():0);
            if (storageDetailMap.containsKey(outboundProduct.getOutboundOderCode())) {
                storageDetailList = storageDetailMap.get(outboundProduct.getOutboundOderCode());
                storageDetailList.add(storageDetail);
                storageDetailMap.put(outboundProduct.getOutboundOderCode(), storageDetailList);
            } else {
                storageDetailMap.put(outboundProduct.getOutboundOderCode(), Collections.singletonList(storageDetail));
            }
        }
        InnerValue innerValue;
        for (Outbound outbound : outboundList) {
            storage = new Storage();
            innerValue = StringConvertUtil.outboundTypeConvert(outbound.getOutboundTypeCode());
            storage.setOrderId(String.format("%s-%s", outbound.getOutboundOderCode(), innerValue.getName()));
            storage.setOrderCode(outbound.getOutboundOderCode());
            storage.setSourceOrderId(String.format("%s-%s", outbound.getSourceOderCode(), innerValue.getName()));
            storage.setSourceOrderCode(outbound.getSourceOderCode());
        }

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
        returnInfoToOrder(orderList, sapOrderRequest);
        LOGGER.info("调用结算sap销售单据参数:{}", JsonUtil.toJson(orderList));
        HttpClient client = HttpClient.post("").json(orderList).timeout(10000);
        HttpResponse httpResponse = client.action().result(HttpResponse.class);
        if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
            LOGGER.info("调用结算sap销售单据成功:{}", httpResponse.getMessage());
        } else {
            LOGGER.error("调用结算sap销售单据异常:{}", httpResponse.getMessage());
            throw new GroundRuntimeException(String.format("调用结算sap销售单据异常:%s", httpResponse.getMessage()));
        }

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

    /**
     * 销售退货
     *
     * @param orderList
     * @param sapOrderRequest
     */
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
                //退货没有赠品
                orderDetail.setGiftFlag(0);
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
