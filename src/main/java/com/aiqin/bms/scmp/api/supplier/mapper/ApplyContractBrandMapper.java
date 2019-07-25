package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyContractBrand;

import java.util.List;

public interface ApplyContractBrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyContractBrand record);

    int insertSelective(ApplyContractBrand record);

    ApplyContractBrand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyContractBrand record);

    int updateByPrimaryKey(ApplyContractBrand record);

    int insertBatch(List<ApplyContractBrand> purchaseGroups);

    List<ApplyContractBrand> selectByApplyContractCode(String applyContractCode);

    int deleteByApplyCode(String applyContractCode);
}