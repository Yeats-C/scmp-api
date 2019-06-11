package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuStockInfo;

public interface ApplyProductSkuStockInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuStockInfo record);

    int insertSelective(ApplyProductSkuStockInfo record);

    ApplyProductSkuStockInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuStockInfo record);

    int updateByPrimaryKey(ApplyProductSkuStockInfo record);
}