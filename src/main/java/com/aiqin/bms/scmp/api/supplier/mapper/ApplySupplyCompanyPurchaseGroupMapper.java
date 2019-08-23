package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplyCompanyPurchaseGroup;

import java.util.List;

public interface ApplySupplyCompanyPurchaseGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplySupplyCompanyPurchaseGroup record);

    int insertSelective(ApplySupplyCompanyPurchaseGroup record);

    ApplySupplyCompanyPurchaseGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplySupplyCompanyPurchaseGroup record);

    int updateByPrimaryKey(ApplySupplyCompanyPurchaseGroup record);

    int insertBatch(List<ApplySupplyCompanyPurchaseGroup> purchaseGroups);

    List<ApplySupplyCompanyPurchaseGroup> selectByApplySupplyCompanyCode(String applySupplyCompanyCode);

    int deleteByApplyCode(String applySupplyCompanyCode);
}