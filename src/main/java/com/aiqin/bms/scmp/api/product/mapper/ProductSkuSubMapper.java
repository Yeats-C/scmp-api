package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSub;

public interface ProductSkuSubMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSub record);

    int insertSelective(ProductSkuSub record);

    ProductSkuSub selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSub record);

    int updateByPrimaryKey(ProductSkuSub record);
}