package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApplyProduct;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.PurchaseGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BiSmartReplenishmentDao {

    List<PurchaseGroup> getPurchaseGroup(@Param("beginTime") String beginTime, @Param("finishTime")String finishTime);

    List<PurchaseApplyProduct>  skuInfo(@Param("beginTime") String beginTime,
                                        @Param("finishTime") String finishTime,
                                        @Param("purchaseGroupCode") String purchaseGroupCode);
}