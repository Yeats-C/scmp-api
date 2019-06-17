package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoLog;

import java.util.List;

public interface OrderInfoLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfoLog record);

    int insertSelective(OrderInfoLog record);

    OrderInfoLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfoLog record);

    int updateByPrimaryKey(OrderInfoLog record);
    /**
     * 批量插入日志信心
     * @author NullPointException
     * @date 2019/6/15
     * @param logs
     * @return int
     */
    int insertBatch(List<OrderInfoLog> logs);
}