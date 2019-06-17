package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;

import java.util.List;

public interface PurchaseApplyProductDao {

    Integer insert(PurchaseApplyProduct record);

    Integer update(PurchaseApplyProduct record);

    List<PurchaseApplyProduct> applyProductList(PurchaseApplyRequest purchaseApplyRequest);

    Integer applyProductCount(PurchaseApplyRequest purchaseApplyRequest);

    PurchaseApplyProduct applyProduct(String applyProductId);

}