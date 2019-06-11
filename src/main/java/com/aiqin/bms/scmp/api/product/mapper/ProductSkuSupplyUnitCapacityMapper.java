package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitCapacity;

public interface ProductSkuSupplyUnitCapacityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSupplyUnitCapacity record);

    int insertSelective(ProductSkuSupplyUnitCapacity record);

    ProductSkuSupplyUnitCapacity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSupplyUnitCapacity record);

    int updateByPrimaryKey(ProductSkuSupplyUnitCapacity record);
}