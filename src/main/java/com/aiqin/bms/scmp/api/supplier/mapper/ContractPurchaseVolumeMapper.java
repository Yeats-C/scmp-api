package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ContractPurchaseVolume;

public interface ContractPurchaseVolumeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContractPurchaseVolume record);

    int insertSelective(ContractPurchaseVolume record);

    ContractPurchaseVolume selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractPurchaseVolume record);

    int updateByPrimaryKey(ContractPurchaseVolume record);
}