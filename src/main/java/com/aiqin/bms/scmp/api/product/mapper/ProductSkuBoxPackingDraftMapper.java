package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuBoxPackingDraft;

import java.util.List;

public interface ProductSkuBoxPackingDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuBoxPackingDraft record);

    int insertSelective(ProductSkuBoxPackingDraft record);

    ProductSkuBoxPackingDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuBoxPackingDraft record);

    int updateByPrimaryKey(ProductSkuBoxPackingDraft record);

    int saveBatch(List<ProductSkuBoxPackingDraft> records);

    Integer delete(List<String> skuCodes);
}