package com.aiqin.bms.scmp.api.purchase.dao.apply;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderDetails;
import com.aiqin.bms.scmp.api.purchase.domain.response.PurchaseApplyDetailResponse;

public interface ApplyPurchaseOrderDetailsDao {

    Integer insert(PurchaseOrderDetails record);

    PurchaseApplyDetailResponse applyOrderDetails(String purchaseOrderCode);

}