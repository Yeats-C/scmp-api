package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSalesInfo;

public interface ProductSkuSalesInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSalesInfo record);

    int insertSelective(ProductSkuSalesInfo record);

    ProductSkuSalesInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSalesInfo record);

    int updateByPrimaryKey(ProductSkuSalesInfo record);
}