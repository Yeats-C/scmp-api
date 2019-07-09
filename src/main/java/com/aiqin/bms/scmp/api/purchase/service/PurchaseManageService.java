package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseFormRequest;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseOrderRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.purchase.PurchaseCountAmountResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

public interface PurchaseManageService {

    HttpResponse selectPurchaseForm(List<String> applyIds);

    HttpResponse purchaseApplyList(PurchaseFormRequest purchaseFormRequest);

    HttpResponse purchaseProductList(PurchaseFormRequest purchaseFormRequest);

    HttpResponse purchaseOrder(PurchaseOrderRequest purchaseOrderRequest);

    HttpResponse purchaseOrderList(PurchaseApplyRequest purchaseApplyRequest);

    HttpResponse cancelPurchaseOrder(PurchaseOrder purchaseOrder);

    HttpResponse purchaseOrderDetails(String purchaseOrderId);

    HttpResponse purchaseOrderProduct(String purchaseOrderId, Integer isPage, Integer pageNo, Integer pageSize);

    HttpResponse purchaseOrderFile(String purchaseOrderId);

    HttpResponse purchaseOrderLog(String purchaseOrderId);

    HttpResponse<PurchaseCountAmountResponse> purchaseOrderAmount(String purchaseOrderId);

    HttpResponse purchaseOrderStock(String purchaseOrderId, String createById, String createByName);

    HttpResponse getWarehousing(List<PurchaseOrderProduct> list);
}
