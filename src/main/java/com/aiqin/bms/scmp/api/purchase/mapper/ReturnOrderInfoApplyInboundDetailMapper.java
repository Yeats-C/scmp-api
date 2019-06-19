package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoApplyInboundDetail;

public interface ReturnOrderInfoApplyInboundDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReturnOrderInfoApplyInboundDetail record);

    int insertSelective(ReturnOrderInfoApplyInboundDetail record);

    ReturnOrderInfoApplyInboundDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReturnOrderInfoApplyInboundDetail record);

    int updateByPrimaryKey(ReturnOrderInfoApplyInboundDetail record);
}