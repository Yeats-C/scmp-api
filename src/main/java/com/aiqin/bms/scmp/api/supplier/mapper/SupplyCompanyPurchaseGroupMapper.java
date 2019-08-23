package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompanyPurchaseGroup;

import java.util.List;

public interface SupplyCompanyPurchaseGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplyCompanyPurchaseGroup record);

    int insertSelective(SupplyCompanyPurchaseGroup record);

    SupplyCompanyPurchaseGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplyCompanyPurchaseGroup record);

    int updateByPrimaryKey(SupplyCompanyPurchaseGroup record);

    List<SupplyCompanyPurchaseGroup> selectBySupplyCompanyCode(String supplyCompanyCode);

    int insertBatch(List<SupplyCompanyPurchaseGroup> purchaseGroups);

    int deleteBySupplyCompanyCode(String supplyCompanyCode);
}