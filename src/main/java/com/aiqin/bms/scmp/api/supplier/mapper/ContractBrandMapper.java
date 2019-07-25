package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ContractBrand;

import java.util.List;

public interface ContractBrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContractBrand record);

    int insertSelective(ContractBrand record);

    ContractBrand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractBrand record);

    int updateByPrimaryKey(ContractBrand record);

    List<ContractBrand> selectByContractCode(String contractCode);

    int insertBatch(List<ContractBrand> contractPurchaseGroups);

    int deleteByContractCode(String contractCode);
}