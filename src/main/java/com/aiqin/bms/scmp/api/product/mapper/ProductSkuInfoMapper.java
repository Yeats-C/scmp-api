package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;

import java.util.List;

public interface ProductSkuInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuInfo record);

    int insertSelective(ProductSkuInfo record);

    ProductSkuInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuInfo record);

    int updateByPrimaryKey(ProductSkuInfo record);

    int insertBatch(List<ProductSkuInfo> records);
}