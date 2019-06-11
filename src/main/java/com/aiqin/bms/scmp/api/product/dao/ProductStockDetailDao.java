package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.ProductStockDetail;

public interface ProductStockDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductStockDetail record);

    int insertSelective(ProductStockDetail record);

    ProductStockDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductStockDetail record);

    int updateByPrimaryKey(ProductStockDetail record);
}