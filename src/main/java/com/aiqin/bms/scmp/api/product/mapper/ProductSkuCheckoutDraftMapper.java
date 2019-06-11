package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckoutDraft;

public interface ProductSkuCheckoutDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuCheckoutDraft record);

    int insertSelective(ProductSkuCheckoutDraft record);

    ProductSkuCheckoutDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuCheckoutDraft record);

    int updateByPrimaryKey(ProductSkuCheckoutDraft record);
}