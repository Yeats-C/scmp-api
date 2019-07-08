package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuBoxPacking;

public interface ProductSkuBoxPackingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuBoxPacking record);

    int insertSelective(ProductSkuBoxPacking record);

    ProductSkuBoxPacking selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuBoxPacking record);

    int updateByPrimaryKey(ProductSkuBoxPacking record);

    int deleteBySkuCode(String skuCode);
}