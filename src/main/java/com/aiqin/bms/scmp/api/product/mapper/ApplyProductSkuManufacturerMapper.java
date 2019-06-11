package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuManufacturer;

public interface ApplyProductSkuManufacturerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuManufacturer record);

    int insertSelective(ApplyProductSkuManufacturer record);

    ApplyProductSkuManufacturer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuManufacturer record);

    int updateByPrimaryKey(ApplyProductSkuManufacturer record);
}