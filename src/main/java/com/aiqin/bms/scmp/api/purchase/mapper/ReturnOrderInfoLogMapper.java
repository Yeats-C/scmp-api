package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoLog;

import java.util.List;

public interface ReturnOrderInfoLogMapper {

    int insert(ReturnOrderInfoLog record);

    int insertBatch(List<ReturnOrderInfoLog> logs);

    List<ReturnOrderInfoLog> returnOrderLog(String orderCode);
}