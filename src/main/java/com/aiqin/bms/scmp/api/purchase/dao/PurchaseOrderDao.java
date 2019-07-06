package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseApplyRequest;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseOrderResponse;

import java.util.List;

public interface PurchaseOrderDao {

    Integer insert(PurchaseOrder record);

    Integer update(PurchaseOrder record);

    List<PurchaseOrderResponse> purchaseOrderList(PurchaseApplyRequest purchaseApplyRequest);

    Integer purchaseOrderCount(PurchaseApplyRequest purchaseApplyRequest);

    PurchaseOrder purchaseOrder(String purchaseOrderId);

}