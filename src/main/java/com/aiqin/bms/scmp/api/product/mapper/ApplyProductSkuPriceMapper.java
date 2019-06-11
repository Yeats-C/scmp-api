package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPrice;

public interface ApplyProductSkuPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuPrice record);

    int insertSelective(ApplyProductSkuPrice record);

    ApplyProductSkuPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuPrice record);

    int updateByPrimaryKey(ApplyProductSkuPrice record);
}