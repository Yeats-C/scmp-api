package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseBatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseBatchDao {

    Integer insertAll(@Param("list")List<PurchaseBatch> record);

    Integer update(PurchaseBatch record);

    PurchaseBatch purchaseInfo(@Param("batchInfoCode") String batchInfoCode,
                               @Param("purchaseOderCode") String purchaseOderCode,
                               @Param("lineCode") Integer lineCode);

}