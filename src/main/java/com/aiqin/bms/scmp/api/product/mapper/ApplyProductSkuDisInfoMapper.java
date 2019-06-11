package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuDisInfo;

public interface ApplyProductSkuDisInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuDisInfo record);

    int insertSelective(ApplyProductSkuDisInfo record);

    ApplyProductSkuDisInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuDisInfo record);

    int updateByPrimaryKey(ApplyProductSkuDisInfo record);
}