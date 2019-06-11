package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDraft;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;

import java.util.List;

public interface ProductSkuDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuDraft record);

    int insertSelective(ProductSkuDraft record);

    ProductSkuDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuDraft record);

    int updateByPrimaryKey(ProductSkuDraft record);

    List<ProductSkuDraftRespVo> getProductSkuDraftByCompanyCode(String companyCode);

    List<ProductSkuDraft> getProductSkuDraftByProductCode(String productCode);
}