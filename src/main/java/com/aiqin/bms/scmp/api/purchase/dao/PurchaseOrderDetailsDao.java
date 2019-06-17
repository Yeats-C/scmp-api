package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderDetails;

public interface PurchaseOrderDetailsDao {

    Integer insert(PurchaseOrderDetails record);

    Integer update(PurchaseOrderDetails record);

}