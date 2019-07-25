package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyContractCategory;

import java.util.List;

public interface ApplyContractCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyContractCategory record);

    int insertSelective(ApplyContractCategory record);

    ApplyContractCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyContractCategory record);

    int updateByPrimaryKey(ApplyContractCategory record);

    int insertBatch(List<ApplyContractCategory> purchaseGroups);

    List<ApplyContractCategory> selectByApplyContractCode(String applyContractCode);

    int deleteByApplyCode(String applyContractCode);
}