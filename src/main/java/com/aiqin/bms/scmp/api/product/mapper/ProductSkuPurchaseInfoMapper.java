package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPurchaseInfo;

public interface ProductSkuPurchaseInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPurchaseInfo record);

    int insertSelective(ProductSkuPurchaseInfo record);

    ProductSkuPurchaseInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPurchaseInfo record);

    int updateByPrimaryKey(ProductSkuPurchaseInfo record);
}