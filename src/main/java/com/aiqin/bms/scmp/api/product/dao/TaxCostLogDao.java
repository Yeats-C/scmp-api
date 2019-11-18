package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.TaxCostLog;
import com.aiqin.bms.scmp.api.product.domain.pojo.TaxCostLogStock;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TaxCostLogDao {
    TaxCostLog selectByTaxDate(@Param("taxDate") String taxDate, @Param("warehouseCode") String warehouseCode, @Param("skuCode") String skuCode);

    void insertOne(TaxCostLog log);

    void updateStockTaxCost(@Param("id") Long id, @Param("taxCost") long taxCost);

    TaxCostLogStock selectTimeByTaxDate(@Param("taxDate") String taxDate);

    void insertOneSku(@Param("taxCostLogStock") List<TaxCostLogStock> taxCostLogStock);

    void updateOneSku(@Param("taxCostStock") List<TaxCostLogStock> taxCostLogStock);

    /**
     * 根据产品code来查询出价格
     * @param productCode
     * @return
     */
    List<BigDecimal> loadPriceByProductCode(@Param("productCode")String productCode);
}
