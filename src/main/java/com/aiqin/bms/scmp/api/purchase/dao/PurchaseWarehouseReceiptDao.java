package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseWarehouseReceipt;

public interface PurchaseWarehouseReceiptDao {

    Integer insert(PurchaseWarehouseReceipt record);

    Integer update(PurchaseWarehouseReceipt record);
}