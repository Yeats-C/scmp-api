package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuStockInfo;

public interface ProductSkuStockInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuStockInfo record);

    int insertSelective(ProductSkuStockInfo record);

    ProductSkuStockInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuStockInfo record);

    int updateByPrimaryKey(ProductSkuStockInfo record);
}