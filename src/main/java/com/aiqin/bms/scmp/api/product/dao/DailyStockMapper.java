package com.aiqin.bms.scmp.api.product.dao;


import com.aiqin.bms.scmp.api.product.domain.pojo.DailyStock;

public interface DailyStockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DailyStock record);

    int insertSelective(DailyStock record);

    DailyStock selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DailyStock record);

    int updateByPrimaryKey(DailyStock record);
}