package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseProduct;

public interface PurchaseProductDao {
    int deleteByPrimaryKey(Long id);

    Integer insert(PurchaseProduct record);

    Integer update(PurchaseProduct record);

}