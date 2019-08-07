package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.ApplyPurchaseOrderDetails;

public interface ApplyPurchaseOrderDetailsDao {

    Integer insert(ApplyPurchaseOrderDetails record);

    Integer update(ApplyPurchaseOrderDetails record);

}