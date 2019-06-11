package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuAssociatedGoods;

public interface ProductSkuAssociatedGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuAssociatedGoods record);

    int insertSelective(ProductSkuAssociatedGoods record);

    ProductSkuAssociatedGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuAssociatedGoods record);

    int updateByPrimaryKey(ProductSkuAssociatedGoods record);
}