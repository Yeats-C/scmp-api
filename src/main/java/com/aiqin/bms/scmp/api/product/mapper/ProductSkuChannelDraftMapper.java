package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChannelDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuChannelRespVo;

import java.util.List;

public interface ProductSkuChannelDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuChannelDraft record);

    int insertSelective(ProductSkuChannelDraft record);

    ProductSkuChannelDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuChannelDraft record);

    int updateByPrimaryKey(ProductSkuChannelDraft record);

    int saveBatch(List<ProductSkuChannelDraft> drafts);


    List<ProductSkuChannelRespVo> getList(String skuCode);

    List<ProductSkuChannelDraft> getDrafts(List<String> skuCodes);

    int delete(List<String> skuCodes);
}