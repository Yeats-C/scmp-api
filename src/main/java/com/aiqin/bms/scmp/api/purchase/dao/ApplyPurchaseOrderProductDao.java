package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.ApplyPurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyPurchaseOrderProductDao {

    Integer insertAll(@Param("list") List<PurchaseOrderProduct> record);

    Integer update(ApplyPurchaseOrderProduct record);

}