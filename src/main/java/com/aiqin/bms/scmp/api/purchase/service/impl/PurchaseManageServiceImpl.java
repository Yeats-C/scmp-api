package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.common.PurchaseOrderLogEnum;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.stock.ChangeStockRequest;
import com.aiqin.bms.scmp.api.product.domain.request.stock.StockInfoRequest;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.wms.CancelSource;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApplyService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.purchase.service.WmsCancelService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: zhao shuai
 * @create: 2019-06-14 17:49
 **/
@Service
public class PurchaseManageServiceImpl extends BaseServiceImpl implements PurchaseManageService {

    private static final BigDecimal big = BigDecimal.valueOf(0);

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseManageServiceImpl.class);

    @Resource
    private PurchaseOrderDao purchaseOrderDao;
    @Resource
    private PurchaseOrderProductDao purchaseOrderProductDao;
    @Resource
    private EncodingRuleDao encodingRuleDao;
    @Resource
    private ProductSkuPurchaseInfoDao productSkuPurchaseInfoDao;
    @Resource
    private InboundService inboundService;
    @Resource
    private InboundDao inboundDao;
    @Resource
    private InboundProductDao inboundProductDao;
    @Resource
    private OperationLogDao operationLogDao;
    @Resource
    private ProductSkuPicturesDao productSkuPicturesDao;
    @Resource
    private ProductSkuSupplyUnitDao productSkuSupplyUnitDao;
    @Resource
    private StockService stockService;
    @Resource
    private PurchaseGroupService purchaseGroupService;
    @Resource
    private PurchaseApplyService purchaseApplyService;
    @Resource
    private PurchaseBatchDao purchaseBatchDao;
    @Resource
    private WmsCancelService wmsCancelService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse purchaseOrder(PurchaseOrderRequest request) {
        if (request == null || request.getPurchaseOrder() == null) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }

        // 获取采购单编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.PURCHASE_ORDER_CODE);
        // 新增采购单
        String purchaseId = IdUtil.purchaseId();
        PurchaseOrder purchaseOrder = request.getPurchaseOrder();
        purchaseOrder.setPurchaseOrderId(purchaseId);
        String purchaseOrderCode = String.valueOf(encodingRule.getNumberingValue());
        purchaseOrder.setPurchaseOrderCode(purchaseOrderCode);
        purchaseOrder.setApprovalCode(purchaseOrderCode);
        purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_0);
        purchaseOrder.setStorageStatus(Global.STORAGE_STATUS_0);
        purchaseOrder.setPurchaseMode(0);
        purchaseOrder.setCreateById(request.getPurchaseOrder().getCreateById());
        purchaseOrder.setCreateByName(request.getPurchaseOrder().getCreateByName());
        purchaseOrder.setUpdateById(request.getPurchaseOrder().getCreateById());
        purchaseOrder.setUpdateById(request.getPurchaseOrder().getCreateByName());
        // 添加采购单
        Integer orderCount = purchaseOrderDao.insert(purchaseOrder);
        LOGGER.info("添加采购申信息:{}", orderCount);

        // 变更采购单号
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());

        // 添加生成采购单操作日志
        log(purchaseId, request.getPurchaseOrder().getCreateById(), request.getPurchaseOrder().getCreateByName(),
            PurchaseOrderLogEnum.INSERT_ORDER.getCode(), PurchaseOrderLogEnum.INSERT_ORDER.getName(), purchaseOrder.getApplyTypeForm());

        // 添加采购单商品
        if(CollectionUtils.isEmptyCollection(request.getProductList())){
            LOGGER.info("添加采购申商品信息为空");
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseOrderProduct> productList = request.getProductList();
        // 行号
        Integer i = 1;
        for(PurchaseOrderProduct product:productList){
            product.setOrderProductId(IdUtil.uuid());
            product.setPurchaseOrderId(purchaseId);
            product.setPurchaseOrderCode(purchaseOrderCode);
            product.setLinnum(i);
            ++i;
        }
        Integer productCount = purchaseOrderProductDao.insertAll(productList);
        LOGGER.info("添加采购单商品信息", productCount);

        // 保存采购单成功开始入库
        String inboundOderCode = purchaseOrderCode + "01";
        PurchaseInboundRequest inboundRequest = new PurchaseInboundRequest();
        inboundRequest.setInboundOrderCode(inboundOderCode);
        inboundRequest.setPurchaseNum(1);
        inboundRequest.setPurchaseOrderId(purchaseId);
        // 查询商品信息
        List<PurchaseOrderProduct> products = purchaseOrderProductDao.orderProductInfo(purchaseId);
        if(CollectionUtils.isEmptyCollection(products)){
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_PRODUCT_NULL);
        }
        inboundRequest.setProductList(products);
        InboundReqSave reqSave = this.InboundReqSave(inboundRequest);
        LOGGER.info("开始调用采购单入库");
        String s = inboundService.saveInbound(reqSave);
        if(StringUtils.isBlank(s)){
            LOGGER.error("生成入库单失败....");
            return HttpResponse.failure(ResultCode.SAVE_OUT_BOUND_FAILED);
        }else {
            LOGGER.info("调用采购单入库成功：" + s);
            // 增加采购在途数
            this.wayNum(purchaseOrder, 7);
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<List<PurchaseOrder>> purchaseOrderList(PurchaseApplyRequest purchaseApplyRequest){
        List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup(null);
        PageResData pageResData = new PageResData();
        if (org.apache.commons.collections.CollectionUtils.isEmpty(groupVoList)) {
            return HttpResponse.success(pageResData);
        }
        purchaseApplyRequest.setGroupList(groupVoList);
        List<PurchaseOrderResponse> list = purchaseOrderDao.purchaseOrderList(purchaseApplyRequest);
        Integer count = purchaseOrderDao.purchaseOrderCount(purchaseApplyRequest);
        pageResData.setDataList(list);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse cancelPurchaseOrder(PurchaseOrder purchaseOrder) {
        if (purchaseOrder == null || StringUtils.isBlank(purchaseOrder.getPurchaseOrderId())
                || purchaseOrder.getPurchaseOrderStatus() == null) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }

        // 获取当前登录人的信息
        AuthToken currentAuthToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (currentAuthToken == null) {
            LOGGER.info("获取当前登录信息失败");
            return HttpResponse.failure(ResultCode.USER_NOT_FOUND);
        }

        String purchaseOrderId = purchaseOrder.getPurchaseOrderId();
        // 查询当前的采购单信息
        PurchaseOrder order = purchaseOrderDao.purchaseOrder(purchaseOrderId);
        if (null == order) {
            LOGGER.info("采购单的信息为空");
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_NULL);
        }
        String personId = currentAuthToken.getPersonId();
        String personName = currentAuthToken.getPersonName();
        purchaseOrder.setUpdateById(personId);
        purchaseOrder.setUpdateByName(personName);

        Integer status = purchaseOrder.getPurchaseOrderStatus();
        String type = "手动";
        switch (status) {
            case 9:
                // 取消采购单
                // 判断采购单是否是待确认、备货确认、发货确认状态
                if (order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_0)
                        || order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_3)
                        || order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_4)) {
                    // 添加日志
                    log(purchaseOrderId, personId, personName, PurchaseOrderLogEnum.REVOKE.getCode(),
                            PurchaseOrderLogEnum.REVOKE.getName(), type);
                    // 调用取消入库单
                    if(order.getInfoStatus().equals(0)){
                        this.cancelInbound(order);
                        CancelSource cancelSource = new CancelSource();
                        cancelSource.setOrderType("3");
                        cancelSource.setOrderCode(purchaseOrder.getPurchaseOrderCode());
                        cancelSource.setWarehouseCode(order.getWarehouseCode());
                        cancelSource.setWarehouseName(order.getWarehouseName());
                        cancelSource.setRemark(purchaseOrder.getCancelReason());
                        wmsCancelService.wmsCancel(cancelSource);
                    }
                }else {
                    LOGGER.info("采购单非待确认、备货确认、发货确认状态");
                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_STATUS_FAIL);
                }
                break;
            case 3:
                if(!order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_0)){
                    LOGGER.info("采购单非待确认状态，不能进行备货确认");
                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_STATUS_FAIL);
                }
                // 添加备货确认日志
                log(purchaseOrderId, personId, personName, PurchaseOrderLogEnum.STOCK_UP.getCode(),
                        PurchaseOrderLogEnum.STOCK_UP.getName(), type);
                break;
            case 4:
                if(!order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_3)){
                    LOGGER.info("采购单非待备货确认状态，不能进行发货确认");
                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_STATUS_FAIL);
                }
                // 发货确认
                purchaseOrder.setDeliveryTime(Calendar.getInstance().getTime());
                // 添加发货确认日志
                log(purchaseOrderId, personId, personName, PurchaseOrderLogEnum.DELIVER_GOODS.getCode(),
                        PurchaseOrderLogEnum.DELIVER_GOODS.getName(), type);
                break;
            case 8:
                if(!order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_6)){
                    LOGGER.info("采购单非待入库中状态，不能进行完成采购确认");
                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_STATUS_FAIL);
                }
                // 完成
                purchaseOrder.setWarehouseTime(Calendar.getInstance().getTime());
                // 添加完成确认日志
                log(purchaseOrderId, personId, personName, PurchaseOrderLogEnum.PURCHASE_FINISH.getCode(),
                        PurchaseOrderLogEnum.PURCHASE_FINISH.getName(), type);
                // 调用入库单的取消
                this.cancelInbound(order);
                // 调用取消wms单据
                CancelSource cancelSource = new CancelSource();
                cancelSource.setOrderType("3");
                cancelSource.setOrderCode(purchaseOrder.getPurchaseOrderCode());
                cancelSource.setWarehouseCode(purchaseOrder.getWarehouseCode());
                cancelSource.setWarehouseName(purchaseOrder.getWarehouseName());
                cancelSource.setRemark(purchaseOrder.getCancelReason());
                wmsCancelService.wmsCancel(cancelSource);
                break;
//            case 11:
//                // 重发
//                if(!order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_0)){
//                    LOGGER.info("采购单非待确认状态，不能进行重发操作");
//                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_STATUS_FAIL);
//                }
//                purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_0);
//                //InboundServiceImpl service = (InboundServiceImpl) AopContext.currentProxy();
//                // 根据采购单号查询入库单号
//                Integer num = inboundDao.selectMaxPurchaseNumBySourceOderCode(order.getPurchaseOrderCode());
//                // 入库单号
//                String code = num <= 9 ? ("0" + num.toString()) : num.toString();
//                inboundService.pushWms(order.getPurchaseOrderCode() + code);
//                break;
        }
        Integer count = purchaseOrderDao.update(purchaseOrder);
        LOGGER.error("变更采购单状态" + count);
        return HttpResponse.success();
    }

    // 撤销未完成的入库单
    public HttpResponse cancelInbound(PurchaseOrder order) {
        // 查询入库单id
        String id = inboundDao.cancelById(order.getPurchaseOrderCode());
        if (StringUtils.isBlank(id)) {
            LOGGER.info("未查询到入库单");
            return HttpResponse.failure(ResultCode.INBOUND_INFO_NULL);
        }
        // 将入库单状态修改为取消
        Inbound inbound = new Inbound();
        inbound.setId(Long.valueOf(id));
        inbound.setInboundStatusCode(InOutStatus.CALL_OFF.getCode());
        inbound.setInboundStatusName(InOutStatus.CALL_OFF.getName());
        inboundDao.updateByPrimaryKeySelective(inbound);
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<PurchaseOrder> purchaseOrderDetails(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        PurchaseOrder detail = purchaseOrderDao.purchaseOrder(purchaseOrderId);
        return HttpResponse.success(detail);
    }

    @Override
    public HttpResponse<PurchaseOrderProduct> purchaseOrderProduct(PurchaseOrderProductRequest request){
        PageResData pageResData = new PageResData();
        if(StringUtils.isBlank(request.getPurchaseOrderId())){
            return HttpResponse.success(pageResData);
        }
        List<PurchaseOrderProduct> list = purchaseOrderProductDao.purchaseOrderList(request);
        Integer count = purchaseOrderProductDao.purchaseOrderCount(request);
        pageResData.setDataList(list);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    @Override
    public HttpResponse<List<OperationLog>> purchaseOrderLog(String operationId){
        if(StringUtils.isBlank(operationId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<OperationLog> list = operationLogDao.list(operationId);
        return HttpResponse.success(list);
    }

    @Override
    public HttpResponse<PurchaseApplyProductInfoResponse> purchaseOrderAmount(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 计算采购单的数量与金额
        PurchaseApplyProductInfoResponse amountResponse = new PurchaseApplyProductInfoResponse();
        Integer productPieceSum = 0, matterPieceSum = 0, giftPieceSum = 0;
        Integer productSingleSum= 0, matterSingleSum = 0, giftSingleSum = 0;
        Integer actualProductPieceSum = 0, actualMatterPieceSum = 0, actualGiftPieceSum = 0;
        BigDecimal productTaxSum = big, matterTaxSum = big, giftTaxSum = big;
        Integer  singleSum = 0, priceSum = 0, actualSingleSum = 0, actualPriceSum = 0;
        Integer actualProductSingleSum= 0, actualMatterSingleSum = 0, actualGiftSingleSum = 0;
        BigDecimal actualProductTaxSum = big, actualMatterTaxSum = big, actualGiftTaxSum = big;
        PurchaseOrderProductRequest request = new PurchaseOrderProductRequest();
        request.setPurchaseOrderId(purchaseOrderId);
        request.setIsPage(1);
        List<PurchaseOrderProduct> orderProducts = purchaseOrderProductDao.purchaseOrderList(request);
        if(CollectionUtils.isNotEmptyCollection(orderProducts)){
            for(PurchaseOrderProduct order:orderProducts){
                // 商品采购件数量
                Integer purchaseWhole = order.getPurchaseWhole() == null ? 0 : order.getPurchaseWhole();
                Integer purchaseSingle = order.getPurchaseSingle() == null ? 0 : order.getPurchaseSingle();
                // 包装数量
                Integer packNumber = order.getBaseProductContent() == null ? 0 : order.getBaseProductContent();
                BigDecimal amount = order.getProductAmount() == null ? big : order.getProductAmount();
                Integer singleCount = purchaseWhole * packNumber + purchaseSingle;
                singleSum += singleCount;
                priceSum += purchaseWhole;
                // 实际
                Integer actualSingleCount = order.getActualSingleCount() == null ? 0: order.getActualSingleCount();
                Integer actualWhole = 0;
                if (packNumber != 0 ) {
                    actualWhole = actualSingleCount / packNumber;
                }
                actualPriceSum += actualWhole;
                actualSingleSum += actualSingleCount;
                if(order.getProductType().equals(Global.PRODUCT_TYPE_0)){
                    productPieceSum += purchaseWhole;
                    productSingleSum += singleCount;
                    productTaxSum = amount.multiply(BigDecimal.valueOf(singleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(productTaxSum);
                    actualProductPieceSum += actualWhole;
                    actualProductSingleSum += actualSingleCount;
                    actualProductTaxSum = amount.multiply(BigDecimal.valueOf(actualSingleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(actualProductTaxSum);
                }else if(order.getProductType().equals(Global.PRODUCT_TYPE_2)){
                    matterPieceSum += purchaseWhole;
                    matterSingleSum += singleCount;
                    matterTaxSum = amount.multiply(BigDecimal.valueOf(singleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(matterTaxSum);
                    actualMatterPieceSum += actualWhole;
                    actualMatterSingleSum += actualSingleCount;
                    actualMatterTaxSum = amount.multiply(BigDecimal.valueOf(actualSingleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(actualMatterTaxSum);
                }else if(order.getProductType().equals(Global.PRODUCT_TYPE_1)){
                    giftPieceSum += purchaseWhole;
                    giftSingleSum += singleCount;
                    giftTaxSum = amount.multiply(BigDecimal.valueOf(singleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(giftTaxSum);
                    actualGiftPieceSum += actualWhole;
                    actualGiftSingleSum += actualSingleCount;
                    actualGiftTaxSum = amount.multiply(BigDecimal.valueOf(actualSingleCount)).setScale(4, BigDecimal.ROUND_HALF_UP).add(actualGiftTaxSum);
                }
            }
            // 采购
            amountResponse.setProductPieceSum(productPieceSum);
            amountResponse.setProductSingleSum(productSingleSum);
            amountResponse.setProductTaxSum(productTaxSum);
            amountResponse.setMatterPieceSum(matterPieceSum);
            amountResponse.setMatterSingleSum(matterSingleSum);
            amountResponse.setMatterTaxSum(matterTaxSum);
            amountResponse.setGiftPieceSum(giftPieceSum);
            amountResponse.setGiftSingleSum(giftSingleSum);
            amountResponse.setGiftTaxSum(giftTaxSum);
            amountResponse.setSingleSum(singleSum);
            amountResponse.setPieceSum(priceSum);

            // 实际
            amountResponse.setActualProductPieceSum(actualProductPieceSum);
            amountResponse.setActualProductSingleSum(actualProductSingleSum);
            amountResponse.setActualProductTaxSum(actualProductTaxSum);
            amountResponse.setActualMatterPieceSum(actualMatterPieceSum);
            amountResponse.setActualMatterSingleSum(actualMatterSingleSum);
            amountResponse.setActualMatterTaxSum(actualMatterTaxSum);
            amountResponse.setActualGiftPieceSum(actualGiftPieceSum);
            amountResponse.setActualGiftSingleSum(actualGiftSingleSum);
            amountResponse.setActualGiftTaxSum(actualGiftTaxSum);
            amountResponse.setActualPieceSum(actualPriceSum);
            amountResponse.setActualSingleSum(actualSingleSum);
        }
        return HttpResponse.success(amountResponse);
    }

    private InboundReqSave InboundReqSave(PurchaseInboundRequest request) {
        // 查询采购单信息
        PurchaseOrder purchaseOrder = purchaseOrderDao.purchaseOrder(request.getPurchaseOrderId());
        LOGGER.info("入库单的采购单的信息：" + purchaseOrder);
        InboundReqSave save = new InboundReqSave();
        // 赋值入库单字段
        save.setInboundOderCode(request.getInboundOrderCode());
        save.setCompanyCode(purchaseOrder.getCompanyCode());
        save.setCompanyName(purchaseOrder.getCompanyName());
        save.setInboundStatusCode(InOutStatus.CREATE_INOUT.getCode());
        save.setInboundStatusName(InOutStatus.CREATE_INOUT.getName());
        save.setInboundTypeCode(InboundTypeEnum.RETURN_SUPPLY.getCode());
        save.setInboundTypeName(InboundTypeEnum.RETURN_SUPPLY.getName());
        save.setSourceOderCode(purchaseOrder.getPurchaseOrderCode());
        save.setLogisticsCenterCode(purchaseOrder.getTransportCenterCode());
        save.setLogisticsCenterName(purchaseOrder.getTransportCenterName());
        save.setWarehouseCode(purchaseOrder.getWarehouseCode());
        save.setWarehouseName(purchaseOrder.getWarehouseName());
        save.setSupplierCode(purchaseOrder.getSupplierCode());
        save.setSupplierName(purchaseOrder.getSupplierName());
        save.setPurchaseNum(request.getPurchaseNum());
        save.setCreateBy(purchaseOrder.getCreateByName());
        save.setUpdateBy(purchaseOrder.getCreateByName());
        save.setCreateTime(Calendar.getInstance().getTime());
        save.setUpdateTime(Calendar.getInstance().getTime());
        save.setPreArrivalTime(purchaseOrder.getPreArrivalTime());

        // 查询采购单商品信息
        List<PurchaseOrderProduct> products = request.getProductList();

        // 赋值入库单商品
        Long preInboundNum = 0L, preInboundMainNum = 0L;
        BigDecimal preTaxAmount = big, preNoTaxAmount = big;
        InboundProductReqVo reqVo;
        // 入库sku商品
        List<InboundProductReqVo> list = Lists.newArrayList();
        ProductSkuPictures productSkuPicture;
        ProductSkuPurchaseInfo skuPurchaseInfo;
        for (PurchaseOrderProduct product : products) {
            Integer singleCount = product.getSingleCount() == null ? 0 : product.getSingleCount();
            Integer actualSingleCount = product.getActualSingleCount() == null ? 0 : product.getActualSingleCount().intValue();
            if (singleCount - actualSingleCount == 0) {
                continue;
            }
            reqVo = new InboundProductReqVo();
            reqVo.setInboundOderCode(request.getInboundOrderCode());
            reqVo.setSkuCode(product.getSkuCode());
            reqVo.setSkuName(product.getSkuName());
            productSkuPicture = productSkuPicturesDao.getPicInfoBySkuCode(product.getSkuCode());
            if (productSkuPicture != null && StringUtils.isNotBlank(productSkuPicture.getProductPicturePath())) {
                reqVo.setPictureUrl(productSkuPicture.getProductPicturePath());
            }
            reqVo.setNorms(product.getProductSpec());
            reqVo.setColorName(product.getColorName());
            reqVo.setColorCode(null);
            reqVo.setModel(product.getModelNumber());
            reqVo.setInboundNorms(product.getProductSpec());
            skuPurchaseInfo = productSkuPurchaseInfoDao.getInfo(product.getSkuCode());
            if (skuPurchaseInfo != null) {
                reqVo.setUnitCode(skuPurchaseInfo.getUnitCode());
                reqVo.setUnitName(skuPurchaseInfo.getUnitName());
                reqVo.setInboundBaseUnit(skuPurchaseInfo.getZeroRemovalCoefficient() == null ? "" :
                        skuPurchaseInfo.getZeroRemovalCoefficient().toString());
                reqVo.setInboundBaseContent(skuPurchaseInfo.getBaseProductContent().toString());
            }
            Integer purchaseWhole = product.getPurchaseWhole() == null ? 0 : product.getPurchaseWhole().intValue();
            Integer baseProductContent = product.getBaseProductContent() == null ? 0 : product.getBaseProductContent().intValue();
            BigDecimal amount = product.getProductAmount() == null ? big : product.getProductAmount();
            if (actualSingleCount > 0 && baseProductContent > 0) {
                purchaseWhole = purchaseWhole - actualSingleCount / baseProductContent;
            }
            reqVo.setPreInboundMainNum(singleCount.longValue() - actualSingleCount.longValue());
            reqVo.setPreInboundNum(purchaseWhole.longValue());
            reqVo.setPreTaxPurchaseAmount(product.getProductAmount());
            BigDecimal productTotalAmount = product.getProductTotalAmount() == null ? big : product.getProductTotalAmount();
            reqVo.setPreTaxAmount(productTotalAmount);
            reqVo.setLinenum(product.getLinnum().longValue());
            reqVo.setCreateBy(purchaseOrder.getCreateByName());
            reqVo.setCreateTime(Calendar.getInstance().getTime());
            reqVo.setTax(product.getTaxRate());
            preInboundMainNum += reqVo.getPreInboundMainNum();
            preInboundNum += purchaseWhole;
            Integer num = singleCount - actualSingleCount;
            BigDecimal totalAmount = amount.multiply(BigDecimal.valueOf(num)).setScale(4, BigDecimal.ROUND_HALF_UP);
            preTaxAmount = totalAmount.add(preTaxAmount);
            BigDecimal noTax = Calculate.computeNoTaxPrice(totalAmount, product.getTaxRate());
            preNoTaxAmount = noTax.add(preNoTaxAmount);
            list.add(reqVo);
        }
        save.setPreInboundNum(preInboundNum);
        save.setPreMainUnitNum(preInboundMainNum);
        save.setPreTaxAmount(preTaxAmount);
        save.setPreAmount(preNoTaxAmount);
        save.setPreTax(preTaxAmount.subtract(preNoTaxAmount));
        save.setRemark(null);
        save.setList(list);
        return save;
    }

    @Override
   // @Transactional(rollbackFor = Exception.class)
    public HttpResponse getWarehousing(PurchaseStorageRequest purchaseStorage) {
        if (purchaseStorage == null || CollectionUtils.isEmptyCollection(purchaseStorage.getOrderList())) {
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 查询采购单信息
        PurchaseOrder order = new PurchaseOrder();
        order.setPurchaseOrderCode(purchaseStorage.getPurchaseOrderCode());
        PurchaseOrder purchaseOrder = purchaseOrderDao.purchaseOrderInfo(order);

        // 变更采购单、采购商品的实际商品数量,最小单位数量
        List<PurchaseOrderProduct> list = purchaseStorage.getOrderList();
        List<PurchaseOrderProduct> productList = Lists.newArrayList();

        Long actualTotalCount = 0L;
        BigDecimal actualProductAmount = big, actualReturnAmount = big, actualGiftAmount = big;
        for (PurchaseOrderProduct product : list) {
            product.setPurchaseOrderCode(purchaseStorage.getPurchaseOrderCode());
            // 查询对应sku的商品信息
            PurchaseOrderProduct orderProduct = purchaseOrderProductDao.selectPreNumAndPraNumBySkuCodeAndSource(
                    purchaseStorage.getPurchaseOrderCode(), product.getSkuCode(), product.getLinnum());
            Integer actualCount = orderProduct.getActualSingleCount() == null ? 0 : orderProduct.getActualSingleCount().intValue();
            product.setActualSingleCount(actualCount + product.getActualSingleCount());
            Integer count1 = purchaseOrderProductDao.update(product);
            LOGGER.info("采购入库回传，更改采购单商品信息的实际数量失败" + count1);

            // 预计单品数量
            Integer singleCount = orderProduct.getSingleCount() == null ? 0 : orderProduct.getSingleCount().intValue();
            // 实际单品数量
            Integer actualSingleCount = product.getActualSingleCount() == null ? 0 : product.getActualSingleCount().intValue();
            if (singleCount - actualSingleCount > 0) {
                // 判断可以再次入库的商品
                //Integer code = inboundDao.selectMaxPurchaseNumBySourceOderCode(purchaseStorage.getPurchaseOrderCode());
                orderProduct.setActualSingleCount(actualSingleCount);
                productList.add(orderProduct);
            }

            // 计算采购单的实际商品 0商品 1赠品 2实物返回
            // 查询含税单价
            BigDecimal amount = orderProduct.getProductAmount() == null ? BigDecimal.ZERO : orderProduct.getProductAmount();
            if(orderProduct.getProductType().equals(Global.PRODUCT_TYPE_0)) {
                actualProductAmount = amount.add(actualProductAmount);
            }else if(orderProduct.getProductType().equals(Global.PRODUCT_TYPE_1)) {
                actualGiftAmount = amount.add(actualGiftAmount);
            }else {
                actualReturnAmount = amount.add(actualReturnAmount);
            }
            actualTotalCount += actualSingleCount;
        }
        order.setActualTotalCount(actualTotalCount);
        order.setActualProductAmount(actualProductAmount);
        order.setActualGiftAmount(actualGiftAmount);
        order.setActualReturnAmount(actualReturnAmount);
        order.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());

        // 更新采购的批次信息
        if(CollectionUtils.isNotEmptyCollection(purchaseStorage.getBatchList())){
            List<PurchaseBatch> purchaseBatches = Lists.newArrayList();
            for(PurchaseBatch purchaseBatch : purchaseStorage.getBatchList()){
                // 根据批次编号 采购单号确认批次是否存在
                PurchaseBatch batchInfo = purchaseBatchDao.purchaseInfo(purchaseBatch.getBatchInfoCode(),
                        purchaseOrder.getPurchaseOrderCode(), purchaseBatch.getLineCode());
                if(batchInfo != null){
                    batchInfo.setActualTotalCount(batchInfo.getActualTotalCount() + purchaseBatch.getActualTotalCount());
                    batchInfo.setUpdateByName(purchaseBatch.getUpdateByName());
                    batchInfo.setUpdateById(purchaseBatch.getUpdateById());
                    Integer count = purchaseBatchDao.update(batchInfo);
                    LOGGER.info("变更采购单批次参数：" + JsonUtil.toJson(batchInfo)+ "，-条数：", count);
                    continue;
                }
                PurchaseBatch info = BeanCopyUtils.copy(purchaseBatch, PurchaseBatch.class);
                info.setPurchaseOderCode(purchaseStorage.getPurchaseOrderCode());
                purchaseBatches.add(info);
            }
            if(CollectionUtils.isNotEmptyCollection(purchaseBatches)){
                Integer count = purchaseBatchDao.insertAll(purchaseBatches);
                LOGGER.info("添加采购单批次参数：" + JsonUtil.toJson(purchaseBatches) + "，-条数：", count);
            }
        }

        // 减在途数
        //this.wayNum(purchaseOrder, 8);

        // 判断入库次数 、入库是否完成
        purchaseStorage.setPurchaseNum(purchaseStorage.getPurchaseNum() + 1);
        if (purchaseOrder.getInboundLine() > 1 && purchaseStorage.getPurchaseNum() <= purchaseOrder.getInboundLine() &&
                productList.size() >= 1) {
            if(!purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_6)){
                // 变更入库中状态
                order.setPurchaseOrderStatus(Global.PURCHASE_ORDER_6);
                // 添加入库中日志
                log(purchaseOrder.getPurchaseOrderId(), purchaseStorage.getCreateById(), purchaseStorage.getCreateByName(), PurchaseOrderLogEnum.WAREHOUSING_IN.getCode(),
                        PurchaseOrderLogEnum.WAREHOUSING_IN.getName(), purchaseOrder.getApplyTypeForm());
            }
            order.setUpdateByName(purchaseStorage.getCreateById());
            order.setUpdateById(purchaseStorage.getCreateByName());
            Integer count = purchaseOrderDao.update(order);
            LOGGER.error("采购单入库中状态修改" + count);

            String code;
            if (purchaseStorage.getPurchaseNum() <= 9) {
                code = "0" + purchaseStorage.getPurchaseNum();
            } else {
                code = purchaseStorage.getPurchaseNum().toString();
            }
            String inboundOderCode = purchaseStorage.getPurchaseOrderCode() + code;
            purchaseStorage.setInboundOderCode(inboundOderCode);
            // 调用入库单接口， 传送wms
            PurchaseInboundRequest inboundRequest = new PurchaseInboundRequest();
            inboundRequest.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
            inboundRequest.setInboundOrderCode(inboundOderCode);
            inboundRequest.setPurchaseNum(Integer.valueOf(code));
            inboundRequest.setProductList(productList);
            if(CollectionUtils.isEmptyCollection(productList)){
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_PRODUCT_NULL);
            }
            InboundReqSave save = this.InboundReqSave(inboundRequest);
            String s = inboundService.saveInbound(save);
            if (StringUtils.isBlank(s)) {
                LOGGER.error("生成入库单失败....");
                return HttpResponse.failure(ResultCode.SAVE_OUT_BOUND_FAILED);
            }
        } else {
            // 更改入库完成，添加入库完成时间
            order.setPurchaseOrderStatus(Global.PURCHASE_ORDER_8);
            order.setWarehouseTime(Calendar.getInstance().getTime());
            order.setUpdateByName(purchaseStorage.getCreateById());
            order.setUpdateById(purchaseStorage.getCreateByName());
            Integer count = purchaseOrderDao.update(order);
            LOGGER.error("采购单入库完成状态修改" + count);
            // 添加入库完成日志
            log(purchaseOrder.getPurchaseOrderId(), purchaseStorage.getCreateById(), purchaseStorage.getCreateByName(), PurchaseOrderLogEnum.PURCHASE_FINISH.getCode(),
                    PurchaseOrderLogEnum.PURCHASE_FINISH.getName(), purchaseOrder.getApplyTypeForm());
            // 减在途数
//            this.wayNum(purchaseOrder, 8);
        }
        return HttpResponse.success();
    }

    private void log(String purchaseOrderId, String createById, String createByName, Integer code, String name, String remark){
        OperationLog log = new OperationLog();
        log.setOperationId(purchaseOrderId);
        log.setCreateById(createById);
        log.setCreateByName(createByName);
        log.setOperationType(code);
        log.setOperationContent(name);
        log.setRemark(remark);
        if(code.equals(10)){
            Calendar calendar = Calendar.getInstance ();
            calendar.add (Calendar.SECOND, 3);
            log.setCreateTime(calendar.getTime());
        }else {
            log.setCreateTime(Calendar.getInstance().getTime());
        }
        operationLogDao.insertByTime(log);
    }

    @Override
    public HttpResponse<List<Inbound>> receipt(String purchaseOrderCode){
        if(StringUtils.isBlank(purchaseOrderCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<Inbound> inbound = inboundDao.selectTimeAndSatusBySourchAndNum(purchaseOrderCode);
        return HttpResponse.success(inbound);
    }

    // 采购单加减在途数
    private void wayNum(PurchaseOrder order, Integer type){
        ChangeStockRequest request = new ChangeStockRequest();
        request.setOperationType(type);
        List<StockInfoRequest> list = Lists.newArrayList();
        StockInfoRequest stockInfo;
        // 查询入库单号
        Inbound inbound = inboundDao.inboundCodeOrderLast(order.getPurchaseOrderCode());
        // 查询该采购单的商品
        List<PurchaseOrderProduct> products = purchaseOrderProductDao.orderProductByGroup(order.getPurchaseOrderId());
        if(CollectionUtils.isNotEmptyCollection(products)){
            for(PurchaseOrderProduct product:products){
                long singleCount =  product.getSingleCount() == null ? 0 : product.getSingleCount().longValue();
                long actualSingleCount =  product.getActualSingleCount() == null ? 0 : product.getActualSingleCount().longValue();
//                if(singleCount - actualSingleCount == 0){
//                    continue;
//                }
                stockInfo = new StockInfoRequest();
                stockInfo.setCompanyCode(order.getCompanyCode());
                stockInfo.setCompanyName(order.getCompanyName());
                stockInfo.setTransportCenterCode(order.getTransportCenterCode());
                stockInfo.setTransportCenterName(order.getTransportCenterName());
                stockInfo.setWarehouseCode(order.getWarehouseCode());
                stockInfo.setWarehouseName(order.getWarehouseName());
                stockInfo.setWarehouseType(1);
                stockInfo.setSkuCode(product.getSkuCode());
                stockInfo.setSkuName(product.getSkuName());
                stockInfo.setTaxRate(product.getTaxRate());
                stockInfo.setDocumentCode(inbound.getInboundOderCode());
                stockInfo.setDocumentType(1);
                stockInfo.setSourceDocumentCode(order.getPurchaseOrderCode());
                stockInfo.setSourceDocumentType(3);
                stockInfo.setOperatorId(order.getCreateById());
                stockInfo.setOperatorName(order.getCreateByName());
                // 加在途
                if(type == 7){
                    stockInfo.setChangeCount(singleCount);
                }else {
                    // 减在途并加库存
                    stockInfo.setChangeCount(singleCount - actualSingleCount);
                    if(order.getInboundLine() == inbound.getPurchaseNum() || singleCount == actualSingleCount){
                        stockInfo.setPreWayCount(singleCount - actualSingleCount);
                    }else {
                        InboundProduct inboundProduct = inboundProductDao.inboundByLineCode(inbound.getInboundOderCode(), product.getSkuCode(), product.getLinnum().longValue());
                        stockInfo.setPreWayCount(inboundProduct.getPraInboundMainNum());
                    }
                }
                list.add(stockInfo);
            }
            request.setStockList(list);
            stockService.stockAndBatchChange(request);
        }
    }

    @Override
    public HttpResponse<PurchaseApplyDetailResponse> receiptProduct(String purchaseOrderCode, Integer purchaseNum, Integer pageNo, Integer pageSize){
        if(StringUtils.isBlank(purchaseOrderCode) || purchaseNum == null){
            return HttpResponse.success(new PageResData<>());
        }
        Inbound inbound = new Inbound();
        inbound.setPageSize(pageSize);
        inbound.setPageNo(pageNo);
        inbound.setSourceOderCode(purchaseOrderCode);
        inbound.setPurchaseNum(purchaseNum);
        List<PurchaseApplyDetailResponse> list = inboundProductDao.selectPurchaseInfoByPurchaseNum(inbound);
        // 查询对应采购数据
        if(CollectionUtils.isNotEmptyCollection(list)){
            for(PurchaseApplyDetailResponse product:list){
                // 查询厂商sku
                String factorySkuCode = productSkuSupplyUnitDao.getFactorySkuCode(product.getSkuCode(), product.getSupplierCode());
                if(StringUtils.isNotBlank(factorySkuCode)){
                    product.setFactorySkuCode(factorySkuCode);
                }
                if(product != null){
                    PurchaseApplyDetailResponse orderProduct = purchaseOrderProductDao.warehousingInfo(product.getSourceOderCode(), product.getLinenum().intValue());
                    if(orderProduct != null){
                        Integer actualSingleCount = product.getActualSingleCount() == null ? 0 : product.getActualSingleCount();
                        BigDecimal productAmount = orderProduct.getProductAmount() == null ? big : orderProduct.getProductAmount();
                        BeanUtils.copyProperties(orderProduct, product);
                        product.setActualSingleCount(actualSingleCount);
                        product.setActualTaxSum(productAmount.multiply(BigDecimal.valueOf(actualSingleCount)).setScale(4, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
        }
        Integer count = inboundProductDao.countPurchaseInfoByPurchaseNum(inbound);
        return HttpResponse.success(new PageResData<>(count, list));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse addLog(OperationLog operationLog){
        if(operationLog == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        operationLogDao.insert(operationLog);
        return HttpResponse.success();
    }

    @Override
    public  HttpResponse<PurchaseFormResponse> skuSupply(String skuCode, String transportCenterCode, String warehouseCode,
                                                         String settlementMethodCode, String purchaseGroupCode){
        if(StringUtils.isBlank(skuCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseFormResponse> purchaseFormResponses = productSkuSupplyUnitDao.supplyList(skuCode);
        if(CollectionUtils.isNotEmptyCollection(purchaseFormResponses)){
            PurchaseApplyRequest request;
            for (PurchaseFormResponse response:purchaseFormResponses){
                request = new PurchaseApplyRequest();
                request.setSkuCode(response.getSkuCode());
                request.setSupplierCode(response.getSupplierCode());
                request.setTransportCenterCode(transportCenterCode);
                request.setSettlementMethodCode(settlementMethodCode);
                request.setWarehouseCode(warehouseCode);
                request.setPurchaseGroupCode(purchaseGroupCode);
                List<PurchaseApplyDetailResponse> responses = purchaseApplyService.productInfo(request);
                response.setSkuData(responses.get(0));
            }
        }
        return HttpResponse.success(purchaseFormResponses);
    }

    @Override
    public HttpResponse purchaseOrderPre(String purchaseGroupCode, Integer purchaseOrderTypeCode, String purchaseOrderCode){
        if(StringUtils.isBlank(purchaseGroupCode) || purchaseOrderTypeCode == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<String> list = purchaseOrderDao.getPurchaseOrderPre(purchaseGroupCode, purchaseOrderTypeCode, purchaseOrderCode);
        return HttpResponse.success(list);
    }

    @Override
    public HttpResponse<PageResData<PurchaseBatch>> purchaseOrderBatch(PurchaseOrderProductRequest request){
        List<PurchaseBatch> list = purchaseBatchDao.list(request);
        Integer count = purchaseBatchDao.listCount(request);
        return HttpResponse.successGenerics(new PageResData<>(count, list));
    }

}
