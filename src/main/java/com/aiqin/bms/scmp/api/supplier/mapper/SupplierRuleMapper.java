package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierRule;
import com.aiqin.bms.scmp.api.supplier.domain.response.rule.DetailRespVo;

public interface SupplierRuleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierRule record);

    int insertSelective(SupplierRule record);

    SupplierRule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierRule record);

    int updateByPrimaryKey(SupplierRule record);

    DetailRespVo findByCompanyCode(String companyCode);
}