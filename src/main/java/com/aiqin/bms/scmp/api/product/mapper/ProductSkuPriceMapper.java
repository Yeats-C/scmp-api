package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPrice;

public interface ProductSkuPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPrice record);

    int insertSelective(ProductSkuPrice record);

    ProductSkuPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPrice record);

    int updateByPrimaryKey(ProductSkuPrice record);
}