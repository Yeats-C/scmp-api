package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;

public interface PurchaseApplyDao {

    Integer insert(PurchaseApply record);

    Integer update(PurchaseApply record);

}