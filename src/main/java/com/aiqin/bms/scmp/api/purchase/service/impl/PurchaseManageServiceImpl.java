package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.common.PurchaseOrderLogEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.InboundDao;
import com.aiqin.bms.scmp.api.product.dao.InboundProductDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuInspReportDao;
import com.aiqin.bms.scmp.api.product.dao.ProductSkuPurchaseInfoDao;
import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPurchaseInfo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseFormResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseOrderResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.purchase.PurchaseCountAmountResponse;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApprovalService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.service.SupplierScoreService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-14 17:49
 **/
@Service
public class PurchaseManageServiceImpl implements PurchaseManageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseManageServiceImpl.class);

    @Resource
    private PurchaseApplyDao purchaseApplyDao;
    @Resource
    private PurchaseApplyProductDao purchaseApplyProductDao;
    @Resource
    private PurchaseOrderDao purchaseOrderDao;
    @Resource
    private PurchaseOrderProductDao purchaseOrderProductDao;
    @Resource
    private PurchaseOrderDetailsDao purchaseOrderDetailsDao;
    @Resource
    private EncodingRuleDao encodingRuleDao;
    @Resource
    private FileRecordDao fileRecordDao;
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
    private SupplierScoreService scoreService;
    @Resource
    private ProductSkuInspReportDao productSkuInspReportDao;
    @Resource
    private PurchaseApprovalService purchaseApprovalService;

    @Override
    public HttpResponse selectPurchaseForm(List<String> applyIds){
        if(CollectionUtils.isEmptyCollection(applyIds)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 查询要生成采购单的信息
        List<PurchaseFormResponse> purchaseForms = purchaseApplyProductDao.selectPurchaseForm(applyIds);
        if(CollectionUtils.isNotEmptyCollection(purchaseForms)){
            PurchaseFormRequest apply = null;
            for(PurchaseFormResponse form:purchaseForms){
                apply = new PurchaseFormRequest();
                apply.setPurchaseGroupCode(form.getPurchaseGroupCode());
                apply.setSupplierCode(form.getSupplierCode());
                apply.setTransportCenterCode(form.getTransportCenterCode());
                apply.setWarehouseCode(form.getWarehouseCode());
                apply.setApplyIds(applyIds);
                List<PurchaseApplyDetailResponse> details = purchaseApplyProductDao.purchaseFormProduct(apply);
                // 计算sku数量，单品数量，含税采购金额，实物返金额
                Integer singleCount = 0, productTotalAmount = 0, returnAmount = 0;
                if(CollectionUtils.isNotEmptyCollection(details)){
                    for(PurchaseApplyDetailResponse detail:details){
                        Integer purchaseWhole = detail.getPurchaseWhole() == null ? 0 : detail.getPurchaseWhole();
                        Integer purchaseSingle = detail.getPurchaseSingle() == null ? 0 : detail.getPurchaseSingle();
                        Integer packNumber = detail.getBaseProductContent() == null ? 0 : detail.getBaseProductContent();
                        Integer amount = detail.getProductPurchaseAmount() == null ? 0 : detail.getProductPurchaseAmount();
                        Integer number = purchaseWhole * packNumber + purchaseSingle;
                        Integer amountSum = number * amount;
                        // 单品数量
                        singleCount += number;
                        // 含税采购金额
                        productTotalAmount += amountSum;
                        // 实物返金额
                        if(detail.getProductType().equals(Global.PRODUCT_TYPE_2)){
                            returnAmount +=  amountSum;
                        }
                    }
                }
                form.setProductTotalAmount(productTotalAmount);
                form.setReturnAmount(returnAmount);
                form.setSingleCount(singleCount);
                // sku数量
                Integer skuCount = purchaseApplyProductDao.formSkuCount(apply);
                form.setSkuCount(skuCount);
            }
        }
        return HttpResponse.success(purchaseForms);
    }

    @Override
    public HttpResponse purchaseApplyList(PurchaseFormRequest purchaseFormRequest){
        if(purchaseFormRequest == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 查询采购申请单商品列表
        List<PurchaseFormResponse> forms = purchaseApplyProductDao.applyByProduct(purchaseFormRequest);
        if(CollectionUtils.isNotEmptyCollection(forms)){
            for(PurchaseFormResponse form:forms){
                purchaseFormRequest.getApplyIds().clear();
                purchaseFormRequest.getApplyIds().add(form.getPurchaseApplyId());
                List<PurchaseApplyDetailResponse> formDetails = purchaseApplyProductDao.purchaseFormProduct(purchaseFormRequest);
                Integer singleCount = 0, productTotalAmount = 0, returnAmount =0;
                for(PurchaseApplyDetailResponse apply:formDetails){
                    // 计算单品数量， 采购含税金额， 实物返金额
                    Integer purchaseWhole = apply.getPurchaseWhole() == null ? 0 : apply.getPurchaseWhole();
                    Integer purchaseSingle = apply.getPurchaseSingle() == null ? 0 : apply.getPurchaseSingle();
                    Integer packNumber = apply.getBaseProductContent() == null ? 0 : apply.getBaseProductContent();
                    Integer amount = apply.getProductPurchaseAmount() == null ? 0 : apply.getProductPurchaseAmount();
                    Integer number = purchaseWhole * packNumber + purchaseSingle;
                    singleCount += number;
                    productTotalAmount += number * amount;
                    if(apply.getProductType().equals(Global.PRODUCT_TYPE_2)){
                        returnAmount += number * amount;
                    }
                }
                form.setSingleCount(singleCount);
                form.setProductTotalAmount(productTotalAmount);
                form.setReturnAmount(returnAmount);
                // 计算选中sku数量
                Integer count = purchaseApplyProductDao.formSkuCount(purchaseFormRequest);
                form.setSkuCount(count);
            }
        }
        return HttpResponse.success(forms);
    }

    @Override
    public HttpResponse purchaseProductList(PurchaseFormRequest purchaseFormRequest){
        if(purchaseFormRequest == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        PageResData pageResData = new PageResData();
        List<PurchaseApplyDetailResponse> details = purchaseApplyProductDao.purchaseFormList(purchaseFormRequest);
        // 提交采购单页面商品列表
        if(CollectionUtils.isNotEmptyCollection(details)){
            for(PurchaseApplyDetailResponse detail:details){
                // 计算单品数量， 含税总价
                Integer purchaseWhole = detail.getPurchaseWhole() == null ? 0 : detail.getPurchaseWhole();
                Integer purchaseSingle = detail.getPurchaseSingle() == null ? 0 : detail.getPurchaseSingle();
                Integer packNumber = detail.getBaseProductContent() == null ? 0 : detail.getBaseProductContent();
                Integer amount = detail.getProductPurchaseAmount() == null ? 0 : detail.getProductPurchaseAmount();
                Integer number = purchaseWhole * packNumber + purchaseSingle;
                detail.setSingleCount(number);
                detail.setProductAmount(amount);
                detail.setProductTotalAmount(number * amount);
            }
        }
        pageResData.setDataList(details);
        Integer count = purchaseApplyProductDao.purchaseFormCount(purchaseFormRequest);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse purchaseOrder(PurchaseOrderRequest purchaseOrderRequest){
        if(purchaseOrderRequest == null || purchaseOrderRequest.getPurchaseOrder() == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 获取采购单编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.PURCHASE_ORDER_CODE);
        // 采购申请单id
        String purchaseId = IdUtil.purchaseId();
        PurchaseOrder purchaseOrder = purchaseOrderRequest.getPurchaseOrder();
        purchaseOrder.setPurchaseOrderId(purchaseId);
        String purchaseProductCode = "CG" + String.valueOf(encodingRule.getNumberingValue());
        purchaseOrder.setPurchaseOrderId(purchaseId);
        purchaseOrder.setPurchaseOrderCode(purchaseProductCode);
        purchaseOrder.setInfoStatus(Global.PURCHASE_APPLY_STATUS_0);
        purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_0);
        purchaseOrder.setCreateById(purchaseOrderRequest.getPersonId());
        purchaseOrder.setCreateByName(purchaseOrderRequest.getPersonName());
        // 添加采购单
        Integer orderCount = purchaseOrderDao.insert(purchaseOrder);
        if(orderCount > 0){
            // 添加操作日志
            log(purchaseId, purchaseOrderRequest.getPersonId(), purchaseOrderRequest.getPersonName(),
                    PurchaseOrderLogEnum.INSERT_ORDER.getCode(), PurchaseOrderLogEnum.INSERT_ORDER.getName(), null);
            log(purchaseId, purchaseOrderRequest.getPersonId(), purchaseOrderRequest.getPersonName(),
                    PurchaseOrderLogEnum.CHECKOUT_STAY.getCode(), PurchaseOrderLogEnum.CHECKOUT_STAY.getName(), null);
            encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());
            // 添加采购单详情
            PurchaseOrderDetails details = purchaseOrderRequest.getOrderDetails();
            details.setPurchaseDetailsId(IdUtil.purchaseId());
            details.setPurchaseOrderId(purchaseId);
            details.setPurchaseOrderCode(purchaseProductCode);
            details.setDetailsStatus(Global.USER_ON);
            details.setOrderType("配送");
            purchaseOrderDetailsDao.insert(details);
            // 添加商品列表
            PurchaseFormRequest form = new PurchaseFormRequest();
            if(CollectionUtils.isNotEmptyCollection(purchaseOrderRequest.getApplyIds())){
                form.setApplyIds(purchaseOrderRequest.getApplyIds());
                PurchaseOrder order = purchaseOrderRequest.getPurchaseOrder();
                form.setWarehouseCode(order.getWarehouseCode());
                form.setTransportCenterCode(order.getTransportCenterCode());
                form.setSupplierCode(order.getSupplierCode());
                form.setPurchaseGroupCode(order.getPurchaseGroupCode());
                form.setSettlementMethodCode(order.getSettlementMethodCode());
            }
            this.insertProduct(form, purchaseId, purchaseProductCode, purchaseOrderRequest);
            // 添加文件信息
            List<FileRecord> fileList = purchaseOrderRequest.getFileList();
            if(CollectionUtils.isNotEmptyCollection(fileList)){
                fileRecordDao.insertAll(purchaseId, fileList);
            }
            //  判断所有采购申请单的完成状态
            List<String> applyIds = purchaseOrderRequest.getApplyIds();
            PurchaseApply purchaseApply = null;
            for(String apply:applyIds){
                // 查询申请单的所有未提交的sku数量
                Integer applySkuSum = purchaseApplyProductDao.skuCount(apply, 0);
                // 计算该次提交申请单的未提交的选中数量
                form.getApplyIds().clear();
                form.getApplyIds().add(apply);
                Integer skuCount = purchaseApplyProductDao.formSkuCount(form);
                if(applySkuSum <= skuCount){
                    // 申请单状态变为完成
                    purchaseApply.setPurchaseApplyId(apply);
                    purchaseApply.setApplyStatus(Global.PURCHASE_APPLY_STATUS_1);
                    purchaseApply.setUpdateById(purchaseOrderRequest.getPersonId());
                    purchaseApply.setUpdateByName(purchaseOrderRequest.getPersonName());
                    purchaseApplyDao.update(purchaseApply);
                }
                // 变更提交采购申请单的完成状态
                form.setPurchaseApplyId(apply);
                form.setUpdateById(purchaseOrderRequest.getPersonId());
                form.setUpdateByName(purchaseOrderRequest.getPersonName());
                purchaseApplyProductDao.updateInfoStatus(form);
            }
            // 调审批流
            purchaseApprovalService.workFlow(purchaseProductCode, purchaseOrderRequest.getPersonName(), details.getDirectSupervisorCode());
        }
        return HttpResponse.success();
    }

    private void insertProduct(PurchaseFormRequest form, String purchaseId, String purchaseProductCode, PurchaseOrderRequest purchaseOrderRequest) {
        List<PurchaseApplyDetailResponse> details = purchaseApplyProductDao.purchaseFormProduct(form);
        // 提交采购单页面商品列表
        if (CollectionUtils.isNotEmptyCollection(details)) {
            List<PurchaseOrderProduct> list = new ArrayList<>();
            PurchaseOrderProduct orderProduct = null;
            for (PurchaseApplyDetailResponse detail : details) {
                orderProduct = new PurchaseOrderProduct();
                // 计算单品数量， 含税总价
                Integer purchaseWhole = detail.getPurchaseWhole() == null ? 0 : detail.getPurchaseWhole();
                Integer purchaseSingle = detail.getPurchaseSingle() == null ? 0 : detail.getPurchaseSingle();
                Integer packNumber = detail.getBaseProductContent() == null ? 0 : detail.getBaseProductContent();
                Integer amount = detail.getProductPurchaseAmount() == null ? 0 : detail.getProductPurchaseAmount();
                Integer number = purchaseWhole * packNumber + purchaseSingle;
                orderProduct.setOrderProductId(IdUtil.purchaseId());
                orderProduct.setPurchaseOrderId(purchaseId);
                orderProduct.setPurchaseOrderCode(purchaseProductCode);
                orderProduct.setSkuCode(detail.getSkuCode());
                orderProduct.setSkuName(detail.getSkuName());
                orderProduct.setBrandId(detail.getBrandId());
                orderProduct.setBrandName(detail.getBrandName());
                orderProduct.setCategoryId(detail.getCategoryId());
                orderProduct.setCategoryName(detail.getCategoryName());
                orderProduct.setProductSpec(detail.getProductSpec());
                orderProduct.setColorName(detail.getColorName());
                orderProduct.setModelNumber(detail.getModelNumber());
                orderProduct.setProductType(detail.getProductType());
                orderProduct.setPurchaseWhole(detail.getPurchaseWhole());
                orderProduct.setPurchaseSingle(detail.getPurchaseSingle());
                orderProduct.setBaseProductContent(detail.getBaseProductContent());
                orderProduct.setBoxGauge(detail.getBoxGauge());
                orderProduct.setSingleCount(number);
                orderProduct.setTaxRate(detail.getTaxRate());
                orderProduct.setProductAmount(amount);
                orderProduct.setProductTotalAmount(amount * number);
                orderProduct.setStockCount(detail.getStockCount());
                orderProduct.setCreateById(purchaseOrderRequest.getPersonId());
                orderProduct.setCreateByName(purchaseOrderRequest.getPersonName());
                list.add(orderProduct);
            }
            purchaseOrderProductDao.insertAll(list);
        }
    }

    @Override
    public HttpResponse purchaseOrderList(PurchaseApplyRequest purchaseApplyRequest){
        PageResData pageResData = new PageResData();
        List<PurchaseOrderResponse> list = purchaseOrderDao.purchaseOrderList(purchaseApplyRequest);
        if(CollectionUtils.isNotEmptyCollection(list)){
            for(PurchaseOrderResponse order:list){
                // 计算实际单品数量，实际含税采购金额， 实际实物返金额
                Integer actualSingleCount = 0, actualTotalAmount = 0, actualReturnAmount = 0;
                List<PurchaseOrderProduct> orderProducts = purchaseOrderProductDao.orderProductInfo(order.getPurchaseOrderId());
                if(CollectionUtils.isNotEmptyCollection(orderProducts)){
                    for(PurchaseOrderProduct product:orderProducts){
                        Integer singleCount = product.getActualSingleCount() == null ? 0:product.getActualSingleCount();
                        Integer productAmount = product.getProductAmount() == null ?0:product.getProductAmount();
                        actualSingleCount += singleCount;
                        actualTotalAmount += productAmount * singleCount;
                        if(product.getProductType().equals(Global.PRODUCT_TYPE_2)){
                            actualReturnAmount += productAmount * singleCount;
                        }
                    }
                }
                order.setActualSingleCount(actualSingleCount);
                order.setActualTotalAmount(actualTotalAmount);
                order.setActualReturnAmount(actualReturnAmount);
            }
        }
        Integer count = purchaseOrderDao.purchaseOrderCount(purchaseApplyRequest);
        pageResData.setDataList(list);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse cancelPurchaseOrder(PurchaseOrder purchaseOrder){
        if(purchaseOrder == null || StringUtils.isBlank(purchaseOrder.getPurchaseOrderId())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        purchaseOrder.setUpdateByName(purchaseOrder.getCreateByName());
        purchaseOrder.setUpdateById(purchaseOrder.getCreateById());
        Integer count = purchaseOrderDao.update(purchaseOrder);
        if(count == 0){
            LOGGER.error("变更采购单的状态失败......");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        // 添加操作日志
        String purchaseOrderId = purchaseOrder.getPurchaseOrderId();
        String createById = purchaseOrder.getCreateById();
        String createByName = purchaseOrder.getCreateByName();
        PurchaseOrderDetails detail;
        if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_2)){
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.STOCK_UP.getCode(),
                    PurchaseOrderLogEnum.STOCK_UP.getName() , null);
        }else if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_3)){
            detail = new PurchaseOrderDetails();
            detail.setPurchaseOrderId(purchaseOrderId);
            detail.setDeliveryTime(Calendar.getInstance().getTime());
            detail.setUpdateById(createById);
            detail.setUpdateByName(createByName);
            purchaseOrderDetailsDao.update(detail);
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.DELIVER_GOODS.getCode(),
                    PurchaseOrderLogEnum.DELIVER_GOODS.getName() , null);
        }else if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_6)){
            detail = new PurchaseOrderDetails();
            detail.setPurchaseOrderId(purchaseOrderId);
            detail.setWarehouseTime(Calendar.getInstance().getTime());
            detail.setUpdateById(createById);
            detail.setUpdateByName(createByName);
            purchaseOrderDetailsDao.update(detail);
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.WAREHOUSING_FINISH.getCode(),
                    PurchaseOrderLogEnum.WAREHOUSING_FINISH.getName() , null);
        }else if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_5)){
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.WAREHOUSING_BEGIN.getCode(),
                    PurchaseOrderLogEnum.WAREHOUSING_BEGIN.getName() , null);
        }else if(purchaseOrder.getStorageStatus() != null && purchaseOrder.getStorageStatus().equals(Global.STORAGE_STATUS_1)){
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.STORAGE_STAY.getCode(),
                    PurchaseOrderLogEnum.STORAGE_STAY.getName() , null);
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<PurchaseApplyDetailResponse> purchaseOrderDetails(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        PurchaseApplyDetailResponse detail = purchaseOrderDetailsDao.purchaseOrderDetail(purchaseOrderId);
        return HttpResponse.success(detail);
    }

    @Override
    public HttpResponse purchaseOrderProduct(PurchaseOrderProductRequest request){
        if(StringUtils.isBlank(request.getPurchaseOrderId())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        PageResData pageResData = new PageResData();
        List<PurchaseOrderProduct> list = purchaseOrderProductDao.purchaseOrderList(request);
        Integer count = purchaseOrderProductDao.purchaseOrderCount(request);
        pageResData.setDataList(list);
        pageResData.setTotalCount(count);
        return HttpResponse.success(pageResData);
    }

    @Override
    public HttpResponse purchaseOrderFile(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<FileRecord> files = fileRecordDao.fileList(purchaseOrderId);
        return HttpResponse.success(files);
    }

    @Override
    public HttpResponse purchaseOrderLog(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<OperationLog> list = operationLogDao.list(purchaseOrderId);
        return HttpResponse.success(list);
    }

    @Override
    public HttpResponse<PurchaseCountAmountResponse> purchaseOrderAmount(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 计算采购单的数量与金额
        PurchaseCountAmountResponse amountResponse = new PurchaseCountAmountResponse();
        Integer productCount = 0, singleCount = 0, returnCount = 0;
        Integer taxAmount = 0, notTaxAmount = 0, returnAmount = 0;
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
                Integer amount = order.getProductTotalAmount() == null ? 0 : order.getProductTotalAmount();
                productCount += purchaseWhole;
                Integer number = purchaseWhole * packNumber + purchaseSingle;
                singleCount += number;
                taxAmount += amount;
                notTaxAmount += taxAmount/(1 + order.getTaxRate());
                if(order.getProductType().equals(Global.PRODUCT_TYPE_2)){
                    returnCount += number;
                    returnAmount += amount;
                }
            }
            amountResponse.setProductCount(productCount);
            amountResponse.setSingleCount(singleCount);
            amountResponse.setReturnCount(returnCount);
            amountResponse.setTaxAmount(taxAmount);
            amountResponse.setNotTaxAmount(notTaxAmount);
            amountResponse.setReturnAmount(returnAmount);
        }
        this.actualCountAndAmount(purchaseOrderId);
        return HttpResponse.success(amountResponse);
    }

    // 计算采购的实际数量金额
    private void actualCountAndAmount(String purchaseOrderId){
        // 查询所有入库单的商品
        List<PurchaseApplyDetailResponse> responses = inboundProductDao.purchaseInboundProduct(purchaseOrderId);
        if(CollectionUtils.isNotEmptyCollection(responses)){
            PurchaseCountAmountResponse amountResponse = new PurchaseCountAmountResponse();
            Integer actualProductCount = 0, actualSingleCount = 0, actualReturnCount = 0;
            Integer actualNotTaxAmount = 0, actualTaxAmount = 0, actualReturnAmount = 0;
            for(PurchaseApplyDetailResponse product:responses){
                if(product != null){
                    PurchaseApplyDetailResponse info = purchaseOrderProductDao.warehousingInfo(product.getSourceOderCode(), product.getSkuCode());
                    Integer packNumber = info.getBaseProductContent() == null ? 0 : info.getBaseProductContent();
                    Integer purchaseWhole = info.getPurchaseWhole() == null ? 0 : info.getPurchaseWhole();
                    Integer singleCount = product.getActualSingleCount() == null ? 0: product.getActualSingleCount();
                    actualProductCount += purchaseWhole;
                    actualSingleCount += singleCount;
                    actualTaxAmount += packNumber * singleCount;
                    actualNotTaxAmount += actualTaxAmount/(1 + info.getTaxRate());
                    if(info.getProductType().equals(Global.PRODUCT_TYPE_2)){
                        actualReturnCount += singleCount;
                        actualReturnAmount += actualTaxAmount;
                    }

                }
            }
            amountResponse.setActualProductCount(actualProductCount);
            amountResponse.setActualSingleCount(actualSingleCount);
            amountResponse.setActualReturnCount(actualReturnCount);
            amountResponse.setActualTaxAmount(actualTaxAmount);
            amountResponse.setActualNotTaxAmount(actualNotTaxAmount);
            amountResponse.setActualReturnAmount(actualReturnAmount);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse purchaseOrderStock(String purchaseOrderId, String createById, String createByName){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 查询采购单的详情
        PurchaseOrder purchaseOrder = purchaseOrderDao.purchaseOrder(purchaseOrderId);
        if(purchaseOrder == null){
            LOGGER.info("未查询到采购单的信息" + purchaseOrderId);
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
        // 变更采购单的状态（开始备货）
        if(!purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_2)) {
            LOGGER.error("该采购单未审核通过， 不能开始备货");
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_CHECK);
        }
        PurchaseOrder order = new PurchaseOrder();
        order.setPurchaseOrderStatus(Global.PURCHASE_ORDER_3);
        order.setPurchaseOrderId(purchaseOrderId);
        Integer count = purchaseOrderDao.update(order);
        if(count == 0){
            LOGGER.error("采购单开始备货状态修改失败");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        // 开始备货， 生成入库单
        InboundReqSave save = new InboundReqSave();
        save.setSourceOderCode(purchaseOrderId);
        save.setInboundTypeCode(InboundTypeEnum.RETURN_SUPPLY.getCode());
        save.setInboundTypeName(InboundTypeEnum.RETURN_SUPPLY.getName());
        save.setWarehouseCode(purchaseOrder.getWarehouseCode());
        save.setWarehouseName(purchaseOrder.getWarehouseName());
        save.setPurchaseNum(1);
        InboundProductReqVo inboundProductReqVo;
        // 入库sku商品
        List<InboundProductReqVo> list = save.getList();
        // 查询是否有商品可以入库
        PurchaseOrderProductRequest request = new PurchaseOrderProductRequest();
        request.setPurchaseOrderId(purchaseOrderId);
        request.setIsPage(1);
        List<PurchaseOrderProduct> products = purchaseOrderProductDao.purchaseOrderList(request);
        if(CollectionUtils.isNotEmptyCollection(products)){
            for(PurchaseOrderProduct product:products){
                // 判断入库实际单品数量是否等于采购欧单品数量
                inboundProductReqVo = new InboundProductReqVo();
                inboundProductReqVo.setSkuCode(product.getSkuCode());
                inboundProductReqVo.setSkuName(product.getSkuName());
                Integer singleCount = product.getSingleCount() == null ? 0 : product.getSingleCount();
                inboundProductReqVo.setPreInboundMainNum(singleCount.longValue());
                inboundProductReqVo.setPreInboundNum(singleCount.longValue());
                ProductSkuPurchaseInfo info = productSkuPurchaseInfoDao.getInfo(product.getSkuCode());
                if(info != null){
                    inboundProductReqVo.setUnitCode(info.getUnitCode());
                    inboundProductReqVo.setUnitName(info.getUnitName());
                }
                inboundProductReqVo.setLinenum(product.getId());
                list.add(inboundProductReqVo);
            }
        }
        save.setList(list);
        String s = inboundService.saveInbound(save);
        if(StringUtils.isBlank(s)){
            LOGGER.error("生成入库单失败....");
            return HttpResponse.failure(ResultCode.SAVE_OUT_BOUND_FAILED);
        }
        String name = "入库申请单"+ s + "，入库完成";
        log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.WAREHOUSING_FINISH.getCode(),
                name , null);
        return HttpResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse getWarehousing(List<PurchaseOrderProduct> list){
        if(CollectionUtils.isEmptyCollection(list)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 变更采购的入库的实际单品数量
        for(PurchaseOrderProduct product:list){
            Integer count = purchaseOrderProductDao.update(product);
            if(count == 0){
                return HttpResponse.failure(ResultCode.UPDATE_ERROR);
            }
        }
        String purchaseOrderId = list.get(0).getPurchaseOrderId();
        PurchaseOrder purchaseOrder = purchaseOrderDao.purchaseOrder(purchaseOrderId);
        InboundReqSave save = new InboundReqSave();
        save.setSourceOderCode(purchaseOrderId);
        save.setInboundTypeCode(InboundTypeEnum.RETURN_SUPPLY.getCode());
        save.setInboundTypeName(InboundTypeEnum.RETURN_SUPPLY.getName());
        save.setWarehouseCode(purchaseOrder.getWarehouseCode());
        save.setWarehouseName(purchaseOrder.getWarehouseName());
        // 查询入库次数
        Integer code = inboundDao.selectMaxPurchaseNumBySourceOderCode(purchaseOrderId);
        save.setPurchaseNum(code + 1);
        InboundProductReqVo inboundProductReqVo;
        // 入库sku商品
        List<InboundProductReqVo> inboundList = save.getList();
        // 查询是否有商品可以入库
        PurchaseOrderProductRequest request = new PurchaseOrderProductRequest();
        request.setPurchaseOrderId(purchaseOrderId);
        request.setIsPage(1);
        List<PurchaseOrderProduct> products = purchaseOrderProductDao.purchaseOrderList(request);
        if(CollectionUtils.isNotEmptyCollection(products)) {
            for (PurchaseOrderProduct product : products) {
                if(product != null){
                    Integer singleCount = product.getSingleCount() == null ? 0 : product.getSingleCount();
                    Integer actualSingleCount = product.getActualSingleCount() == null ? 0 : product.getActualSingleCount();
                    // 判断入库实际单品数量是否小于采购欧单品数量
                    if(actualSingleCount < singleCount){
                        inboundProductReqVo = new InboundProductReqVo();
                        inboundProductReqVo.setSkuCode(product.getSkuCode());
                        inboundProductReqVo.setSkuName(product.getSkuName());
                        inboundProductReqVo.setPreInboundMainNum(singleCount.longValue());
                        inboundProductReqVo.setPreInboundNum(singleCount.longValue() - actualSingleCount.longValue());
                        ProductSkuPurchaseInfo info = productSkuPurchaseInfoDao.getInfo(product.getSkuCode());
                        if(info != null){
                            inboundProductReqVo.setUnitCode(info.getUnitCode());
                            inboundProductReqVo.setUnitName(info.getUnitName());
                        }
                        inboundProductReqVo.setLinenum(product.getId());
                        inboundList.add(inboundProductReqVo);
                    }
                }
            }
        }
        save.setList(inboundList);
        // 是否入库完成
        if(inboundList.size() <= 0){
            PurchaseOrder order = new PurchaseOrder();
            order.setPurchaseOrderStatus(Global.PURCHASE_ORDER_7);
            order.setPurchaseOrderId(purchaseOrderId);
            Integer count = purchaseOrderDao.update(order);
            if(count == 0){
                LOGGER.error("采购单入库状态修改失败");
                return HttpResponse.failure(ResultCode.UPDATE_ERROR);
            }
            // 添加日志
            log(purchaseOrderId, list.get(0).getCreateById(), list.get(0).getCreateByName(), PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getCode(),
                    PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getName() , "自动");
        }else {
            String s = inboundService.saveInbound(save);
            if(StringUtils.isBlank(s)){
                LOGGER.error("生成入库单失败....");
                return HttpResponse.failure(ResultCode.SAVE_OUT_BOUND_FAILED);
            }
            String name = "入库申请单"+ s + "，入库完成";
            log(purchaseOrderId, list.get(0).getCreateById(), list.get(0).getCreateByName(), PurchaseOrderLogEnum.WAREHOUSING_FINISH.getCode(),
                    name , null);
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
        operationLogDao.insert(log);
    }

    @Override
    public HttpResponse reportSku(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseOrderProduct> list = purchaseOrderProductDao.orderBySku(purchaseOrderId);
        return HttpResponse.success(list);
    }

    @Override
    public HttpResponse<List<Inbound>> receipt(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<Inbound> inbound = inboundDao.selectTimeAndSatusBySourchAndNum(purchaseOrderId);
        return HttpResponse.success(inbound);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse storageConfirm(PurchaseStorageRequest storageRequest){
        if(StringUtils.isBlank(storageRequest.getPurchaseOrderId())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        String purchaseOrderId = storageRequest.getPurchaseOrderId();
        PurchaseOrder order = purchaseOrderDao.purchaseOrder(purchaseOrderId);
        if(!order.getStorageStatus().equals(Global.STORAGE_STATUS_1)){
            LOGGER.info("采购仓储状态非确认中状态， 不能确认");
            return HttpResponse.failure(ResultCode.STORAGE_NOT_CONFIRM);
        }
        // 变更采购单的状态
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPurchaseOrderId(purchaseOrderId);
        purchaseOrder.setStorageStatus(Global.STORAGE_STATUS_2);
        purchaseOrder.setUpdateById(storageRequest.getCreateById());
        purchaseOrder.setUpdateByName(storageRequest.getCreateByName());
        Integer count = purchaseOrderDao.update(purchaseOrder);
        if(count == 0){
            LOGGER.error("变更采购单的仓储状态失败......");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        // 保存质检报告
        if(CollectionUtils.isNotEmptyCollection(storageRequest.getReportRequest())){
            productSkuInspReportDao.insertInspReportList(storageRequest.getReportRequest());
        }
        // 保存供应商评分
        if(storageRequest.getScoreRequest() != null){
            String code = scoreService.saveByPurchase(storageRequest.getScoreRequest());
            if(StringUtils.isBlank(code)){
                LOGGER.error("保存采购单对应的评分失败");
                return HttpResponse.failure(ResultCode.ADD_ERROR);
            }
            // 评分编码存入采购单详情
            PurchaseOrderDetails detail = new PurchaseOrderDetails();
            detail.setPurchaseOrderId(purchaseOrderId);
            detail.setScoreCode(code);
            detail.setUpdateByName(storageRequest.getCreateByName());
            detail.setUpdateById(storageRequest.getCreateById());
            purchaseOrderDetailsDao.update(detail);
        }
        // 新增操作日志
        log(purchaseOrderId, storageRequest.getCreateById(), storageRequest.getCreateByName(), PurchaseOrderLogEnum.STORAGE_FINISH.getCode(),
                PurchaseOrderLogEnum.STORAGE_FINISH.getName() , null);
        return HttpResponse.success();
    }

    @Override
    public HttpResponse<InboundProduct> receiptProduct(String purchaseOrderId, Integer purchaseNum, Integer pageNo, Integer pageSize){
        if(StringUtils.isBlank(purchaseOrderId) || purchaseNum == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Inbound inbound = new Inbound();
        inbound.setPageSize(pageSize);
        inbound.setPageNo(pageNo);
        inbound.setSourceOderCode(purchaseOrderId);
        inbound.setPurchaseNum(purchaseNum);
        List<PurchaseApplyDetailResponse> list = inboundProductDao.selectPurchaseInfoByPurchaseNum(inbound);
        // 查询对应采购数据
        if(CollectionUtils.isNotEmptyCollection(list)){
            for(PurchaseApplyDetailResponse product:list){
                if(product != null){
                    PurchaseApplyDetailResponse orderProduct = purchaseOrderProductDao.warehousingInfo(product.getSourceOderCode(), product.getSkuCode());
                    if(orderProduct != null){
                        BeanUtils.copyProperties(orderProduct, product);
                        if(product.getActualSingleCount() != null && product.getBaseProductContent() != null){
                            product.setActualSingleCount(product.getActualSingleCount());
                            product.setActualTaxSum(product.getActualSingleCount() * product.getBaseProductContent() );
                        }else {
                            product.setActualSingleCount(0);
                            product.setActualTaxSum(0);
                        }
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
}
