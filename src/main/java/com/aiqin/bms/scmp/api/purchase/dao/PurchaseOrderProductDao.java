package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseOrderProductRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseOrderProductDao {

    Integer insert(PurchaseOrderProduct record);

    Integer update(PurchaseOrderProduct record);

    Integer insertAll(@Param("list")List<PurchaseOrderProduct> list);

    List<PurchaseOrderProduct> purchaseOrderList(PurchaseOrderProductRequest request);

    Integer purchaseOrderCount(PurchaseOrderProductRequest request);

    List<PurchaseOrderProduct> orderBySku(String purchaseOrderId);
}