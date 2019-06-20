package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;

public interface ReturnOrderInfoInspectionItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReturnOrderInfoInspectionItem record);

    int insertSelective(ReturnOrderInfoInspectionItem record);

    ReturnOrderInfoInspectionItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReturnOrderInfoInspectionItem record);

    int updateByPrimaryKey(ReturnOrderInfoInspectionItem record);
}