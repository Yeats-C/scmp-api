package com.aiqin.bms.scmp.api.purchase.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseApplyDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseApplyProductDao;
import com.aiqin.bms.scmp.api.purchase.dao.PurchaseFileDao;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseFile;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseFormRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseFormProcuctResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseFormResponse;
import com.aiqin.bms.scmp.api.purchase.service.PurchaseManageService;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
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
    private PurchaseFileDao purchaseFileDao;

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
    public HttpResponse purchaseProductList(PurchaseFormRequest purchaseFormRequest){
        if(purchaseFormRequest == null){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        PurchaseFormProcuctResponse purchase = new PurchaseFormProcuctResponse();
        List<PurchaseApplyDetailResponse> details = purchaseApplyProductDao.purchaseFormProduct(purchaseFormRequest);
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
            purchase.setProductList(details);
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
            purchase.setPurchaseFormResponseList(forms);
        }
        return HttpResponse.success(purchase);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse deletePurchaseFile(PurchaseFile purchaseFile){
        if(purchaseFile == null || StringUtils.isBlank(purchaseFile.getFileCode())){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        Integer count = purchaseFileDao.update(purchaseFile);
        if(count == 0){
            LOGGER.info("删除采购文件失败");
            return HttpResponse.failure(ResultCode.UPDATE_ERROR);
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse purchaseFileList(String purchaseId){
        if(StringUtils.isBlank(purchaseId)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }
        List<PurchaseFile> purchaseFiles = purchaseFileDao.fileList(purchaseId);
        return HttpResponse.success(purchaseFiles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse purchaseForm(List<String> applyIds){
        if(CollectionUtils.isEmptyCollection(applyIds)){
            return HttpResponse.failure(ResultCode.REQUIRED_PARAMETER);
        }


        return HttpResponse.success();
    }

}
