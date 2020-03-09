package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseFormRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseFormResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseApplyProductDao {

    Integer insert(PurchaseApplyProduct record);

    Integer update(PurchaseApplyProduct record);

    List<PurchaseApplyDetailResponse> applyProductList(PurchaseApplyRequest purchaseApplyRequest);

    Integer applyProductCount(PurchaseApplyRequest purchaseApplyRequest);

    PurchaseApplyProduct applyProduct(PurchaseApplyProduct purchaseApplyProduct);

    List<PurchaseApplyDetailResponse> productListByDetail(@Param("purchaseApplyId") String purchaseApplyId);

    Integer skuCount(@Param("purchaseApplyId") String purchaseApplyId, @Param("infoStatus")Integer infoStatus);

    Integer insertAll(@Param("list") List<PurchaseApplyProduct> purchaseApplyProduct);

    List<PurchaseFormResponse> selectPurchaseForm(@Param("list") List<String> applyIds);

    List<PurchaseApplyDetailResponse> purchaseFormProduct(PurchaseFormRequest apply);

    Integer formSkuCount(PurchaseFormRequest apply);

    List<PurchaseFormResponse> applyByProduct(PurchaseFormRequest apply);

    List<PurchaseApplyDetailResponse> purchaseFormList(PurchaseFormRequest apply);

    Integer purchaseFormCount(PurchaseFormRequest apply);

    Integer delete(String purchaseApplyId);

    Integer updateInfoStatus(PurchaseFormRequest form);

    Integer purchaseFormByComplete(String purchaseApplyId);

    List<PurchaseApplyDetailResponse> productBySku(PurchaseFormRequest apply);

    Integer purchaseFormByRepeatCount(PurchaseFormRequest apply);

    List<PurchaseApplyDetailResponse> purchaseFormByRepeat(PurchaseFormRequest apply);

    List<PurchaseApplyProduct> applyPurchaseProductList(String purchaseApplyId);

    List<PurchaseApplyDetailResponse> productCodeByDetail(@Param("purchaseApplyCode") String purchaseApplyCode,
                                                          @Param("warehouseCode")String warehouseCode);

    List<PurchaseApplyDetailResponse> productCodeByDetailSum(@Param("purchaseApplyCode") String purchaseApplyCode);


}