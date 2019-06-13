package com.aiqin.bms.scmp.api.purchase.mapper;


import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfo;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
}