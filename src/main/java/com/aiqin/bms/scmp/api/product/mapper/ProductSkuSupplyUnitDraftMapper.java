package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitDraft;

import java.util.List;

public interface ProductSkuSupplyUnitDraftMapper {

    Integer delete(List<String> skuCodes);

    List<ProductSkuSupplyUnitDraft> selectByIds(List<Long> ids);

    int deleteDraftByIds(List<Long> ids);
}