package com.aiqin.bms.scmp.api.purchase.dao.apply;

import com.aiqin.bms.scmp.api.purchase.domain.apply.ApplyPurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.purchase.domain.request.PurchaseOrderProductRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyPurchaseOrderProductDao {

    Integer insertAll(@Param("list") List<PurchaseOrderProduct> record);

    List<ApplyPurchaseOrderProduct> applyPurchaseOrderList(PurchaseOrderProductRequest request);

    Integer applyPurchaseOrderCount(PurchaseOrderProductRequest request);

}