package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuBoxPacking;

public interface ApplyProductSkuBoxPackingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuBoxPacking record);

    int insertSelective(ApplyProductSkuBoxPacking record);

    ApplyProductSkuBoxPacking selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuBoxPacking record);

    int updateByPrimaryKey(ApplyProductSkuBoxPacking record);
}