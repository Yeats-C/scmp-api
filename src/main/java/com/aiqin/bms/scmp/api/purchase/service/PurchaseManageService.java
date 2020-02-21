package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.purchase.domain.OperationLog;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseInspectionReport;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyProductInfoResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseFormResponse;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseOrderResponse;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

public interface PurchaseManageService {

    HttpResponse selectPurchaseForm(List<String> applyIds);

    HttpResponse purchaseApplyList(PurchaseFormRequest purchaseFormRequest);

    HttpResponse purchaseProductList(PurchaseFormRequest purchaseFormRequest);

    HttpResponse purchaseOrder(PurchaseOrderRequest purchaseOrderRequest);

    HttpResponse<List<PurchaseOrder>> purchaseOrderList(PurchaseApplyRequest purchaseApplyRequest);

    HttpResponse cancelPurchaseOrder(PurchaseOrder purchaseOrder);

    HttpResponse<PurchaseOrder> purchaseOrderDetails(String purchaseOrderId);

    HttpResponse purchaseOrderProduct(PurchaseOrderProductRequest request);

    HttpResponse purchaseOrderFile(String purchaseOrderId);

    HttpResponse purchaseOrderLog(String purchaseOrderId);

    HttpResponse<PurchaseApplyProductInfoResponse> purchaseOrderAmount(String purchaseOrderId);

    HttpResponse purchaseOrderStock(PurchaseStorageRequest purchaseStorage);

    HttpResponse getWarehousing(PurchaseStorageRequest purchaseStorageRequest);

    HttpResponse reportSku(String purchaseOrderId);

    HttpResponse<List<Inbound>> receipt(String purchaseOrderCode);

    HttpResponse storageConfirm(PurchaseStorageRequest storageRequest);

    HttpResponse<PurchaseApplyDetailResponse> receiptProduct(String purchaseOrderCode, Integer purchaseNum, Integer pageNo, Integer pageSize);

    HttpResponse addLog(OperationLog operationLog);

    HttpResponse<PurchaseInspectionReport> inspectionReport(String purchaseOrderId);

    HttpResponse<PurchaseFormResponse> skuSupply(String skuCode);

    HttpResponse<PurchaseApplyDetailResponse> applyDetails(String purchaseOrderCode);

    HttpResponse applyOrderProduct(PurchaseOrderProductRequest request);

    HttpResponse<PurchaseApplyProductInfoResponse>  applyOrderAmount(String purchaseOrderId);

    HttpResponse purchaseOrderPre(String purchaseGroupCode, Integer purchaseOrderTypeCode, String purchaseOrderCode);
}
