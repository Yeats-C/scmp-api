package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;

public interface OrderInfoItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfoItem record);

    int insertSelective(OrderInfoItem record);

    OrderInfoItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfoItem record);

    int updateByPrimaryKey(OrderInfoItem record);
}