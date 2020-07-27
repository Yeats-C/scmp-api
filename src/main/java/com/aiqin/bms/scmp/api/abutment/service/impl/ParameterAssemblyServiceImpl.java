package com.aiqin.bms.scmp.api.abutment.service.impl;

import com.aiqin.bms.scmp.api.abutment.domain.request.dl.*;
import com.aiqin.bms.scmp.api.abutment.service.DlAbutmentService;
import com.aiqin.bms.scmp.api.abutment.service.ParameterAssemblyService;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnOrderDetailReq;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnOrderInfoReq;
import com.aiqin.bms.scmp.api.product.domain.request.ReturnReq;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.ErpOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.request.order.ErpOrderItem;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.dao.supplier.SupplyCompanyAccountDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.DeliveryInformation;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompanyAccount;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompanyPurchaseGroup;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.DateUtils;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.json.JsonUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ParameterAssemblyServiceImpl implements ParameterAssemblyService {

    private static Logger LOGGER = LoggerFactory.getLogger(DlAbutmentServiceImpl.class);
    @Resource
    private WarehouseDao warehouseDao;
    @Resource
    private SupplyCompanyAccountDao supplyCompanyAccountDao;
    @Resource
    private SupplierDictionaryInfoDao supplierDictionaryInfoDao;
    @Resource
    @Lazy(true)
    private DlAbutmentService dlAbutmentService;

    @Override
    public ErpOrderInfo orderInfoParameter(OrderInfoRequest request) {
        ErpOrderInfo orderInfo = BeanCopyUtils.copy(request, ErpOrderInfo.class);
        orderInfo.setSourceCode(request.getOrderId());
        orderInfo.setOrderStoreCode(request.getOrderCode());
        // 默认公司为宁波熙耘
        orderInfo.setCompanyCode(Global.COMPANY_09);
        orderInfo.setCompanyName(Global.COMPANY_09_NAME);
        orderInfo.setOrderTypeCode(request.getOrderType().toString());
        // 供货方式 1配送、2直送、3货架直送、4采购直送
        orderInfo.setOrderTypeName(request.getOrderType() == 1 ? "配送" :(request.getOrderType() == 2 ? "直送" :
                (request.getOrderType() == 3 ? "供货直送" : "采购直送")));
        orderInfo.setOrderCategoryName(request.getOrderCategory());
        orderInfo.setOrderCategoryCode(request.getOrderCategoryCode());
        orderInfo.setStoreCode(request.getCustomerCode());
        orderInfo.setSourceName(request.getCustomerName());
        if(request.getOrderType() == 1){
            // 配送默认状态为6
            orderInfo.setOrderStatus(6);
        }else {
            // 其他的默认状态为5
            orderInfo.setOrderStatus(5);
        }
        // 默认转账
        orderInfo.setPaymentCode("1");
        orderInfo.setPaymentName("转账");
        orderInfo.setProvinceId(request.getProvinceCode());
        orderInfo.setOrderException(0);
        orderInfo.setOrderDelete(0);
        orderInfo.setOrderLock(0);
        orderInfo.setCityId(request.getCityCode());
        orderInfo.setDistrictId(request.getDistrictCode());
        orderInfo.setReceiveAddress(request.getDetailAddress());
        orderInfo.setReceivePerson(request.getConsignee());
        orderInfo.setReceiveMobile(request.getConsigneePhone());
        orderInfo.setProductChannelTotalAmount(request.getChannelTotalAmount());
        orderInfo.setTotalProductAmount(request.getProductTotalAmount());
        orderInfo.setDiscountAmount(request.getActivityDiscount());
        orderInfo.setTotalVolume(request.getVolume());
        orderInfo.setTotalWeight(request.getWeight());
        orderInfo.setCreateByName("DL同步");
        orderInfo.setSourceCode(request.getOrderId());
        // 默认为DL平台类型
        orderInfo.setPlatformType(1);
        // 默认主订单
        orderInfo.setOrderLevel(0);
        // 默认已支付
        orderInfo.setPaymentStatus(0);
        if(StringUtils.isNotBlank(request.getChannelName())){
            // 渠道类型 1爱亲科技、2萌贝树、3小红马、4爱亲母婴
            orderInfo.setChannelName(request.getChannelName());
            orderInfo.setChannelCode(request.getChannelName() == "爱亲科技" ? "1" :(request.getChannelName() == "萌贝树" ? "2" :
                    (request.getChannelName() == "小红马" ? "3" : "4")));
        }
        // 转换库房信息
        WarehouseDTO warehouse = warehouseDao.warehouseDl(request.getWarehouseCode(), request.getWmsWarehouseType());
        if(warehouse == null){
            LOGGER.info("DL 推送销售单耘链的库房转换失败：{}", JsonUtil.toJson(request));
            return null;
        }
        orderInfo.setWarehouseCode(warehouse.getWarehouseCode());
        orderInfo.setWarehouseName(warehouse.getWarehouseName());
        orderInfo.setTransportCenterCode(warehouse.getLogisticsCenterCode());
        orderInfo.setTransportCenterName(warehouse.getLogisticsCenterName());
        if(CollectionUtils.isEmpty(request.getProductList()) && request.getProductList().size() == 0){
           LOGGER.info("DL 推送耘链销售单商品信息为空：{}", JsonUtil.toJson(request));
           return null;
        }
        ErpOrderItem item;
        List<ErpOrderItem> itemList = Lists.newArrayList();
        for (ProductRequest product : request.getProductList()){
            item = BeanCopyUtils.copy(product, ErpOrderItem.class);
            item.setOrderStoreCode(request.getOrderCode());
            item.setModelCode(product.getModelNumber());
            item.setTotalAcivityAmount(product.getActivityApportionment());
            item.setTotalPreferentialAmount(product.getPreferentialAllocation());
            item.setPurchaseAmount(product.getChannelAmount());
            item.setProductCount(product.getTotalCount());
            item.setLineCode(product.getLineCode().longValue());
            if(CollectionUtils.isNotEmpty(product.getBatchList()) && product.getBatchList().size() > 0){
               for (BatchRequest batch : product.getBatchList()){
                   item.setProductCount(batch.getTotalCount());
//                   item.setBatchDate(DateUtils.strToDateLong(batch.getProductDate()));
                   item.setBatchDate(batch.getProductDate());
                   itemList.add(item);
               }
            }else {
                item.setProductCount(product.getTotalCount());
                itemList.add(item);
            }
        }
        orderInfo.setItemList(itemList);
        LOGGER.info("DL 推送销售单耘链的参数转换：{}", JsonUtil.toJson(orderInfo));
        return orderInfo;
    }

    @Override
    public ReturnReq returnInfoParameter(ReturnOrderInfoRequest request){
        ReturnReq returnRequest = new ReturnReq();
        ReturnOrderInfoReq returnInfo = BeanCopyUtils.copy(request, ReturnOrderInfoReq.class);
        returnInfo.setReturnOrderId(request.getReturnOrderId());
        returnInfo.setOrderStoreCode(request.getOrderCode());
        // 默认公司为宁波熙耘
        returnInfo.setCompanyCode(Global.COMPANY_09);
        returnInfo.setCompanyName(Global.COMPANY_09_NAME);
        returnInfo.setOrderType(request.getOrderType());
        // 默认售后退货
        returnInfo.setReturnOrderType(2);
        returnInfo.setReturnLock(1);
        // 默认已支付
        returnInfo.setPaymentStatus(0);
        // 默认转账
        returnInfo.setPaymentCode("1");
        returnInfo.setPaymentName("转账");
        // 默认退货退款
        returnInfo.setTreatmentMethod(1);
        returnInfo.setStoreCode(request.getCustomerCode());
        returnInfo.setStoreName(request.getCustomerName());
        returnInfo.setLogisticsCompanyCode(request.getTransportCompanyCode());
        returnInfo.setLogisticsCompanyName(request.getTransportCompanyName());
        returnInfo.setLogisticsCode(request.getTransportCompanyNumber());
        returnInfo.setReturnOrderAmount(request.getProductTotalAmount());
        returnInfo.setReceivePerson(request.getConsignor());
        returnInfo.setReceiveMobile(request.getConsignorPhone());
        returnInfo.setTotalWeight(request.getWeight());
        returnInfo.setTotalVolume(request.getVolume());
        returnInfo.setReturnReasonContent(request.getReturnReason());
        returnInfo.setUseStatus(0);
        returnInfo.setUpdateById(request.getCreateById());
        returnInfo.setUpdateByName(request.getCreateByName());
        // 默认未退款
        returnInfo.setRefundStatus(0);
        // 默认整单退
        returnInfo.setProcessType(0);
        // 来源DL
        returnInfo.setSourceType(4);
        // 默认为DL平台类型
        returnInfo.setPlatformType(1);
        returnInfo.setCopartnerAreaId(request.getPartnerCode());
        returnInfo.setCopartnerAreaName(request.getPartnerName());
        // 转换库房信息
        WarehouseDTO warehouse = warehouseDao.warehouseDl(request.getTransportCenterCode(), request.getWmsWarehouseType());
        if(warehouse == null){
            LOGGER.info("DL 推送退货单耘链的库房转换失败：{}", JsonUtil.toJson(request));
            return null;
        }
        returnInfo.setWarehouseCode(warehouse.getWarehouseCode());
        returnInfo.setWarehouseName(warehouse.getWarehouseName());
        returnInfo.setTransportCenterCode(warehouse.getLogisticsCenterCode());
        returnInfo.setTransportCenterName(warehouse.getLogisticsCenterName());
        if(StringUtils.isNotBlank(request.getChannelName())){
            // 渠道类型 1爱亲科技、2萌贝树、3小红马、4爱亲母婴
            returnInfo.setChannelName(request.getChannelName());
            returnInfo.setChannelCode(request.getChannelName() == "爱亲科技" ? "1" :(request.getChannelName() == "萌贝树" ? "2" :
                    (request.getChannelName() == "小红马" ? "3" : "4")));
        }
        returnRequest.setReturnOrderInfo(returnInfo);
        if(CollectionUtils.isEmpty(request.getProductList()) && request.getProductList().size() <= 0){
            LOGGER.info("DL->耘链 推送耘链退货单商品信息为空");
            return null;
        }
        ReturnOrderDetailReq item;
        List<ReturnOrderDetailReq> itemList = Lists.newArrayList();
        for (ProductRequest product : request.getProductList()){
            item = BeanCopyUtils.copy(product, ReturnOrderDetailReq.class);
            item.setReturnOrderDetailId(IdUtil.uuid());
            item.setReturnOrderCode(request.getReturnOrderCode());
            item.setModelCode(product.getModelNumber());
            // 默认商品类型 - 商品
            item.setProductType(0);
            item.setZeroDisassemblyCoefficient(1L);
            item.setReturnProductCount(product.getTotalCount());
            item.setTotalProductAmount(product.getProductTotalAmount());
            item.setUseStatus(0);
            item.setCreateById(request.getCreateById());
            item.setCreateByName(request.getCreateByName());
            item.setLineCode(product.getLineCode().longValue());
            item.setReturnProductCount(product.getTotalCount());
            itemList.add(item);
        }
        returnRequest.setReturnOrderDetailReqList(itemList);
        LOGGER.info("DL->耘链 推送退货单到耘链的参数转换：{}", JsonUtil.toJson(returnRequest));
        return returnRequest;
    }

    @Override
    public SupplierInfoRequest supplierParameter(SupplierAbutmentRequest request){
        LOGGER.info("供应商开始转换调用DL参数：{}", JsonUtil.toJson(request));
        SupplierInfoRequest supplierInfo = BeanCopyUtils.copy(request, SupplierInfoRequest.class);
        supplierInfo.setSupplierCode(request.getSupplyCode());
        supplierInfo.setSupplierName(request.getSupplyName());
        supplierInfo.setSupplierAbbreviation(request.getSupplyAbbreviation());
        // 查询供应商的类型
        String typeName = supplierDictionaryInfoDao.dictionaryDetailInfo(request.getSupplyType(), "106");
        LOGGER.info("查询供应商属性信息：{}", typeName);
        supplierInfo.setSupplierType(typeName);
        supplierInfo.setMobile(request.getPhone());
        supplierInfo.setContactsPhone(request.getMobilePhone());
        supplierInfo.setContacts(request.getContactName());
        supplierInfo.setProvinceCode(request.getProvinceId());
        supplierInfo.setCityCode(request.getCityId());
        supplierInfo.setDistrictCode(request.getDistrictId());
        supplierInfo.setDetailAddress(request.getAddress());
        supplierInfo.setEnable(request.getEnable().intValue());
        supplierInfo.setSupplierCompanyCode(request.getSupplierCode());
        supplierInfo.setSupplierCompanyName(request.getSupplierName());
        // 查询供应商的结算账户信息
        SupplyCompanyAccount account = supplyCompanyAccountDao.companyAccount(supplierInfo.getSupplierCode());
        if(account != null){
            supplierInfo.setBankAccount(account.getBankAccount());
            supplierInfo.setAccount(account.getAccount());
            supplierInfo.setAccountName(account.getAccountName());
            supplierInfo.setMaxPaymentAmount(account.getMaxPaymentAmount());
        }else {
            supplierInfo.setBankAccount("");
            supplierInfo.setAccount("");
            supplierInfo.setAccountName("");
            supplierInfo.setMaxPaymentAmount(BigDecimal.ZERO);
        }

        if(CollectionUtils.isNotEmpty(request.getGroupList())){
            List<String> groups = Lists.newArrayList();
            for (SupplyCompanyPurchaseGroup group : request.getGroupList()){
                groups.add(group.getPurchasingGroupName());
            }
            supplierInfo.setPurchaseGroupName(groups);
        }

        if(CollectionUtils.isNotEmpty(request.getDeliveryList())){
            List<SupplierDeliveryRequest> deliveryRequests = Lists.newArrayList();
            SupplierDeliveryRequest deliveryRequest;
            for (DeliveryInformation delivery : request.getDeliveryList()){
                deliveryRequest = new SupplierDeliveryRequest();
                deliveryRequest.setDeliveryType(delivery.getDeliveryType().intValue());
                deliveryRequest.setProvinceCode(delivery.getSendProvinceId());
                deliveryRequest.setProvinceName(delivery.getSendProvinceName());
                deliveryRequest.setCityCode(delivery.getSendCityId());
                deliveryRequest.setCityName(delivery.getSendCityName());
                deliveryRequest.setDistrictCode(delivery.getSendDistrictId());
                deliveryRequest.setDistrictName(delivery.getSendDistrictName());
                deliveryRequest.setDetailAddress(delivery.getSendingAddress());
                deliveryRequests.add(deliveryRequest);
            }
            supplierInfo.setDeliveryList(deliveryRequests);
        }
        LOGGER.info("供应商转换调用dl参数：{}", JsonUtil.toJson(supplierInfo));
        dlAbutmentService.supplierInfo(supplierInfo);
        return supplierInfo;
    }

}
