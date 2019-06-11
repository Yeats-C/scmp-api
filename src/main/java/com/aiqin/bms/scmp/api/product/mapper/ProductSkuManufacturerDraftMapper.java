package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuManufacturerDraft;

import java.util.List;

public interface ProductSkuManufacturerDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuManufacturerDraft record);

    int insertSelective(ProductSkuManufacturerDraft record);

    ProductSkuManufacturerDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuManufacturerDraft record);

    int updateByPrimaryKey(ProductSkuManufacturerDraft record);

    Integer delete(List<String> skuCodes);
}