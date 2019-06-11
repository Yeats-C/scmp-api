package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSalesInfo;

public interface ApplyProductSkuSalesInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuSalesInfo record);

    int insertSelective(ApplyProductSkuSalesInfo record);

    ApplyProductSkuSalesInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuSalesInfo record);

    int updateByPrimaryKey(ApplyProductSkuSalesInfo record);
}