package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ContractFile;

import java.util.List;

public interface ContractFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContractFile record);

    int insertSelective(ContractFile record);

    ContractFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractFile record);

    int updateByPrimaryKey(ContractFile record);

    int insertBatch(List<ContractFile> files);

    int deleteByContractCode(String contractCode);

    List<ContractFile> selectByContractCode(String contractCode);
}