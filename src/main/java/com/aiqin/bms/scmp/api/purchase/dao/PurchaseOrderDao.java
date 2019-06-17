package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;

public interface PurchaseOrderDao {

    Integer insert(PurchaseOrder record);

    Integer update(PurchaseOrder record);

}