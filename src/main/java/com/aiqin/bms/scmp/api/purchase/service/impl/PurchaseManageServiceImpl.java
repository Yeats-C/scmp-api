package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.InOutStatus;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.service.impl.BaseServiceImpl;
import com.aiqin.bms.scmp.api.common.InboundTypeEnum;
import com.aiqin.bms.scmp.api.common.PurchaseOrderLogEnum;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.dao.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.request.StockChangeRequest;
import com.aiqin.bms.scmp.api.product.domain.request.StockVoRequest;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.inbound.InboundReqSave;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QueryProductSkuInspReportReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuInspReportRespVo;
import com.aiqin.bms.scmp.api.product.service.InboundService;
import com.aiqin.bms.scmp.api.product.service.StockService;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.*;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyProductInfoResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseFormResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseOrderResponse;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseApprovalService;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.service.PurchaseGroupService;
import com.aiqin.bms.scmp.api.supplier.service.SupplierScoreService;
import com.aiqin.bms.scmp.api.util.Calculate;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowVO;
import com.aiqin.bms.scmp.api.workflow.vo.response.WorkFlowRespVO;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author: zhao shuai
 * @create: 2019-06-14 17:49
 **/
@Service
public class PurchaseManageServiceImpl extends BaseServiceImpl implements PurchaseManageService {

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
    @Resource
    private ProductSkuPicturesDao productSkuPicturesDao;
    @Resource
    private ProductSkuSupplyUnitDao productSkuSupplyUnitDao;
    @Resource
    private StockService stockService;
    @Resource
    private PurchaseGroupService purchaseGroupService;
    @Resource
    private PurchaseInspectionReportDao purchaseInspectionReportDao;
    @Resource
    private ApplyPurchaseOrderDao applyPurchaseOrderDao;
    @Resource
    private ApplyPurchaseOrderDetailsDao applyPurchaseOrderDetailsDao;
    @Resource
    private ApplyPurchaseOrderProductDao applyPurchaseOrderProductDao;

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
                Integer singleCount = 0, productTotalAmount = 0, returnAmount = 0, wholeCount = 0, giftTaxSum= 0;
                if(CollectionUtils.isNotEmptyCollection(details)){
                    Set<Integer> setType = new HashSet<>();
                    for(PurchaseApplyDetailResponse detail:details){
                        setType.add(detail.getApplyType());
                        Integer purchaseWhole = detail.getPurchaseWhole() == null ? 0 : detail.getPurchaseWhole();
                        Integer purchaseSingle = detail.getPurchaseSingle() == null ? 0 : detail.getPurchaseSingle();
                        Integer packNumber = detail.getBaseProductContent() == null ? 0 : detail.getBaseProductContent();
                        Integer amount = detail.getProductPurchaseAmount() == null ? 0 : detail.getProductPurchaseAmount();
                        Integer number = purchaseWhole * packNumber + purchaseSingle;
                        Integer amountSum = number * amount;
                        // 单品数量
                        singleCount += number;
                        wholeCount += purchaseWhole;
                        if(detail.getProductType().equals(Global.PRODUCT_TYPE_2)){
                            returnAmount +=  amountSum;
                        }else if(detail.getProductType().equals(Global.PRODUCT_TYPE_1)){
                            giftTaxSum += amountSum;
                        }else if(detail.getProductType().equals(Global.PRODUCT_TYPE_0)){
                            productTotalAmount += amountSum;
                        }
                    }
                    if(setType != null){
                        if(setType.size() == 2){
                            form.setApplyTypeForm("手动/自动");
                        }else {
                            String type = "";
                            for (Integer i:setType){
                                if(i == 0){
                                    type = "手动";
                                }else {
                                    type = "自动";
                                }
                            }
                            form.setApplyTypeForm(type);
                        }
                    }
                }
                form.setProductTotalAmount(productTotalAmount);
                form.setReturnAmount(returnAmount);
                form.setSingleCount(singleCount);
                form.setGiftTaxSum(giftTaxSum);
                // sku数量
                //Integer skuCount = purchaseApplyProductDao.formSkuCount(apply);
                form.setSkuCount(wholeCount);
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
                    if(apply.getProductType().equals(Global.PRODUCT_TYPE_2)){
                        returnAmount += number * amount;
                    }
                    if(apply.getProductType().equals(Global.PRODUCT_TYPE_0)){
                        productTotalAmount += number * amount;
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
        purchaseOrder.setPurchaseOrderCode(purchaseProductCode);
        purchaseOrder.setInfoStatus(Global.PURCHASE_APPLY_STATUS_0);
        purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_0);
        purchaseOrder.setStorageStatus(Global.STORAGE_STATUS_0);
        purchaseOrder.setPurchaseMode(0);
        purchaseOrder.setCreateById(purchaseOrderRequest.getPersonId());
        purchaseOrder.setCreateByName(purchaseOrderRequest.getPersonName());
        // 添加采购单
        Integer orderCount = purchaseOrderDao.insert(purchaseOrder);
        if(orderCount > 0){
            // 添加采购单的审批日志
            applyPurchaseOrderDao.insert(purchaseOrder);
            // 添加操作日志
            log(purchaseId, purchaseOrderRequest.getPersonId(), purchaseOrderRequest.getPersonName(),
                    PurchaseOrderLogEnum.INSERT_ORDER.getCode(), PurchaseOrderLogEnum.INSERT_ORDER.getName(), purchaseOrder.getApplyTypeForm());
            log(purchaseId, purchaseOrderRequest.getPersonId(), purchaseOrderRequest.getPersonName(),
                    PurchaseOrderLogEnum.CHECKOUT_STAY.getCode(), PurchaseOrderLogEnum.CHECKOUT_STAY.getName(), purchaseOrder.getApplyTypeForm());
            encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(), encodingRule.getId());
            // 添加采购单详情
            PurchaseOrderDetails details = purchaseOrderRequest.getOrderDetails();
            details.setPurchaseDetailsId(IdUtil.purchaseId());
            details.setPurchaseOrderId(purchaseId);
            details.setPurchaseOrderCode(purchaseProductCode);
            details.setDetailsStatus(Global.USER_ON);
            details.setOrderType("配送");
            purchaseOrderDetailsDao.insert(details);
            // 添加采购单审批的详情
            applyPurchaseOrderDetailsDao.insert(details);
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
                    purchaseApply = new PurchaseApply();
                    purchaseApply.setPurchaseApplyId(apply);
                    purchaseApply.setApplyStatus(Global.PURCHASE_APPLY_STATUS_1.intValue());
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
            List<PurchaseOrderProduct> list = Lists.newArrayList();
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
                orderProduct.setActualSingleCount(0);
                orderProduct.setFactorySkuCode(detail.getFactorySkuCode());
                list.add(orderProduct);
            }
            purchaseOrderProductDao.insertAll(list);
            // 添加采购单商品的审批记录
            applyPurchaseOrderProductDao.insertAll(list);
        }
    }

    @Override
    public HttpResponse<List<PurchaseOrderResponse>> purchaseOrderList(PurchaseApplyRequest purchaseApplyRequest){
        List<PurchaseGroupVo> groupVoList = purchaseGroupService.getPurchaseGroup(null);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(groupVoList)) {
            return HttpResponse.success();
        }
        purchaseApplyRequest.setGroupList(groupVoList);
        PageResData pageResData = new PageResData();
        List<PurchaseOrderResponse> list = purchaseOrderDao.purchaseOrderList(purchaseApplyRequest);
        if(CollectionUtils.isNotEmptyCollection(list)){
            for(PurchaseOrderResponse order:list){
                // 计算实际单品数量，实际含税采购金额， 实际实物返金额
                Integer actualSingleCount = 0, actualTotalAmount = 0, actualReturnAmount = 0, actualGiftTaxSum = 0;
                Integer productTotalAmount = 0, returnAmount = 0, giftTaxSum = 0;
                List<PurchaseOrderProduct> orderProducts = purchaseOrderProductDao.orderProductInfo(order.getPurchaseOrderId());
                if(CollectionUtils.isNotEmptyCollection(orderProducts)){
                    for(PurchaseOrderProduct product:orderProducts){
                        Integer singleCount = product.getSingleCount() == null ? 0:product.getSingleCount();
                        Integer actualSingle = product.getActualSingleCount() == null ? 0:product.getActualSingleCount();
                        Integer productAmount = product.getProductAmount() == null ?0:product.getProductAmount();
                        actualSingleCount += actualSingle;
                        if(product.getProductType().equals(Global.PRODUCT_TYPE_0)) {
                            actualTotalAmount += productAmount * actualSingle;
                            productTotalAmount += productAmount * singleCount;
                        }
                        if(product.getProductType().equals(Global.PRODUCT_TYPE_2)) {
                            actualReturnAmount += productAmount * actualSingle;
                            returnAmount += productAmount * singleCount;
                        }
                        if(product.getProductType().equals(Global.PRODUCT_TYPE_1)) {
                            actualGiftTaxSum += productAmount * actualSingle;
                            giftTaxSum += productAmount * singleCount;
                        }
                    }
                }
                order.setActualSingleCount(actualSingleCount);
                order.setActualTotalAmount(actualTotalAmount);
                order.setActualReturnAmount(actualReturnAmount);
                order.setReturnAmount(returnAmount);
                order.setProductTotalAmount(productTotalAmount);
                order.setActualGiftTaxSum(actualGiftTaxSum);
                order.setGiftTaxSum(giftTaxSum);
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
        String purchaseOrderId = purchaseOrder.getPurchaseOrderId();
        String createById = purchaseOrder.getCreateById();
        String createByName = purchaseOrder.getCreateByName();
        PurchaseOrder order = purchaseOrderDao.purchaseOrder(purchaseOrderId);
        if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_9)){
            if(order != null && order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_0)
                    || order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_1)){
                WorkFlowVO w = new WorkFlowVO();
                w.setFormNo(order.getPurchaseOrderCode());
                WorkFlowRespVO workFlowRespVO = cancelWorkFlow(w);
                if (!workFlowRespVO.getSuccess()) {
                    throw new GroundRuntimeException("审批流撤销失败!");
                }
            }
        }
        purchaseOrder.setUpdateByName(createByName);
        purchaseOrder.setUpdateById(createById);
        Integer count = purchaseOrderDao.update(purchaseOrder);
        if(count == 0){
            LOGGER.error("变更采购单的状态失败......");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        // 添加操作日志
        PurchaseOrderDetails detail;
        if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_2)){
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.STOCK_UP.getCode(),
                    PurchaseOrderLogEnum.STOCK_UP.getName(), order.getApplyTypeForm());
        }else if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_4)){
            detail = new PurchaseOrderDetails();
            detail.setPurchaseOrderId(purchaseOrderId);
            detail.setDeliveryTime(Calendar.getInstance().getTime());
            detail.setUpdateById(createById);
            detail.setUpdateByName(createByName);
            purchaseOrderDetailsDao.update(detail);
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.DELIVER_GOODS.getCode(),
                    PurchaseOrderLogEnum.DELIVER_GOODS.getName(), order.getApplyTypeForm());
        }else if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_6)){
            detail = new PurchaseOrderDetails();
            detail.setPurchaseOrderId(purchaseOrderId);
            detail.setWarehouseTime(Calendar.getInstance().getTime());
            detail.setUpdateById(createById);
            detail.setUpdateByName(createByName);
            purchaseOrderDetailsDao.update(detail);
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.WAREHOUSING_FINISH.getCode(),
                    PurchaseOrderLogEnum.WAREHOUSING_FINISH.getName(), order.getApplyTypeForm());
        }else if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_5)){
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.WAREHOUSING_BEGIN.getCode(),
                    PurchaseOrderLogEnum.WAREHOUSING_BEGIN.getName(), order.getApplyTypeForm());
        }else if(purchaseOrder.getStorageStatus() != null && purchaseOrder.getStorageStatus().equals(Global.STORAGE_STATUS_1)){
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.STORAGE_STAY.getCode(),
                    PurchaseOrderLogEnum.STORAGE_STAY.getName(), order.getApplyTypeForm());
        }else if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_9)){
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.REVOKE.getCode(),
                    PurchaseOrderLogEnum.REVOKE.getName(), order.getApplyTypeForm());
        }else if(purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_7) && order.getStorageStatus().equals(Global.STORAGE_STATUS_2)){
            // 仓储确认判断是否入库完成
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getCode(),
                    PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getName(), order.getApplyTypeForm());
            this.wayNum(purchaseOrderId);
        }else if(purchaseOrder.getPurchaseOrderStatus() != null && purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_7)){
            // 添加入库完成时间
            detail = new PurchaseOrderDetails();
            detail.setPurchaseOrderId(purchaseOrderId);
            detail.setWarehouseTime(Calendar.getInstance().getTime());
            detail.setUpdateByName(createById);
            detail.setUpdateById(createByName);
            purchaseOrderDetailsDao.update(detail);
            // 手动入库完成 撤销未完成的入库单
            inboundService.repealOrder(order.getPurchaseOrderCode(), createById, createByName);
            log(purchaseOrderId, createById, createByName, PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getCode(),
                    PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getName(), order.getApplyTypeForm());
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
    public HttpResponse<PurchaseApplyProductInfoResponse> purchaseOrderAmount(String purchaseOrderId){
        if(StringUtils.isBlank(purchaseOrderId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        // 计算采购单的数量与金额
        PurchaseApplyProductInfoResponse amountResponse = new PurchaseApplyProductInfoResponse();
        Integer productPieceSum = 0, matterPieceSum = 0, giftPieceSum = 0;
        Integer productSingleSum= 0, matterSingleSum = 0, giftSingleSum = 0;
        Integer productTaxSum = 0, matterTaxSum = 0, giftTaxSum = 0, singleSum = 0, priceSum = 0;
        Integer actualProductPieceSum = 0, actualMatterPieceSum = 0, actualGiftPieceSum = 0;
        Integer actualProductSingleSum= 0, actualMatterSingleSum = 0, actualGiftSingleSum = 0;
        Integer actualProductTaxSum = 0, actualMatterTaxSum = 0, actualGiftTaxSum = 0, actualSingleSum = 0, actualPriceSum = 0;
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
                Integer amount = order.getProductAmount() == null ? 0 : order.getProductAmount();
                Integer singleCount = purchaseWhole * packNumber + purchaseSingle;
                singleSum += singleCount;
                priceSum += purchaseWhole;
                // 实际
                Integer actualSingleCount = order.getActualSingleCount() == null ? 0: order.getActualSingleCount();
                Integer actualWhole = actualSingleCount / packNumber;
                actualPriceSum += actualWhole;
                actualSingleSum += actualSingleCount;
                if(order.getProductType().equals(Global.PRODUCT_TYPE_0)){
                    productPieceSum += purchaseWhole;
                    productSingleSum += singleCount;
                    productTaxSum += amount * singleCount;
                    actualProductPieceSum += actualWhole;
                    actualProductSingleSum += actualSingleCount;
                    actualProductTaxSum += amount * actualSingleCount;
                }else if(order.getProductType().equals(Global.PRODUCT_TYPE_2)){
                    matterPieceSum += purchaseWhole;
                    matterSingleSum += singleCount;
                    matterTaxSum += amount * singleCount;
                    actualMatterPieceSum += actualWhole;
                    actualMatterSingleSum += actualSingleCount;
                    actualMatterTaxSum += amount * actualSingleCount;
                }else if(order.getProductType().equals(Global.PRODUCT_TYPE_1)){
                    giftPieceSum += purchaseWhole;
                    giftSingleSum += singleCount;
                    giftTaxSum += amount * singleCount;
                    actualGiftPieceSum += actualWhole;
                    actualGiftSingleSum += actualSingleCount;
                    actualGiftTaxSum += amount * actualSingleCount;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse purchaseOrderStock(PurchaseStorageRequest purchaseStorage){
        if(purchaseStorage == null || StringUtils.isBlank(purchaseStorage.getPurchaseOrderCode())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        String purchaseOrderCode = purchaseStorage.getPurchaseOrderCode();
        // 查询采购单的详情
        PurchaseOrder order = new PurchaseOrder();
        order.setPurchaseOrderCode(purchaseOrderCode);
        PurchaseOrder purchaseOrder = purchaseOrderDao.purchaseOrderInfo(order);
        if(purchaseOrder == null){
            LOGGER.info("未查询到采购单的信息" + purchaseOrderCode);
            return HttpResponse.failure(ResultCode.SEARCH_ERROR);
        }
        // 变更采购单的状态（开始备货）
        if(!purchaseOrder.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_2)) {
            LOGGER.error("该采购单未审核通过， 不能开始备货");
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_CHECK);
        }
        order.setPurchaseOrderStatus(Global.PURCHASE_ORDER_3);
        order.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        Integer count = purchaseOrderDao.update(order);
        if(count == 0){
            LOGGER.error("采购单开始备货状态修改失败");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        log(purchaseOrder.getPurchaseOrderId(), purchaseStorage.getCreateById(), purchaseStorage.getCreateByName(), PurchaseOrderLogEnum.STOCK_UP.getCode(),
                PurchaseOrderLogEnum.STOCK_UP.getName() , purchaseOrder.getApplyTypeForm());
        // 开始备货， 生成入库单
        purchaseStorage.setPurchaseNum(1);
        // 查询采购单商品
        PurchaseOrderProductRequest request = new PurchaseOrderProductRequest();
        request.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        request.setIsPage(1);
        List<PurchaseOrderProduct> products = purchaseOrderProductDao.purchaseOrderList(request);
        if(CollectionUtils.isEmptyCollection(products)){
            LOGGER.error("此采购单没有商品");
            return HttpResponse.failure(ResultCode.PRODUCT_NO_EXISTS);
        }
        InboundReqSave reqSave = this.InboundReqSave(purchaseOrder, purchaseStorage, products);
        String s = inboundService.saveInbound(reqSave);
        if(StringUtils.isBlank(s)){
            LOGGER.error("生成入库单失败....");
            return HttpResponse.failure(ResultCode.SAVE_OUT_BOUND_FAILED);
        }
        return HttpResponse.success();
    }

    private InboundReqSave InboundReqSave(PurchaseOrder purchaseOrder, PurchaseStorageRequest purchaseStorage, List<PurchaseOrderProduct> productList){
        InboundReqSave save = new InboundReqSave();
        save.setCompanyCode(purchaseStorage.getCompanyCode());
        save.setCompanyName(purchaseStorage.getCompanyName());
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
        save.setPurchaseNum(purchaseStorage.getPurchaseNum());
        save.setCreateBy(purchaseStorage.getCreateByName());
        save.setCreateTime(Calendar.getInstance().getTime());
        save.setInboundTime(Calendar.getInstance().getTime());
        // 预计到货时间
        PurchaseApplyDetailResponse detail = purchaseOrderDetailsDao.purchaseOrderDetail(purchaseOrder.getPurchaseOrderCode());
        if(detail != null){
            save.setPreArrivalTime(detail.getExpectArrivalTime());
        }
        Long preInboundNum = 0L;
        Long preInboundMainNum = 0L;
        Long preTaxAmount = 0L;
        Long preNoTaxAmount = 0L;

        InboundProductReqVo reqVo;
        // 入库sku商品
        List<InboundProductReqVo> list = Lists.newArrayList();
        // 查询是否有商品可以入库
        if(CollectionUtils.isNotEmptyCollection(productList)){
            for(PurchaseOrderProduct product:productList){
                Integer singleCount = product.getSingleCount() == null ? 0 : product.getSingleCount();
                Integer actualSingleCount = product.getActualSingleCount() == null ? 0 : product.getActualSingleCount().intValue();
                if(singleCount - actualSingleCount == 0){
                    continue;
                }
                reqVo = new InboundProductReqVo();
                reqVo.setSkuCode(product.getSkuCode());
                reqVo.setSkuName(product.getSkuName());
                ProductSkuPictures productSkuPicture = productSkuPicturesDao.getPicInfoBySkuCode(product.getSkuCode());
                if(productSkuPicture != null && StringUtils.isNotBlank(productSkuPicture.getProductPicturePath())){
                    reqVo.setPictureUrl(productSkuPicture.getProductPicturePath());
                }
                reqVo.setNorms(product.getProductSpec());
                reqVo.setColorName(product.getColorName());
                reqVo.setColorCode(null);
                reqVo.setModel(product.getModelNumber());
                reqVo.setInboundNorms(product.getProductSpec());
                ProductSkuPurchaseInfo skuPurchaseInfo = productSkuPurchaseInfoDao.getInfo(product.getSkuCode());
                if(skuPurchaseInfo != null){
                    reqVo.setUnitCode(skuPurchaseInfo.getUnitCode());
                    reqVo.setUnitName(skuPurchaseInfo.getUnitName());
                    reqVo.setInboundBaseUnit(skuPurchaseInfo.getZeroRemovalCoefficient().toString());
                    reqVo.setInboundBaseContent(skuPurchaseInfo.getBaseProductContent().toString());
                }
                Integer purchaseWhole = product.getPurchaseWhole() == null ? 0 : product.getPurchaseWhole().intValue();
                Integer baseProductContent = product.getBaseProductContent() == null ? 0 : product.getBaseProductContent().intValue();
                Integer amount = product.getProductAmount() == null ? 0 : product.getProductAmount();
                if(actualSingleCount > 0 && baseProductContent > 0){
                    purchaseWhole = purchaseWhole - actualSingleCount / baseProductContent;
                }
                reqVo.setPreInboundMainNum(singleCount.longValue() - actualSingleCount.longValue());
                reqVo.setPreInboundNum(purchaseWhole.longValue());
                reqVo.setPreTaxPurchaseAmount(product.getProductAmount().longValue());
                Long productTotalAmount = product.getProductTotalAmount() == null ? 0 : product.getProductTotalAmount().longValue();
                reqVo.setPreTaxAmount(productTotalAmount);
                reqVo.setLinenum(product.getId());
                reqVo.setCreateBy(purchaseStorage.getCreateByName());
                reqVo.setCreateTime(Calendar.getInstance().getTime());
                reqVo.setTaxRate(product.getTaxRate());
                preInboundMainNum += reqVo.getPreInboundMainNum();
                preInboundNum += purchaseWhole;
                Integer totalAmount = amount * (singleCount - actualSingleCount);
                preTaxAmount += totalAmount.longValue();
                preNoTaxAmount += Calculate.computeNoTaxPrice(totalAmount.longValue(), product.getTaxRate().longValue());
                list.add(reqVo);
            }
        }
        save.setPreInboundNum(preInboundNum);
        save.setPreMainUnitNum(preInboundMainNum);
        save.setPreTaxAmount(preTaxAmount);
        save.setPreAmount(preNoTaxAmount);
        save.setPreTax(preTaxAmount-preNoTaxAmount);
        save.setRemark(null);
        save.setList(list);
        return save;
    }

    @Override
   // @Transactional(rollbackFor = Exception.class)
    public HttpResponse getWarehousing(PurchaseStorageRequest purchaseStorage){
        if(purchaseStorage == null || CollectionUtils.isEmptyCollection(purchaseStorage.getOrderList())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Integer num = purchaseStorage.getPurchaseNum();
        PurchaseOrder order = new PurchaseOrder();
        order.setPurchaseOrderCode(purchaseStorage.getPurchaseOrderCode());
        PurchaseOrder purchaseOrder = purchaseOrderDao.purchaseOrderInfo(order);
        order.setStorageStatus(Global.STORAGE_STATUS_1);
        if(num == 1){
            order.setPurchaseOrderStatus(Global.PURCHASE_ORDER_6);
        }
        order.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        Integer count = purchaseOrderDao.update(order);
        if(count == 0){
            LOGGER.error("采购单入库中状态修改失败");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        // 变更采购的入库的实际单品数量
        List<PurchaseOrderProduct> list = purchaseStorage.getOrderList();
        List<PurchaseOrderProduct> productList = Lists.newArrayList();
        for(PurchaseOrderProduct product:list){
            product.setPurchaseOrderCode(purchaseStorage.getPurchaseOrderCode());
            PurchaseOrderProduct orderProduct= purchaseOrderProductDao.selectPreNumAndPraNumBySkuCodeAndSource(
                    purchaseStorage.getPurchaseOrderCode(), product.getSkuCode(), product.getId());
            Integer actualCount = orderProduct.getActualSingleCount() == null ? 0 : orderProduct.getActualSingleCount().intValue();
            product.setActualSingleCount(actualCount + product.getActualSingleCount());
            Integer count1 = purchaseOrderProductDao.update(product);
            if(count1 == 0){
                return HttpResponse.failure(ResultCode.UPDATE_ERROR);
            }
            PurchaseOrderProduct purchaseOrderProduct = purchaseOrderProductDao.selectPreNumAndPraNumBySkuCodeAndSource(
                    purchaseStorage.getPurchaseOrderCode(), product.getSkuCode(), product.getId());
            Integer singleCount = purchaseOrderProduct.getSingleCount() == null ? 0 : purchaseOrderProduct.getSingleCount().intValue();
            Integer actualSingleCount = purchaseOrderProduct.getActualSingleCount() == null ? 0 : purchaseOrderProduct.getActualSingleCount().intValue();
            if(singleCount - actualSingleCount > 0){
                Integer code = inboundDao.selectMaxPurchaseNumBySourceOderCode(purchaseStorage.getPurchaseOrderCode());
                purchaseStorage.setPurchaseNum(code + 1);
                productList.add(purchaseOrderProduct);
            }
        }
        // 是否入库完成
        if(purchaseStorage.getPurchaseNum() > num){
            InboundReqSave save = this.InboundReqSave(purchaseOrder, purchaseStorage, productList);
            String s = inboundService.saveInbound(save);
            if(StringUtils.isBlank(s)){
                LOGGER.error("生成入库单失败....");
                return HttpResponse.failure(ResultCode.SAVE_OUT_BOUND_FAILED);
            }
        }else {
            order.setPurchaseOrderStatus(Global.PURCHASE_ORDER_7);
            order.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
            Integer count1 = purchaseOrderDao.update(order);
            if(count1 == 0){
                LOGGER.error("采购单入库完成状态修改失败");
                return HttpResponse.failure(ResultCode.UPDATE_ERROR);
            }
            // 添加入库完成时间
            PurchaseOrderDetails detail = new PurchaseOrderDetails();
            detail.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
            detail.setWarehouseTime(Calendar.getInstance().getTime());
            detail.setUpdateByName(purchaseStorage.getCreateById());
            detail.setUpdateById(purchaseStorage.getCreateByName());
            purchaseOrderDetailsDao.update(detail);
            log(purchaseOrder.getPurchaseOrderId(), purchaseStorage.getCreateById(), purchaseStorage.getCreateByName(), PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getCode(),
                    PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getName() , purchaseOrder.getApplyTypeForm());
            // 仓储确认判断是否入库完成
            if(order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_7) && order.getStorageStatus().equals(Global.STORAGE_STATUS_2)){
                this.wayNum(purchaseStorage.getPurchaseOrderId());
            }
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
    public HttpResponse<List<Inbound>> receipt(String purchaseOrderCode){
        if(StringUtils.isBlank(purchaseOrderCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<Inbound> inbound = inboundDao.selectTimeAndSatusBySourchAndNum(purchaseOrderCode);
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
        if(order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_7)){
            purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_8);
        }
        purchaseOrder.setStorageStatus(Global.STORAGE_STATUS_2);
        purchaseOrder.setUpdateById(storageRequest.getCreateById());
        purchaseOrder.setUpdateByName(storageRequest.getCreateByName());
        Integer count = purchaseOrderDao.update(purchaseOrder);
        if(count == 0){
            LOGGER.error("变更采购单的仓储状态失败......");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        // 保存质检报告
        if(CollectionUtils.isNotEmptyCollection(storageRequest.getInspectionReport())){
            List<PurchaseInspectionReport> purchaseInspectionReports = purchaseInspectionReportDao.inspectionReportInfo(purchaseOrderId);
            if(CollectionUtils.isNotEmptyCollection(purchaseInspectionReports)){
                PurchaseInspectionReport report = new PurchaseInspectionReport();
                report.setPurchaseOrderId(purchaseOrderId);
                report.setStatus(Global.USER_OFF);
                report.setUpdateById(storageRequest.getCreateById());
                report.setUpdateByName(storageRequest.getCreateByName());
                purchaseInspectionReportDao.update(report);
            }
            purchaseInspectionReportDao.insertAll(storageRequest.getInspectionReport());
            // 替换商品的对应sku与日期的质检报告
            List<PurchaseInspectionReport> inspection = storageRequest.getInspectionReport();
            ProductSkuInspReport inspReport;
            List<ProductSkuInspReport> productSkuInspReportList = Lists.newArrayList();
            QueryProductSkuInspReportReqVo vo;
            for(PurchaseInspectionReport pir:inspection){
                // 判断商品的质检报告是否存在， 存在根据sku与生产日期更新上传路径，否则新增
                vo = new QueryProductSkuInspReportReqVo();
                vo.setSkuCode(pir.getSkuCode());
                vo.setProductionDate(pir.getProductionDate());
                List<ProductSkuInspReportRespVo> list = productSkuInspReportDao.getListBySkuCodeAndProductDate(vo);
                inspReport = new ProductSkuInspReport();
                inspReport.setSkuCode(pir.getSkuCode());
                inspReport.setProductionDate(pir.getProductionDate());
                if(list.size() > 0){
                    inspReport.setUpdateBy(storageRequest.getCreateByName());
                    productSkuInspReportDao.updateInspection(inspReport);
                }else {
                    inspReport.setSkuName(pir.getSkuName());
                    inspReport.setInspectionReportPath(pir.getInspectionReportPath());
                    inspReport.setCreateBy(storageRequest.getCreateByName());
                    productSkuInspReportList.add(inspReport);
                }
            }
            if(CollectionUtils.isNotEmptyCollection(productSkuInspReportList)){
                productSkuInspReportDao.insertInspReportList(productSkuInspReportList);
            }
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
                PurchaseOrderLogEnum.STORAGE_FINISH.getName() , order.getApplyTypeForm());
        // 仓储确认判断是否入库完成
        if(order.getPurchaseOrderStatus().equals(Global.PURCHASE_ORDER_7) && order.getStorageStatus().equals(Global.STORAGE_STATUS_2)){
            log(purchaseOrderId, storageRequest.getCreateById(), storageRequest.getCreateByName(), PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getCode(),
                    PurchaseOrderLogEnum.ORDER_WAREHOUSING_FINISH.getName() , order.getApplyTypeForm());
            this.wayNum(purchaseOrderId);
        }
        return HttpResponse.success();
    }

    // 修改库存在途数
    private void wayNum(String purchaseOrderId){
        StockChangeRequest stock = new StockChangeRequest();
        stock.setOperationType(11);
        List<StockVoRequest> list = Lists.newArrayList();
        StockVoRequest stockVo;
        // 查询该采购单的商品
        List<PurchaseApplyDetailResponse> products = purchaseOrderProductDao.orderProductInfoByGroup(purchaseOrderId);
        if(CollectionUtils.isNotEmptyCollection(products)){
            for(PurchaseApplyDetailResponse product:products){
                stockVo = new StockVoRequest();
                stockVo.setTransportCenterCode(product.getTransportCenterCode());
                stockVo.setTransportCenterName(product.getTransportCenterName());
                stockVo.setWarehouseCode(product.getWarehouseCode());
                stockVo.setWarehouseName(product.getWarehouseName());
                stockVo.setOperator(product.getCreateByName());
                stockVo.setSkuCode(product.getSkuCode());
                stockVo.setSkuName(product.getSkuName());
                long singleCount =  product.getSingleCount() == null ? 0 : product.getSingleCount().longValue();
                long actualSingleCount =  product.getActualSingleCount() == null ? 0 : product.getActualSingleCount().longValue();
                stockVo.setChangeNum(singleCount - actualSingleCount);
                stockVo.setDocumentNum(product.getPurchaseOrderCode());
                stockVo.setDocumentType(3);
                list.add(stockVo);
            }
            stock.setStockVoRequests(list);
            stockService.changeStock(stock);
        }
    }

    @Override
    public HttpResponse<PurchaseApplyDetailResponse> receiptProduct(String purchaseOrderCode, Integer purchaseNum, Integer pageNo, Integer pageSize){
        if(StringUtils.isBlank(purchaseOrderCode) || purchaseNum == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
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
                    PurchaseApplyDetailResponse orderProduct = purchaseOrderProductDao.warehousingInfo(product.getSourceOderCode(), product.getLinenum());
                    if(orderProduct != null){
                        Integer actualSingleCount = product.getActualSingleCount() == null ? 0 : product.getActualSingleCount();
                        Integer baseProductContent = orderProduct.getBaseProductContent() == null ? 0 : orderProduct.getBaseProductContent();
                        BeanUtils.copyProperties(orderProduct, product);
                        product.setActualSingleCount(actualSingleCount);
                        product.setActualTaxSum(actualSingleCount * baseProductContent);
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
    public HttpResponse<PurchaseInspectionReport> inspectionReport(String purchaseOrderId){
       if(StringUtils.isBlank(purchaseOrderId)){
           return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
       }
        List<PurchaseInspectionReport> inspectionReport = purchaseInspectionReportDao.inspectionReportInfo(purchaseOrderId);
        return HttpResponse.success(inspectionReport);
    }

    @Override
    public HttpResponse<PurchaseFormResponse> skuSupply(String skuCode){
        if(StringUtils.isBlank(skuCode)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseFormResponse> purchaseFormResponses = productSkuSupplyUnitDao.supplyList(skuCode);
        return HttpResponse.success(purchaseFormResponses);
    }
}
