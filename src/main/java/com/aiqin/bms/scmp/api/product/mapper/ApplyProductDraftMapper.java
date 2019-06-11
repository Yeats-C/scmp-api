package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductDraft;
import com.aiqin.bms.scmp.api.product.domain.response.draft.ProductSkuDraftRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;


public interface ApplyProductDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductDraft record);

    int insertSelective(ApplyProductDraft record);

    ApplyProductDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductDraft record);

    int updateByPrimaryKey(ApplyProductDraft record);

    int deleteCode(@Param("list") Collection<String> list);

    ApplyProductDraft getProductCode(@Param("productCode") String productCode);

    Integer getName(@Param("productName") String productName);

    List<ProductSkuDraftRespVo> getProductDraft(String companyCode);
}