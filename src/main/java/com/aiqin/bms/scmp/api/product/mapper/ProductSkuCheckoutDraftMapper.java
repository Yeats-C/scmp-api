package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckoutDraft;

import java.util.List;

public interface ProductSkuCheckoutDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuCheckoutDraft record);
    int insertBatch(List<ProductSkuCheckoutDraft> records);

    int insertSelective(ProductSkuCheckoutDraft record);

    ProductSkuCheckoutDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuCheckoutDraft record);

    int updateByPrimaryKey(ProductSkuCheckoutDraft record);

    int delete(List<String> skuCodes);
}