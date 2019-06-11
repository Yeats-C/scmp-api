package com.aiqin.bms.scmp.api.supplier.mapper;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.LogisticsCenterArea;

public interface LogisticsCenterAreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LogisticsCenterArea record);

    int insertSelective(LogisticsCenterArea record);

    LogisticsCenterArea selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LogisticsCenterArea record);

    int updateByPrimaryKey(LogisticsCenterArea record);
}