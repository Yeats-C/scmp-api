package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.TaxCostLog;
import org.apache.ibatis.annotations.Param;

public interface TaxCostLogDao {
    TaxCostLog selectByTaxDate(@Param("taxDate") String taxDate, @Param("warehouseCode") String warehouseCode, @Param("skuCode") String skuCode);

    void insertOne(TaxCostLog log);

    void updateStockTaxCost(@Param("id") Long id, @Param("taxCost") long taxCost);
}
