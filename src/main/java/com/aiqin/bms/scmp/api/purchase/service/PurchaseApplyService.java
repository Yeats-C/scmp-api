package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

public interface PurchaseApplyService {

    HttpResponse<List<PurchaseApplyResponse>> applyList(PurchaseApplyRequest purchaseApplyRequest);

    HttpResponse<List<PurchaseApplyProduct>> applyProductList(PurchaseApplyRequest purchaseApplyRequest);

    HttpResponse insertApplyProduct(PurchaseApplyProduct purchaseApplyProduct);

    HttpResponse searchApplyProduct(String applyProductId);

    HttpResponse deleteApplyProduct(String applyProductId);

    HttpResponse applyProductBasic(String purchaseApplyId);
}
