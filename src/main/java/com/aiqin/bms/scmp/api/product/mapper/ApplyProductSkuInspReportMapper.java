package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuInspReport;

public interface ApplyProductSkuInspReportMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuInspReport record);

    int insertSelective(ApplyProductSkuInspReport record);

    ApplyProductSkuInspReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuInspReport record);

    int updateByPrimaryKey(ApplyProductSkuInspReport record);
}