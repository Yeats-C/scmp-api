package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplyCompany;

public interface ApplySupplyCompanyMapper {

    ApplySupplyCompany selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplySupplyCompany record);

    int updateByPrimaryKey(ApplySupplyCompany record);
}