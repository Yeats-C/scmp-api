package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseBatch;

public interface PurchaseBatchDao {

    Integer insert(PurchaseBatch record);

    Integer update(PurchaseBatch record);

}