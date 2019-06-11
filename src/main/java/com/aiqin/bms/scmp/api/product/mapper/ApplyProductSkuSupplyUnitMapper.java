package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSupplyUnit;

public interface ApplyProductSkuSupplyUnitMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuSupplyUnit record);

    int insertSelective(ApplyProductSkuSupplyUnit record);

    ApplyProductSkuSupplyUnit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuSupplyUnit record);

    int updateByPrimaryKey(ApplyProductSkuSupplyUnit record);
}