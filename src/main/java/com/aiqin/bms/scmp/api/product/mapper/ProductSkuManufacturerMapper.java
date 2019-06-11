package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuManufacturer;

public interface ProductSkuManufacturerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuManufacturer record);

    int insertSelective(ProductSkuManufacturer record);

    ProductSkuManufacturer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuManufacturer record);

    int updateByPrimaryKey(ProductSkuManufacturer record);
}