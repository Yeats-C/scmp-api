package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySettlementInformation;

public interface ApplySettlementInformationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplySettlementInformation record);

    int insertSelective(ApplySettlementInformation record);

    ApplySettlementInformation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplySettlementInformation record);

    int updateByPrimaryKey(ApplySettlementInformation record);
}