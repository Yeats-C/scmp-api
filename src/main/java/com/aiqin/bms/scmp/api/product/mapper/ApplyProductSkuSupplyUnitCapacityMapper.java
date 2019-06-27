package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSupplyUnitCapacity;

import java.util.List;

public interface ApplyProductSkuSupplyUnitCapacityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuSupplyUnitCapacity record);

    int insertSelective(ApplyProductSkuSupplyUnitCapacity record);

    ApplyProductSkuSupplyUnitCapacity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuSupplyUnitCapacity record);

    int updateByPrimaryKey(ApplyProductSkuSupplyUnitCapacity record);

    int insertBatch(List<ApplyProductSkuSupplyUnitCapacity> applyProductSkuSupplyUnitCapacities);
}