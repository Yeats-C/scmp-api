package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseBatch;
import org.apache.ibatis.annotations.Param;

public interface PurchaseBatchDao {

    Integer insert(PurchaseBatch record);

    Integer update(PurchaseBatch record);

    PurchaseBatch purchaseInfo(@Param("batchInfoCode") String batchInfoCode,
                               @Param("purchaseOderCode") String purchaseOderCode);

}