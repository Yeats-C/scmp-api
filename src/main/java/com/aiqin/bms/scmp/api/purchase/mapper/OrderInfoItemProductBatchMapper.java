package com.aiqin.bms.scmp.api.purchase.mapper;


import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;

import java.util.List;

public interface OrderInfoItemProductBatchMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfoItemProductBatch record);

    int insertSelective(OrderInfoItemProductBatch record);

    OrderInfoItemProductBatch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfoItemProductBatch record);

    int updateByPrimaryKey(OrderInfoItemProductBatch record);
    /**
     * 批量插入数据
     * @author NullPointException
     * @date 2019/6/21
     * @param list
     * @return int
     */
    int insertBatch(List<OrderInfoItemProductBatch> list);
}