package com.aiqin.bms.scmp.api.purchase.mapper;


import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoLog;

import java.util.List;

public interface ReturnOrderInfoLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReturnOrderInfoLog record);

    int insertSelective(ReturnOrderInfoLog record);

    ReturnOrderInfoLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReturnOrderInfoLog record);

    int updateByPrimaryKey(ReturnOrderInfoLog record);

    /**
     * 批量插入日志。
     * @param logs
     * @return
     */
    int insertBatch(List<ReturnOrderInfoLog> logs);
}