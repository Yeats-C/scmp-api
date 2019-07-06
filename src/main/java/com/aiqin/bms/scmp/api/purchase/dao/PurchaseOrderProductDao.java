package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseOrderProductDao {

    Integer insert(PurchaseOrderProduct record);

    Integer update(PurchaseOrderProduct record);

    Integer insertAll(@Param("list")List<PurchaseOrderProduct> list);

    List<PurchaseOrderProduct> purchaseOrderList(@Param("purchaseOrderId") String purchaseOrderId,
                                                 @Param("isPage")Integer isPage,
                                                 @Param("pageNo")Integer pageNo,
                                                 @Param("pageSize")Integer pageSize);
}