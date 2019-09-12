package com.aiqin.bms.scmp.api.abutment.service.impl;

import com.aiqin.bms.scmp.api.abutment.domain.conts.ScmpOrderEnum;
import com.aiqin.bms.scmp.api.abutment.domain.conts.ScmpStorageChangeEnum;
import com.aiqin.bms.scmp.api.abutment.domain.conts.StringConvertUtil;
import com.aiqin.bms.scmp.api.abutment.domain.request.*;
import com.aiqin.bms.scmp.api.abutment.service.SapBaseDataService;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.common.OutboundTypeEnum;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.mapper.AllocationMapper;
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
import com.google.common.collect.Maps;
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
    private InboundBatchDao inboundBatchDao;
    @Resource
    private OutboundDao outboundDao;
    @Resource
    private OutboundProductDao outboundProductDao;
    @Resource
    private OutboundBatchDao outboundBatchDao;
    @Resource
    private ProductSkuDao productSkuDao;
    @Resource
    private AllocationMapper allocationMapper;
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
        this.inboundToStock(storageList, sapOrderRequest);
        sapStorageAbutment(storageList,1);
        storageList.clear();
        this.outboundToStock(storageList, sapOrderRequest);
        sapStorageAbutment(storageList,2);
    }

    private void sapStorageAbutment(List<Storage> storageList, Integer type) {
        LOGGER.info("调用结算sap出入库单据参数:{} ", JsonUtil.toJson(storageList));
        LOGGER.info("type:{}", type);
        int total = (int) Math.ceil(storageList.size() / (SAP_API_COUNT * 1.0));
        int endIndex;
        List<Storage> subLists;
        List<String> orderCodes;
        for (int i = 0; i < total; i++) {
            endIndex = SAP_API_COUNT * (i + 1);
            if (SAP_API_COUNT * (i + 1) >= storageList.size()) {
                endIndex = storageList.size();
            }
            subLists = storageList.subList(SAP_API_COUNT * i, endIndex);
            HttpClient client = HttpClient.post(STORAGE_URL).json(subLists).timeout(10000);
            HttpResponse httpResponse = client.action().result(HttpResponse.class);
            if (httpResponse.getCode().equals(MessageId.SUCCESS_CODE)) {
                LOGGER.info("调用结算sap出入库单据成功:{}", httpResponse.getMessage());
                //1 销售出库单 2 销售退货 更新同步状态
                orderCodes = subLists.stream().map(Storage::getOrderCode).collect(Collectors.toList());
                if (type.equals(1)) {
                    inboundDao.updateByOrderCodes(orderCodes);
                } else if (type.equals(2)) {
                    outboundDao.updateByOrderCodes(orderCodes);
                }
            } else {
                LOGGER.error("调用结算sap出入库单据异常:{}", httpResponse.getMessage());
                throw new GroundRuntimeException(String.format("调用结算sap出入库单据异常:%s", httpResponse.getMessage()));
            }
        }
    }

    /**
     * 出库单转结算sap对接
     *
     * @param sapOrderRequest
     */
    private void  inboundToStock(List<Storage> storageList, SapOrderRequest sapOrderRequest) {
        Storage storage;
        List<Inbound> inboundList = inboundDao.listForSap(sapOrderRequest);
        if(CollectionUtils.isNotEmpty(inboundList)) {
            Map<String, Inbound> inboundMap = inboundList.stream().collect(Collectors.toMap(Inbound::getInboundOderCode, Function.identity()));
            List<String> orderCodes = inboundList.stream().map(Inbound::getInboundOderCode).collect(Collectors.toList());
            List<String> inboundCodes = inboundList.stream().filter(inbound -> inbound.getInboundTypeCode().equals(InboundTypeEnum.ALLOCATE.getCode()) || inbound.getInboundTypeCode().equals(InboundTypeEnum.MOVEMENT.getCode())).map(Inbound::getInboundOderCode).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(orderCodes);
            //调拨出入库单据使用
            Map<String, Allocation> allocationMap = Maps.newHashMap();
            if (CollectionUtils.isNotEmpty(inboundCodes)) {
                List<Allocation> allocations = allocationMapper.listByInboundCodes(inboundCodes);
                allocationMap = allocations.stream().collect(Collectors.toMap(Allocation::getInboundOderCode, Function.identity()));
            }
            List<InboundProduct> inboundProducts = inboundProductDao.listDetailForSap(sapOrderRequest);
            List<InboundBatch> batchList = inboundBatchDao.listByOrderCode(sapOrderRequest);
            List<String> skuCodes = inboundProducts.stream().map(InboundProduct::getSkuCode).collect(Collectors.toList());
            List<ProductSkuInfo> productSkuList = productSkuDao.getSkuInfoByCodeList(skuCodes);
            Map<String, Long> productMap = productSkuList.stream().collect(Collectors.toMap(ProductSkuInfo::getSkuCode, ProductSkuInfo::getManufacturerGuidePrice));
            StorageDetail storageDetail;
            List<StorageDetail> storageDetailList;
            InboundProduct inboundProduct;
            Inbound inbounds;
            Map<String, InboundProduct> inboundProductMap = inboundProducts.stream().collect(Collectors.toMap(inboundProduct1 -> inboundProduct1.getInboundOderCode() + inboundProduct1.getSkuCode(), Function.identity()));
            Map<String, List<StorageDetail>> storageDetailMap = new HashMap<>();
            for (InboundBatch batch : batchList) {
                inboundProduct = inboundProductMap.get(batch.getInboundOderCode() + batch.getSkuCode());
                storageDetail = new StorageDetail();
                //供应商
                storageDetail.setSupplierCode(batch.getSupplierCode());
                storageDetail.setSupplierName(batch.getSupplierName());
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
                storageDetail.setSingleCount(inboundProduct.getPraInboundNum().intValue());
                storageDetail.setMinUnitCount(inboundProduct.getPraInboundNum().intValue());
                //退货和采购才有金额
                inbounds = inboundMap.get(batch.getInboundOderCode());
                if ((null != inbounds) && (inbounds.getInboundTypeCode().equals(InboundTypeEnum.ORDER.getCode()) || inbounds.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode()))) {
                    storageDetail.setExpectTaxPrice(inboundProduct.getPreTaxPurchaseAmount().intValue());
                    storageDetail.setTaxPrice(inboundProduct.getPraTaxPurchaseAmount().intValue());
                }
                //厂商指导价
                storageDetail.setMinUnitCount(productMap.containsKey(inboundProduct.getSkuCode()) ? productMap.get(inboundProduct.getSkuCode()).intValue() : 0);
                if (storageDetailMap.containsKey(inboundProduct.getInboundOderCode())) {
                    storageDetailList = storageDetailMap.get(inboundProduct.getInboundOderCode());
                    storageDetailList.add(storageDetail);
                    storageDetailMap.put(inboundProduct.getInboundOderCode(), storageDetailList);
                } else {
                    storageDetailList = Lists.newArrayList();
                    storageDetailList.add(storageDetail);
                    storageDetailMap.put(inboundProduct.getInboundOderCode(), storageDetailList);
                }
            }
            InnerValue innerValue;
            InnerValue innerValueType;
            Allocation allocation;
            for (Inbound inbound : inboundList) {
                storage = new Storage();
                innerValue = StringConvertUtil.inboundTypeConvert(inbound.getInboundTypeCode());
                storage.setOrderId(String.format("%s-%s", inbound.getInboundOderCode(), innerValue.getValue()));
                storage.setSubOrderType(innerValue.getValue());
                storage.setSubOrderTypeName(innerValue.getName());
                storage.setOrderCode(inbound.getInboundOderCode());
                storage.setOrderCount(inbound.getPraInboundNum().intValue());
                storage.setDiscountPrice("0");
                storage.setOptTime(inbound.getInboundTime());
                storage.setCreateTime(inbound.getCreateTime());
                storage.setCreateByName(inbound.getCreateBy());
                //采购和退货订单才传来源类型 才有金额
                if (inbound.getInboundTypeCode().equals(InboundTypeEnum.ORDER.getCode()) || inbound.getInboundTypeCode().equals(InboundTypeEnum.RETURN_SUPPLY.getCode())) {
                    innerValueType = StringConvertUtil.inboundSourceTypeConvert(inbound.getInboundTypeCode());
                    storage.setSourceOrderId(String.format("%s-%s", inbound.getSourceOderCode(), innerValueType.getValue()));
                    storage.setSourceOrderCode(inbound.getSourceOderCode());
                    storage.setSourceOrderType(innerValueType.getValue());
                    storage.setSourceOrderTypeName(innerValueType.getValue());
                    storage.setAmount(inbound.getPraTaxAmount().toString());
                    storage.setTransportCode(inbound.getLogisticsCenterCode());
                    storage.setTransportName(inbound.getLogisticsCenterName());
                    storage.setStorageCode(inbound.getWarehouseCode());
                    storage.setStorageName(inbound.getWarehouseName());
                } else if (inbound.getInboundTypeCode().equals(InboundTypeEnum.ALLOCATE.getCode()) || inbound.getInboundTypeCode().equals(InboundTypeEnum.MOVEMENT.getCode())) {
                    // 调拨单据 传调出调入仓库
                    allocation = allocationMap.get(inbound.getInboundOderCode());
                    if (null != allocation) {
                        //调出
                        storage.setTransportCode(allocation.getCallOutLogisticsCenterCode());
                        storage.setTransportName(allocation.getCallOutLogisticsCenterName());
                        storage.setStorageCode(allocation.getCallOutWarehouseCode());
                        storage.setStorageName(allocation.getCallOutWarehouseName());
                        //调入
                        storage.setTransportCode1(allocation.getCallInLogisticsCenterCode());
                        storage.setTransportName1(allocation.getCallInLogisticsCenterName());
                        storage.setStorageName1(allocation.getCallInWarehouseName());
                        storage.setStorageCode1(allocation.getCallInWarehouseCode());
                    }
                }
                storage.setDetails(storageDetailMap.get(inbound.getInboundOderCode()));
                storageList.add(storage);
            }
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
        if(CollectionUtils.isNotEmpty(outboundList)){
            Map<String, Outbound> outboundMap = outboundList.stream().collect(Collectors.toMap(Outbound::getOutboundOderCode,Function.identity()));
            List<String> orderCodes = outboundList.stream().map(Outbound::getOutboundOderCode).collect(Collectors.toList());
            List<String> outBoundCodes = outboundList.stream().filter(outbound -> outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ALLOCATE.getCode())||outbound.getOutboundTypeCode().equals(OutboundTypeEnum.MOVEMENT.getCode())).map(Outbound::getOutboundOderCode).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(orderCodes);
            //调拨出入库单据使用出库和入库的信息  其他出入库单据统一传第一个中
            Map<String,Allocation> allocationMap = Maps.newHashMap();
            if(CollectionUtils.isNotEmpty(outBoundCodes)){
                List<Allocation> allocations =  allocationMapper.listByOutboundCodes(outBoundCodes);
                allocationMap  = allocations.stream().collect(Collectors.toMap(Allocation::getOutboundOderCode,Function.identity()));
            }
            List<OutboundBatch> batchList = outboundBatchDao.listByOrderCode(sapOrderRequest);
            List<OutboundProduct> outboundProducts = outboundProductDao.listDetailForSap(sapOrderRequest);
            Map<String, OutboundProduct> outboundProductMap = outboundProducts.stream().collect(Collectors.toMap(outboundProduct -> outboundProduct.getOutboundOderCode() + outboundProduct.getSkuCode(), Function.identity()));
            List<String> skuCodes = outboundProducts.stream().map(OutboundProduct::getSkuCode).collect(Collectors.toList());
            List<ProductSkuInfo> productSkuList = productSkuDao.getSkuInfoByCodeList(skuCodes);
            Map<String, Long> productMap = productSkuList.stream().collect(Collectors.toMap(ProductSkuInfo::getSkuCode, ProductSkuInfo::getManufacturerGuidePrice));
            StorageDetail storageDetail;
            List<StorageDetail> storageDetailList;
            Map<String, List<StorageDetail>> storageDetailMap = new HashMap<>();
            Outbound outbounds;
            OutboundProduct outboundProduct;
            for (OutboundBatch batch : batchList) {
                outboundProduct = outboundProductMap.get(batch.getOutboundOderCode()+batch.getSkuCode());
                if(outboundProduct==null){
                    throw new GroundRuntimeException(String.format("未查询到商品信息!,sku_code:%s",batch.getSkuCode()));
                }
                storageDetail = new StorageDetail();
                storageDetail.setSkuCode(outboundProduct.getSkuCode());
                storageDetail.setSkuName(outboundProduct.getSkuName());
                storageDetail.setSkuDesc(StringConvertUtil.productDesc(outboundProduct.getColorName(), outboundProduct.getNorms(), outboundProduct.getModel()));
                storageDetail.setUnit(outboundProduct.getUnitName());
                storageDetail.setSupplierCode(batch.getSupplierCode());
                storageDetail.setSupplierName(batch.getSupplierName());
                //固定为1
                storageDetail.setUnitCount(1);
                storageDetail.setTradeExponent(1);
                storageDetail.setTaxRate(outboundProduct.getTax().intValue());
                storageDetail.setExpectCount(outboundProduct.getPreOutboundNum().intValue());
                storageDetail.setExpectMinUnitCount(outboundProduct.getPreOutboundNum().intValue());
                storageDetail.setSingleCount(outboundProduct.getPraOutboundNum().intValue());
                storageDetail.setMinUnitCount(outboundProduct.getPraOutboundMainNum().intValue());
                //销售和退供才有金额
                outbounds = outboundMap.get(outboundProduct.getOutboundOderCode());
                if((null != outbounds) && (outbounds.getOutboundTypeCode().equals(OutboundTypeEnum.ORDER.getCode()) || outbounds.getOutboundTypeCode().equals(OutboundTypeEnum.RETURN_SUPPLY.getCode()))){
                    storageDetail.setExpectTaxPrice(outboundProduct.getPreTaxPurchaseAmount().intValue());
                    storageDetail.setTaxPrice(outboundProduct.getPraTaxPurchaseAmount().intValue());
                }
                //厂商指导价
                storageDetail.setMinUnitCount(productMap.containsKey(outboundProduct.getSkuCode()) ? productMap.get(outboundProduct.getSkuCode()).intValue() : 0);
                if (storageDetailMap.containsKey(outboundProduct.getOutboundOderCode())) {
                    storageDetailList = storageDetailMap.get(outboundProduct.getOutboundOderCode());
                    storageDetailList.add(storageDetail);
                    storageDetailMap.put(outboundProduct.getOutboundOderCode(), storageDetailList);
                } else {
                    storageDetailList = Lists.newArrayList();
                    storageDetailList.add(storageDetail);
                    storageDetailMap.put(outboundProduct.getOutboundOderCode(), storageDetailList);
                }
            }
            InnerValue innerValue;
            InnerValue innerValueType;
            Allocation allocation;
            //销售单的id  出库单中没有上级销售单据的具体类型  所以筛选重新查询
            List<String> orderIds = outboundList.stream().filter((Outbound) -> Outbound.getOutboundTypeCode().equals((byte)3)).map(Outbound::getSourceOderCode).collect(Collectors.toList());
            Map<String, OrderInfo> orderInfoMap = Maps.newHashMap();
            if(CollectionUtils.isNotEmpty(orderIds)){
                List<OrderInfo> orderInfoList = orderInfoMapper.listByIds(orderIds);
                orderInfoMap = orderInfoList.stream().collect(Collectors.toMap(OrderInfo::getOrderCode, Function.identity()));
            }
            OrderInfo orderInfo;
            Integer type;
            for (Outbound outbound : outboundList) {
                storage = new Storage();
                innerValue = StringConvertUtil.outboundTypeConvert(outbound.getOutboundTypeCode());
                storage.setOrderId(String.format("%s-%s", outbound.getOutboundOderCode(), innerValue.getValue()));
                storage.setSubOrderType(innerValue.getValue());
                storage.setSubOrderTypeName(innerValue.getName());
                //销售订单/退供才传来源类型
                if (outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ORDER.getCode()) || outbound.getOutboundTypeCode().equals(OutboundTypeEnum.RETURN_SUPPLY.getCode())) {
                    if(outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ORDER.getCode()) ){
                        orderInfo = orderInfoMap.get(outbound.getSourceOderCode());
                        type = orderInfo.getOrderTypeCode();
                        innerValueType = StringConvertUtil.outboundSourceTypeConvert(type);
                        storage.setSourceOrderType(innerValueType.getValue());
                        storage.setSourceOrderTypeName(innerValueType.getName());
                    }else{
                        storage.setSourceOrderType(ScmpStorageChangeEnum.getByCode("5").getCode());
                        storage.setSourceOrderTypeName(ScmpStorageChangeEnum.getByCode("5").getDesc());
                    }
                    storage.setSourceOrderId(String.format("%s-%d", outbound.getSourceOderCode(), 5));
                    storage.setSourceOrderCode(outbound.getSourceOderCode());
                    storage.setAmount(outbound.getPraTaxAmount().toString());
                    storage.setTransportCode(outbound.getLogisticsCenterCode());
                    storage.setTransportName(outbound.getLogisticsCenterName());
                    storage.setStorageCode(outbound.getWarehouseCode());
                    storage.setStorageName(outbound.getWarehouseName());
                }
                if (outbound.getOutboundTypeCode().equals(OutboundTypeEnum.ALLOCATE.getCode())||outbound.getOutboundTypeCode().equals(OutboundTypeEnum.MOVEMENT.getCode())) {
                    // 调拨单据 传调出调入仓库
                    allocation = allocationMap.get(outbound.getOutboundOderCode());
                    if(null != allocation){
                        //调出
                        storage.setTransportCode(allocation.getCallOutLogisticsCenterCode());
                        storage.setStorageCode(allocation.getCallOutWarehouseCode());
                        storage.setTransportName(allocation.getCallOutLogisticsCenterName());
                        storage.setStorageName(allocation.getCallOutWarehouseName());
                        //调入
                        storage.setTransportCode1(allocation.getCallInLogisticsCenterCode());
                        storage.setStorageCode1(allocation.getCallInWarehouseCode());
                        storage.setTransportName1(allocation.getCallInLogisticsCenterName());
                        storage.setStorageName1(allocation.getCallInWarehouseName());
                    }
                }
                storage.setOrderCode(outbound.getOutboundOderCode());
                storage.setOrderCount(outbound.getPraOutboundNum().intValue());
                storage.setDiscountPrice("0");
                storage.setOptTime(outbound.getOutboundTime());
                storage.setCreateTime(outbound.getCreateTime());
                storage.setCreateByName(outbound.getCreateBy());
                storage.setDetails(storageDetailMap.get(outbound.getOutboundOderCode()));
                storageList.add(storage);
            }
        }
    }

    /**
     * 采购/退供数据同步
     */
    public void purchaseSynchronization() {
        List<Purchase> purchases = Lists.newArrayList();

    }

    /**
     * 销售入库/销售退货数据同步
     */
    @Transactional(rollbackFor = Exception.class)
    public void saleSynchronization(SapOrderRequest sapOrderRequest) {
        //销售单list
        List<Order> orderList = Lists.newArrayList();
        orderInfoToOrder(orderList, sapOrderRequest);
        sapOrderAbutment(orderList, 1);
        orderList.clear();
        returnInfoToOrder(orderList, sapOrderRequest);
        sapOrderAbutment(orderList, 2);
    }

    /**
     * sap订单对接
     * @param orderList
     * @param type
     */
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
            List<String> orderCodes = returnList.stream().map(ReturnOrderInfo::getReturnOrderCode).collect(Collectors.toList());
            sapOrderRequest.setOrderCodeList(orderCodes);
            List<ReturnOrderInfoItem> orderInfoItems = returnOrderInfoItemMapper.listDetailForSap(sapOrderRequest);
            List<InboundBatch> batchList = inboundBatchDao.listBySourceCodes(orderCodes);
            Map<String, InboundBatch> batchMap = batchList.stream().collect(Collectors.toMap(InboundBatch::getInboundOderCode, Function.identity()));
            InboundBatch batch;
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
                //供应商信息
                batch = batchMap.get(returnOrderInfoItem.getReturnOrderCode() + returnOrderInfoItem.getSkuCode());
                orderDetail.setSupplierName(batch.getSupplierName());
                orderDetail.setSupplierCode(batch.getSupplierCode());
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
                order.setOrderId(String.format("%s-%s", returnOrderInfo.getReturnOrderCode(), ScmpOrderEnum.ORDER_BACK.getCode()));
                order.setOrderCode(returnOrderInfo.getReturnOrderCode());
                //单据类型
                order.setOrderType(Integer.valueOf(ScmpOrderEnum.ORDER_BACK.getCode()));
                order.setOrderTypeDesc(ScmpOrderEnum.ORDER_BACK.getDesc());
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
                order.setDetails(orderDetailMap.get(returnOrderInfo.getReturnOrderCode()));
                orderList.add(order);
            }

        }
    }
}
