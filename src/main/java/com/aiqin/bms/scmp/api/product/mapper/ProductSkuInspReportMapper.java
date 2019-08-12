package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInspReport;

import java.util.List;

public interface ProductSkuInspReportMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuInspReport record);

    int insertSelective(ProductSkuInspReport record);

    ProductSkuInspReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuInspReport record);

    int updateByPrimaryKey(ProductSkuInspReport record);

    int deleteByIds(List<Long> ids);
}