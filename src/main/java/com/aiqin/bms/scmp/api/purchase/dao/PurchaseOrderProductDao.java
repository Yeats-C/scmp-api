package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;

public interface PurchaseOrderProductDao {

    Integer insert(PurchaseOrderProduct record);

    Integer update(PurchaseOrderProduct record);

    Integer insertAll(PurchaseOrderProduct orderProduct);
}