package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItem;

import java.util.List;

public interface OrderInfoItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfoItem record);

    int insertSelective(OrderInfoItem record);

    OrderInfoItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfoItem record);

    int updateByPrimaryKey(OrderInfoItem record);
    /**
     * 批量插入数据
     * @author NullPointException
     * @date 2019/6/13
     * @param infoItems
     * @return int
     */
    int insertBatch(List<OrderInfoItem> items);
}