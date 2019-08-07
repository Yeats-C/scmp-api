package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.ApplyPurchaseOrderDetails;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderDetails;

public interface ApplyPurchaseOrderDetailsDao {

    Integer insert(PurchaseOrderDetails record);

    Integer update(ApplyPurchaseOrderDetails record);

}