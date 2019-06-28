package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.PageResData;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.purchase.dao.*;
import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderDetails;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseFormRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseOrderRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseFormResponse;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.id.IdUtil;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
                detail.setProductPurchaseSum(number * amount);
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
        purchaseOrder.setPurchaseOrderId(purchaseProductCode);
        purchaseOrder.setInfoStatus(Global.PURCHASE_APPLY_STATUS_1);
        purchaseOrder.setPurchaseOrderStatus(Global.PURCHASE_ORDER_0);
        // 添加采购单
        Integer orderCount = purchaseOrderDao.insert(purchaseOrder);
        if(orderCount > 0){
            // 添加采购单详情
            PurchaseOrderDetails details = purchaseOrderRequest.getOrderDetails();
            details.setPurchaseDetailsId(IdUtil.purchaseId());
            details.setPurchaseOrderId(purchaseId);
            details.setPurchaseOrderCode(purchaseProductCode);
            details.setDetailsStatus(Global.USER_ON);
            details.setOrderType("配送");
            purchaseOrderDetailsDao.insert(details);
            // 添加商品列表
            this.insertProduct(purchaseOrderRequest, purchaseId, purchaseProductCode);
            // 添加文件信息
            List<FileRecord> fileList = purchaseOrderRequest.getFileList();
            if(CollectionUtils.isNotEmptyCollection(fileList)){
                fileRecordDao.insertAll(purchaseId, fileList);
            }
        }
        return HttpResponse.success();
    }

    private void insertProduct(PurchaseOrderRequest purchaseOrderRequest, String purchaseId, String purchaseProductCode){
        if(CollectionUtils.isNotEmptyCollection(purchaseOrderRequest.getApplyIds())){
            PurchaseFormRequest form = new PurchaseFormRequest();
            form.setApplyIds(purchaseOrderRequest.getApplyIds());
            PurchaseOrder order = purchaseOrderRequest.getPurchaseOrder();
            form.setWarehouseCode(order.getWarehouseCode());
            form.setTransportCenterCode(order.getTransportCenterCode());
            form.setSupplierCode(order.getSupplierCode());
            form.setPurchaseGroupCode(order.getPurchaseGroupCode());
            form.setSettlementMethodCode(order.getSettlementMethodCode());
            List<PurchaseApplyDetailResponse> details = purchaseApplyProductDao.purchaseFormProduct(form);
            // 提交采购单页面商品列表
            if(CollectionUtils.isNotEmptyCollection(details)){
                PurchaseOrderProduct orderProduct = null;
                for(PurchaseApplyDetailResponse detail:details){
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
                    purchaseOrderProductDao.insertAll(orderProduct);
                }
            }

        }

    }
}
