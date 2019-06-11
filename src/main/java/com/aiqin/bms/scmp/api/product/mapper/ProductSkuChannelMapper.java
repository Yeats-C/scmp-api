package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChannel;

public interface ProductSkuChannelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuChannel record);

    int insertSelective(ProductSkuChannel record);

    ProductSkuChannel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuChannel record);

    int updateByPrimaryKey(ProductSkuChannel record);
}