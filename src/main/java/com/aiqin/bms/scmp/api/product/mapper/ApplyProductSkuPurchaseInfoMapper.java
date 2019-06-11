package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPurchaseInfo;

public interface ApplyProductSkuPurchaseInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuPurchaseInfo record);

    int insertSelective(ApplyProductSkuPurchaseInfo record);

    ApplyProductSkuPurchaseInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuPurchaseInfo record);

    int updateByPrimaryKey(ApplyProductSkuPurchaseInfo record);
}