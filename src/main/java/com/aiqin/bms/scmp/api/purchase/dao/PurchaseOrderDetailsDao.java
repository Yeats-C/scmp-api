package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderDetails;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;

public interface PurchaseOrderDetailsDao {

    Integer insert(PurchaseOrderDetails record);

    Integer update(PurchaseOrderDetails record);

    PurchaseApplyDetailResponse purchaseOrderDetail(String purchaseOrderCode);

}