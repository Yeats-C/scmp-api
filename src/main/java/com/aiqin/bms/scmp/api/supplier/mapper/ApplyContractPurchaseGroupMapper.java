package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyContractPurchaseGroup;

import java.util.List;

public interface ApplyContractPurchaseGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyContractPurchaseGroup record);

    int insertSelective(ApplyContractPurchaseGroup record);

    ApplyContractPurchaseGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyContractPurchaseGroup record);

    int updateByPrimaryKey(ApplyContractPurchaseGroup record);

    int insertBatch(List<ApplyContractPurchaseGroup> purchaseGroups);

    List<ApplyContractPurchaseGroup> selectByApplyContractCode(String applyContractCode);

    int deleteByApplyCode(String applyContractCode);
}