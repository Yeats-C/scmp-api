package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.ApplyPurchaseOrder;

public interface ApplyPurchaseOrderDao {

    Integer insert(ApplyPurchaseOrder record);

    Integer update(ApplyPurchaseOrder record);

}