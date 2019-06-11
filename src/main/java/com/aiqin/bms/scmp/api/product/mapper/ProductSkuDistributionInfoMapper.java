package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDistributionInfo;

public interface ProductSkuDistributionInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuDistributionInfo record);

    int insertSelective(ProductSkuDistributionInfo record);

    ProductSkuDistributionInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuDistributionInfo record);

    int updateByPrimaryKey(ProductSkuDistributionInfo record);
}