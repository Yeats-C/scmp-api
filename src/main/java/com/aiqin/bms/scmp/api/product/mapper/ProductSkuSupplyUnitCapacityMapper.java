package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitCapacity;

import java.util.List;

public interface ProductSkuSupplyUnitCapacityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSupplyUnitCapacity record);

    int insertSelective(ProductSkuSupplyUnitCapacity record);

    ProductSkuSupplyUnitCapacity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSupplyUnitCapacity record);

    int updateByPrimaryKey(ProductSkuSupplyUnitCapacity record);

    int deleteByApplyCode(String applyCode);

    int insertBatch(List<ProductSkuSupplyUnitCapacity> capacities);
}