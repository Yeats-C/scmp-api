package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;

import java.util.List;

public interface ReturnOrderInfoItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReturnOrderInfoItem record);

    int insertSelective(ReturnOrderInfoItem record);

    ReturnOrderInfoItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReturnOrderInfoItem record);

    int updateByPrimaryKey(ReturnOrderInfoItem record);
    /**
     * 批量插入数据
     * @author NullPointException
     * @date 2019/6/19
     * @param orderItems
     * @return int
     */
    int insertBatch(List<ReturnOrderInfoItem> orderItems);
}