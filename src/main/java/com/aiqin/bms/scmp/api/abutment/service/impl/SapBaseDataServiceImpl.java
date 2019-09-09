package com.aiqin.bms.scmp.api.abutment.service.impl;

import com.aiqin.bms.scmp.api.abutment.domain.conts.StringConvertUtil;
import com.aiqin.bms.scmp.api.abutment.domain.request.*;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.aiqin.bms.scmp.api.purchase.domain.response.InnerValue;
import com.aiqin.bms.scmp.api.purchase.mapper.*;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.http.HttpClient;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
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
    private static Integer SAP_API_COUNT = 100;
    @Value("${sap.order}")
    private String ORDER_URL;
    @Value("${sap.product}")
    private String PRODUCT_URL;
    @Value("${sap.supply}")
    private String SUPPLY_URL;
    @Value("${sap.purchase}")
    private String PURCHASE_URL;
    @Value("${sap.storage}")
    private String STORAGE_URL;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private OrderInfoItemMapper orderInfoItemMapper;
    @Resource
    private OrderInfoItemProductBatchMapper orderInfoItemProductBatchMapper;
    @Resource
    private ReturnOrderInfoMapper returnOrderInfoMapper;
    @Resource
    private ReturnOrderInfoItemMapper returnOrderInfoItemMapper;
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
        this.outboundToStock(storageList, sapOrderRequest);
        this.inboundToStock(storageList, sapOrderRequest);

    }

    /**
     * 出库单转结算sap对接
     *
     * @param sapOrderRequest
     */
    private void inboundToStock(List<Storage> storageList, SapOrderRequest sapOrderRequest) {
        Storage storage;
        List<Inbound> inboundList = inboundDao.listForSap(sapOrderRequest);
        List<String> orderCodes = inboundList.stream().map(Inbound::getInboundOderCode).collect(Collectors.toList());
        sapOrderRequest.setOrderCodeList(orderCodes);
        List<InboundProduct> inboundProducts = inboundProductDao.listDetailForSap(sapOrderRequest);
        List<String> skuCodes = inboundProducts.stream().map(InboundProduct::getSkuCode).collect(Collectors.toList());
        List<ProductSkuInfo> productSkuList = productSkuDao.getSkuInfoByCodeList(skuCodes);
        Map<String, Long> productMap = productSkuList.stream().collect(Collectors.toMap(ProductSkuInfo::getSkuCode, ProductSkuInfo::getManufacturerGuidePrice));
        StorageDetail storageDetail;
        List<StorageDetail> storageDetailList;
        Map<String, List<StorageDetail>> storageDetailMap = new HashMap<>();
        for (InboundProduct inboundProduct : inboundProducts) {
            storageDetail = new StorageDetail();
            storageDetail.setSkuCode(inboundProduct.getSkuCode());
            storageDetail.setSkuName(inboundProduct.getSkuName());
            storageDetail.setSkuDesc(StringConvertUtil.productDesc(inboundProduct.getColorName(), inboundProduct.getNorms(), inboundProduct.getModel()));
            storageDetail.setUnit(inboundProduct.getUnitName());
            //固定为1
            storageDetail.setUnitCount(1);
            storageDetail.setTradeExponent(1);
            storageDetail.setTaxRate(inboundProduct.getTax().intValue());
            storageDetail.setExpectCount(inboundProduct.getPreInboundNum().intValue());
            storageDetail.setExpectMinUnitCount(inboundProduct.getPreInboundNum().intValue());
            storageDetail.setExpectTaxPrice(inboundProduct.getPreTaxPurchaseAmount().intValue());
            storageDetail.setSingleCount(inboundProduct.getPraInboundNum().intValue());
            storageDetail.setMinUnitCount(inboundProduct.getPraInboundNum().intValue());
            storageDetail.setTaxRate(inboundProduct.getPraTaxPurchaseAmount().intValue());
            //厂商指导价
            storageDetail.setMinUnitCount(productMap.containsKey(inboundProduct.getSkuCode()) ? productMap.get(inboundProduct.getSkuCode()).intValue() : 0);
            if (storageDetailMap.containsKey(inboundProduct.getInboundOderCode())) {
                storageDetailList = storageDetailMap.get(inboundProduct.getInboundOderCode());
                storageDetailList.add(storageDetail);
                storageDetailMap.put(inboundProduct.getInboundOderCode(), storageDetailList);
            } else {
                storageDetailMap.put(inboundProduct.getInboundOderCode(), Collections.singletonList(storageDetail));
            }
        }
        InnerValue innerValue;
        InnerValue innerValueType;
        for (Inbound inbound : inboundList) {
            storage = new Storage();
            innerValue = StringConvertUtil.inboundTypeConvert(inbound.getInboundTypeCode());
            storage.setOrderId(String.format("%s-%s", inbound.getInboundOderCode(), innerValue.getName()));
            storage.setSubOrderType(innerValue.getValue());
            storage.setSubOrderTypeName(innerValue.getName());
            //采购和退货订单才传来源类型
            if (inbound.getInboundTypeCode().equals(3) || inbound.getInboundTypeCode().equals(1)) {
                innerValueType = StringConvertUtil.inboundSourceTypeConvert(inbound.getInboundTypeCode());
                storage.setSourceOrderId(String.format("%s-%s", inbound.getSourceOderCode(), innerValueType.getName()));
                storage.setSourceOrderCode(inbound.getSourceOderCode());
                storage.setSourceOrderType(innerValueType.getValue());
                storage.setSourceOrderTypeName(innerValueType.getValue());
            }
            storage.setOrderCode(inbound.getInboundOderCode());
            storage.setTransportCode(inbound.getLogisticsCenterCode());
            storage.setTransportName(inbound.getLogisticsCenterName());
            storage.setStorageCode(inbound.getWarehouseCode());
            storage.setStorageName(inbound.getWarehouseName());
//            storage.setSupplierCode(inbound.getSupplierCode());
//            storage.setSupplierName(inbound.getSupplierName());
            storage.setOrderCount(inbound.getPraInboundNum().intValue());
            storage.setAmount(inbound.getPraTaxAmount().toString());
            storage.setDiscountPrice("0");
            storage.setOptTime(inbound.getInboundTime());
            storage.setCreateTime(inbound.getCreateTime());
            storage.setCreateByName(inbound.getCreateBy());
            storage.setDetails(storageDetailMap.get(inbound.getInboundOderCode()));
            storageList.add(storage);
        }

    }

    /**
     * 入库单转结算sap对接
     *
     * @param sapOrderRequest
     */
    private void outboundToStock(List<Storage> storageList, SapOrderRequest sapOrderRequest) {
        Storage storage;
        List<Outbound> outboundList = outboundDao.listForSap(sapOrderRequest);
        List<String> orderCodes = outboundList.stream().map(Outbound::getOutboundOderCode).collect(Collectors.toList());
        sapOrderRequest.setOrderCodeList(orderCodes);
        List<OutboundProduct> outboundProducts = outboundProductDao.listDetailForSap(sapOrderRequest);
        List<String> skuCodes = outboundProducts.stream().map(OutboundProduct::getSkuCode).collect(Collectors.toList());
        List<ProductSkuInfo> productSkuList = productSkuDao.getSkuInfoByCodeList(skuCodes);
        Map<String, Long> productMap = productSkuList.stream().collect(Collectors.toMap(ProductSkuInfo::getSkuCode, ProductSkuInfo::getManufacturerGuidePrice));
        StorageDetail storageDetail;
        List<StorageDetail> storageDetailList;
        Map<String, List<StorageDetail>> storageDetailMap = new HashMap<>();
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
            storageDetail.setMinUnitCount(productMap.containsKey(outboundProduct.getSkuCode()) ? productMap.get(outboundProduct.getSkuCode()).intValue() : 0);
            if (storageDetailMap.containsKey(outboundProduct.getOutboundOderCode())) {
                storageDetailList = storageDetailMap.get(outboundProduct.getOutboundOderCode());
                storageDetailList.add(storageDetail);
                storageDetailMap.put(outboundProduct.getOutboundOderCode(), storageDetailList);
            } else {
                storageDetailMap.put(outboundProduct.getOutboundOderCode(), Collections.singletonList(storageDetail));
            }
        }
        InnerValue innerValue;
        InnerValue innerValueType;
        //销售单的id
        List<String> orderIds = outboundList.stream().filter((Outbound)->Outbound.getOutboundTypeCode().equals(3)).map(Outbound::getSourceOderCode).collect(Collectors.toList());
        List<OrderInfo> orderInfoList = orderInfoMapper.listByIds(orderIds);
        Map<String,OrderInfo> orderInfoMap = orderInfoList.stream().collect(Collectors.toMap(OrderInfo::getOrderCode,Function.identity()));
        OrderInfo orderInfo;
        for (Outbound outbound : outboundList) {
            storage = new Storage();
            innerValue = StringConvertUtil.outboundTypeConvert(outbound.getOutboundTypeCode());
            storage.setOrderId(String.format("%s-%s", outbound.getOutboundOderCode(), innerValue.getName()));
            storage.setSubOrderType(innerValue.getValue());
            storage.setSubOrderTypeName(innerValue.getName());
            //订单/退供才传来源类型
            if (outbound.getOutboundTypeCode().equals(1)) {
                storage.setSourceOrderId(String.format("%s-%d", outbound.getSourceOderCode(), 5));
                storage.setSourceOrderCode(outbound.getSourceOderCode());
                storage.setSourceOrderType("5");
                storage.setSourceOrderTypeName("退供");
            }
            if(outbound.getOutboundTypeCode().equals(3)){
                orderInfo = orderInfoMap.get(outbound.getSourceOderCode());
                innerValueType = StringConvertUtil.orderInfoConvert(Integer.valueOf(orderInfo.getOrderType()));
                storage.setSourceOrderId(String.format("%s-%s", outbound.getSourceOderCode(), innerValueType.getName()));
                storage.setSourceOrderCode(outbound.getSourceOderCode());
                storage.setSourceOrderType(innerValueType.getValue());
                storage.setSourceOrderTypeName(innerValueType.getValue());
            }
            storage.setOrderCode(outbound.getOutboundOderCode());
            storage.setTransportCode(outbound.getLogisticsCenterCode());
            storage.setTransportName(outbound.getLogisticsCenterName());
            storage.setStorageCode(outbound.getWarehouseCode());
            storage.setStorageName(outbound.getWarehouseName());
//            storage.setSupplierCode(outbound.getSupplierCode());
//            storage.setSupplierName(outbound.getSupplierName());
            storage.setOrderCount(outbound.getPraOutboundNum().intValue());
            storage.setAmount(outbound.getPraTaxAmount().toString());
            storage.setDiscountPrice("0");
            storage.setOptTime(outbound.getOutboundTime());
            storage.setCreateTime(outbound.getCreateTime());
            storage.setCreateByName(outbound.getCreateBy());
            storage.setDetails(storageDetailMap.get(outbound.getOutboundOderCode()));
            storageList.add(storage);
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
    @Transactional(rollbackFor = Exception.class)
    public void saleSynchronization(SapOrderRequest sapOrderRequest) {
        //销售单list
        List<Order> orderList = Lists.newArrayList();
//        orderInfoToOrder(orderList, sapOrderRequest);
//        sapOrderAbutment(orderList, 1);
//        orderList.clear();
        returnInfoToOrder(orderList, sapOrderRequest);
        sapOrderAbutment(orderList,2);
    }

    private void sapOrderAbutment(List<Order> orderList, Integer type) {
        LOGGER.info("调用结算sap销售单据参数:{} ", JsonUtil.toJson(orderList));
        LOGGER.info("type:{}", type);
        int total = (int) Math.ceil(orderList.size() / (SAP_API_COUNT * 1.0));
        int endIndex;
        List<Order> subLists;
        List<String> orderCodes;
        for (int i = 0; i < total; i++) {
            endIndex = SAP_API_COUNT * (i + 1);
            if (SAP_API_COUNT * (i + 1) >= orderList.size()) {
                endIndex = orderList.size();
            }
            subLists = orderList.subList(SAP_API_COUNT * i, endIndex);
            HttpClient client = HttpClient.post(ORDER_URL).json(subLists).timeout(10000);
            HttpResponse httpResponse = client.action().result(HttpResponse.class);
            if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
                LOGGER.info("调用结算sap销售单据成功:{}", httpResponse.getMessage());
                //1 销售出库单 2 销售退货 更新同步状态
                orderCodes = subLists.stream().map(Order::getOrderCode).collect(Collectors.toList());
                if (type.equals(1)) {
                    orderInfoMapper.updateByOrderCodes(orderCodes);
                } else if (type.equals(2)) {
                    returnOrderInfoMapper.updateByOrderCodes(orderCodes);
                }
            } else {
                LOGGER.error("调用结算sap销售单据异常:{}", httpResponse.getMessage());
                throw new GroundRuntimeException(String.format("调用结算sap销售单据异常:%s", httpResponse.getMessage()));
            }
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
        List<OrderInfo> orderInfoList = orderInfoMapper.listForSap(sapOrderRequest);
        if (CollectionUtils.isNotEmpty(orderInfoList)) {
            List<String> orderCodes = orderInfoList.stream().map(OrderInfo::getOrderCode).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(orderCodes);
            List<OrderInfoItem> orderInfoItems = orderInfoItemMapper.listDetailForSap(sapOrderRequest);
            Map<String, OrderInfoItem> orderInfoItemMap = orderInfoItems.stream().collect(Collectors.toMap(OrderInfoItem::getSkuCode, Function.identity()));
            List<OrderInfoItemProductBatch> batchList = orderInfoItemProductBatchMapper.listDetailForSap(sapOrderRequest);
            OrderDetail orderDetail;
            List<OrderDetail> orderDetails;
            OrderInfoItem orderInfoItem;
            Map<String, List<OrderDetail>> orderDetailMap = new HashMap<>();
            for (OrderInfoItemProductBatch itemProductBatch : batchList) {
                orderInfoItem = orderInfoItemMap.get(itemProductBatch.getSkuCode());
                if (orderInfoItem == null) {
                    throw new GroundRuntimeException("未查询到对应的商品信息");
                }
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
                //取供应商对应商品的表中数据
                orderDetail.setSupplierCode(itemProductBatch.getSupplierCode());
                orderDetail.setSupplierName(itemProductBatch.getSupplierName());
                orderDetail.setSingleCount(itemProductBatch.getNum().intValue());
                orderDetail.setDeliveryCount(itemProductBatch.getActualDeliverNum().intValue());
                if (orderDetailMap.containsKey(orderInfoItem.getOrderCode())) {
                    orderDetails = orderDetailMap.get(orderInfoItem.getOrderCode());
                    orderDetails.add(orderDetail);
                    orderDetailMap.put(orderInfoItem.getOrderCode(), orderDetails);
                } else {
                    orderDetails = new ArrayList<>();
                    orderDetails.add(orderDetail);
                    orderDetailMap.put(orderInfoItem.getOrderCode(), orderDetails);
                }
            }
            InnerValue innerValue;
            for (OrderInfo orderInfo : orderInfoList) {
                order = new Order();
                order.setOrderId(String.format("%s-%s", orderInfo.getOrderCode(), orderInfo.getOrderTypeCode()));
                order.setOrderCode(orderInfo.getOrderCode());
                innerValue = StringConvertUtil.orderInfoConvert(orderInfo.getOrderTypeCode());
                //单据类型
                order.setOrderType(Integer.valueOf(innerValue.getValue()));
                order.setOrderTypeDesc(innerValue.getName());
                //支付方式
                order.setPayType(orderInfo.getPaymentTypeCode());
                order.setPayTypeDesc(orderInfo.getPaymentType());
                //1 是未支付 2 是已支付
                order.setPayStatus(2);
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
                order.setOrderStatus(orderInfo.getOrderStatus().toString());
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
        List<ReturnOrderInfo> returnList = returnOrderInfoMapper.listForSap(sapOrderRequest);
        if (CollectionUtils.isNotEmpty(returnList)) {
            List<String> orderCodes = returnList.stream().map(ReturnOrderInfo::getOrderCode).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(orderCodes);
            List<ReturnOrderInfoItem> orderInfoItems = returnOrderInfoItemMapper.listDetailForSap(sapOrderRequest);
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
                order.setOrderCode(returnOrderInfo.getReturnOrderCode());
                //单据类型
                order.setOrderType(25);
                order.setOrderTypeDesc("售后退货");
                //支付方式
                order.setPayType(returnOrderInfo.getPaymentTypeCode());
                order.setPayTypeDesc(returnOrderInfo.getPaymentType());
                //1 是未支付 2 是已支付
                order.setPayStatus(2);
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
//                order.setSupplierCode(returnOrderInfo.getSupplierCode());
//                order.setSupplierName(returnOrderInfo.getSupplierName());
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
