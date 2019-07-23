package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.product.domain.pojo.InboundProduct;
import com.aiqin.bms.scmp.api.purchase.domain.OperationLog;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseOrderResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.purchase.PurchaseCountAmountResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

public interface PurchaseManageService {

    HttpResponse selectPurchaseForm(List<String> applyIds);

    HttpResponse purchaseApplyList(PurchaseFormRequest purchaseFormRequest);

    HttpResponse purchaseProductList(PurchaseFormRequest purchaseFormRequest);

    HttpResponse purchaseOrder(PurchaseOrderRequest purchaseOrderRequest);

    HttpResponse<List<PurchaseOrderResponse>> purchaseOrderList(PurchaseApplyRequest purchaseApplyRequest);

    HttpResponse cancelPurchaseOrder(PurchaseOrder purchaseOrder);

    HttpResponse<PurchaseApplyDetailResponse> purchaseOrderDetails(String purchaseOrderId);

    HttpResponse purchaseOrderProduct(PurchaseOrderProductRequest request);

    HttpResponse purchaseOrderFile(String purchaseOrderId);

    HttpResponse purchaseOrderLog(String purchaseOrderId);

    HttpResponse<PurchaseCountAmountResponse> purchaseOrderAmount(String purchaseOrderId);

    HttpResponse purchaseOrderStock(PurchaseStorageRequest purchaseStorage);

    HttpResponse getWarehousing(PurchaseStorageRequest purchaseStorageRequest);

    HttpResponse reportSku(String purchaseOrderId);

    HttpResponse<List<Inbound>> receipt(String purchaseOrderId);

    HttpResponse storageConfirm(PurchaseStorageRequest storageRequest);

    HttpResponse<InboundProduct> receiptProduct(String purchaseOrderId, Integer purchaseNum, Integer pageNo, Integer pageSize);

    HttpResponse addLog(OperationLog operationLog);
}
