package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitDraft;

import java.util.List;

public interface ProductSkuSupplyUnitDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSupplyUnitDraft record);

    int insertSelective(ProductSkuSupplyUnitDraft record);

    ProductSkuSupplyUnitDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSupplyUnitDraft record);

    int updateByPrimaryKey(ProductSkuSupplyUnitDraft record);

    Integer delete(List<String> skuCodes);

    List<ProductSkuSupplyUnitDraft> selectByIds(List<Long> ids);

    int deleteDraftByIds(List<Long> ids);
}