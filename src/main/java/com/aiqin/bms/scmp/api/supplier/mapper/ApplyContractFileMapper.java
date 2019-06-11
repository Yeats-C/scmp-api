package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyContractFile;

import java.util.List;

public interface ApplyContractFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyContractFile record);

    int insertSelective(ApplyContractFile record);

    ApplyContractFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyContractFile record);

    int updateByPrimaryKey(ApplyContractFile record);

    int insertBatch(List<ApplyContractFile> files);

    int deleteByApplyCode(String applyCode);

    List<ApplyContractFile> selectByApplyContractCode(String applyCode);
}