package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseApplyProductDao {

    Integer insert(PurchaseApplyProduct record);

    Integer update(PurchaseApplyProduct record);

    List<PurchaseApplyDetailResponse> applyProductList(PurchaseApplyRequest purchaseApplyRequest);

    Integer applyProductCount(PurchaseApplyRequest purchaseApplyRequest);

    PurchaseApplyProduct applyProduct(PurchaseApplyProduct purchaseApplyProduct);

    List<PurchaseApplyDetailResponse> productList(String purchaseApplyId);

    Integer skuCount(String purchaseApplyId);

    Integer insertAll(@Param("list") List<PurchaseApplyProduct> purchaseApplyProduct);

}