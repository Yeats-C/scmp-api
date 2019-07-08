package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuAssociatedGoods;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuAssociatedGoodsRespVo;

import java.util.List;

public interface ProductSkuAssociatedGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuAssociatedGoods record);

    int insertSelective(ProductSkuAssociatedGoods record);

    ProductSkuAssociatedGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuAssociatedGoods record);

    int updateByPrimaryKey(ProductSkuAssociatedGoods record);

    List<ProductSkuAssociatedGoodsRespVo> getList(String skuCode);
}