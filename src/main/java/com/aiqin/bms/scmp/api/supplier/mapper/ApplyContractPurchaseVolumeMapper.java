package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyContractPurchaseVolume;

public interface ApplyContractPurchaseVolumeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyContractPurchaseVolume record);

    int insertSelective(ApplyContractPurchaseVolume record);

    ApplyContractPurchaseVolume selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyContractPurchaseVolume record);

    int updateByPrimaryKey(ApplyContractPurchaseVolume record);
}