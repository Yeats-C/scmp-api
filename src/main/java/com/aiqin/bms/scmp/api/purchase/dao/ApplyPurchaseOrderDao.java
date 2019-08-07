package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.ApplyPurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;

public interface ApplyPurchaseOrderDao {

    Integer insert(PurchaseOrder purchaseOrder);

    Integer update(ApplyPurchaseOrder record);

}