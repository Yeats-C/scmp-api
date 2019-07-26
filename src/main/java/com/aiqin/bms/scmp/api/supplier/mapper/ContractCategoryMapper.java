package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ContractCategory;

import java.util.List;

public interface ContractCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContractCategory record);

    int insertSelective(ContractCategory record);

    ContractCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractCategory record);

    int updateByPrimaryKey(ContractCategory record);

    List<ContractCategory> selectByContractCode(String contractCode);

    int insertBatch(List<ContractCategory> contractPurchaseGroups);

    int deleteByContractCode(String contractCode);
}