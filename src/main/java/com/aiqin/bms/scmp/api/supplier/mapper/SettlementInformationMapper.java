package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SettlementInformation;

public interface SettlementInformationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SettlementInformation record);

    int insertSelective(SettlementInformation record);

    SettlementInformation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SettlementInformation record);

    int updateByPrimaryKey(SettlementInformation record);
}