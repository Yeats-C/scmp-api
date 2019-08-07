package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.ApplyPurchaseOrderProduct;

public interface ApplyPurchaseOrderProductDao {

    Integer insert(ApplyPurchaseOrderProduct record);

    Integer update(ApplyPurchaseOrderProduct record);

}