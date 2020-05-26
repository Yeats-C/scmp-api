package com.aiqin.bms.scmp.api.purchase.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.purchase.domain.OperationLog;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.*;
import com.aiqin.bms.scmp.api.purchase.domain.response.*;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

public interface PurchaseManageService {

    HttpResponse purchaseOrder(PurchaseOrderRequest purchaseOrderRequest);

    HttpResponse<List<PurchaseOrder>> purchaseOrderList(PurchaseApplyRequest purchaseApplyRequest);

    HttpResponse cancelPurchaseOrder(PurchaseOrder purchaseOrder);

    HttpResponse<PurchaseOrder> purchaseOrderDetails(String purchaseOrderId);

    HttpResponse<PurchaseOrderProduct> purchaseOrderProduct(PurchaseOrderProductRequest request);

    HttpResponse<List<OperationLog>> purchaseOrderLog(String operationId);

    HttpResponse<PurchaseApplyProductInfoResponse> purchaseOrderAmount(String purchaseOrderId);

    HttpResponse getWarehousing(PurchaseStorageRequest purchaseStorageRequest);

    HttpResponse<List<Inbound>> receipt(String purchaseOrderCode);

    HttpResponse<PurchaseApplyDetailResponse> receiptProduct(String purchaseOrderCode, Integer purchaseNum, Integer pageNo, Integer pageSize);

    HttpResponse addLog(OperationLog operationLog);

    HttpResponse<PurchaseFormResponse> skuSupply(String skuCode, String transportCenterCode, String warehouseCode,
                                                 String settlementMethodCode, String purchaseGroupCode);

    HttpResponse purchaseOrderPre(String purchaseGroupCode, Integer purchaseOrderTypeCode, String purchaseOrderCode);

    HttpResponse cancelInbound(PurchaseOrder order);
}
