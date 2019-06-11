package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceDraft;

public interface ProductSkuPriceDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPriceDraft record);

    int insertSelective(ProductSkuPriceDraft record);

    ProductSkuPriceDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPriceDraft record);

    int updateByPrimaryKey(ProductSkuPriceDraft record);
}