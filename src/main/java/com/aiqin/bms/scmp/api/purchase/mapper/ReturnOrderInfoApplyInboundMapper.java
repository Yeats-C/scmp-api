package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoApplyInbound;

public interface ReturnOrderInfoApplyInboundMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReturnOrderInfoApplyInbound record);

    int insertSelective(ReturnOrderInfoApplyInbound record);

    ReturnOrderInfoApplyInbound selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReturnOrderInfoApplyInbound record);

    int updateByPrimaryKey(ReturnOrderInfoApplyInbound record);
}