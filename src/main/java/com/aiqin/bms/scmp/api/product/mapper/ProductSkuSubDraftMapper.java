package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSubDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSubRespVo;

import java.util.List;

public interface ProductSkuSubDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSubDraft record);

    int insertSelective(ProductSkuSubDraft record);

    ProductSkuSubDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSubDraft record);

    int updateByPrimaryKey(ProductSkuSubDraft record);

    int insertBatch(List<ProductSkuSubDraft> draftList);

    List<ProductSkuSubRespVo> selectBySkuCode(String skuCode);

    List<ProductSkuSubDraft> selectBySkuCodes(List<String> skuCodes);
}