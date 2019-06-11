package com.aiqin.bms.scmp.api.supplier.mapper;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.LogisticsCenter;

public interface LogisticsCenterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LogisticsCenter record);

    int insertSelective(LogisticsCenter record);

    LogisticsCenter selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LogisticsCenter record);

    int updateByPrimaryKey(LogisticsCenter record);
}