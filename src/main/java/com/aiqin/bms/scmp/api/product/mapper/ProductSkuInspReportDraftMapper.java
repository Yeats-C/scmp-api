package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInspReportDraft;

import java.util.List;

public interface ProductSkuInspReportDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuInspReportDraft record);

    int insertSelective(ProductSkuInspReportDraft record);

    ProductSkuInspReportDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuInspReportDraft record);

    int updateByPrimaryKey(ProductSkuInspReportDraft record);

    Integer delete(List<String> skuCodes);
}