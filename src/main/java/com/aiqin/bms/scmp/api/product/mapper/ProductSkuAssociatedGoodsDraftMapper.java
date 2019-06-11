package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuAssociatedGoodsDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuAssociatedGoodsRespVo;

import java.util.List;

public interface ProductSkuAssociatedGoodsDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuAssociatedGoodsDraft record);

    int insertSelective(ProductSkuAssociatedGoodsDraft record);

    ProductSkuAssociatedGoodsDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuAssociatedGoodsDraft record);

    int updateByPrimaryKey(ProductSkuAssociatedGoodsDraft record);


    int saveBatch(List<ProductSkuAssociatedGoodsDraft> records);

    List<ProductSkuAssociatedGoodsRespVo> getList(String skuCode);

    Integer delete(List<String> skuCodes);
}