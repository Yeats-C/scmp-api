package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;

public interface PurchaseApplyProductDao {

    Integer insert(PurchaseApplyProduct record);

    Integer update(PurchaseApplyProduct record);

}