package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ContractPurchaseGroup;

import java.util.List;

public interface ContractPurchaseGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContractPurchaseGroup record);

    int insertSelective(ContractPurchaseGroup record);

    ContractPurchaseGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractPurchaseGroup record);

    int updateByPrimaryKey(ContractPurchaseGroup record);

    List<ContractPurchaseGroup> selectByContractCode(String contractCode);

    int insertBatch(List<ContractPurchaseGroup> contractPurchaseGroups);

    int deleteByContractCode(String contractCode);
}