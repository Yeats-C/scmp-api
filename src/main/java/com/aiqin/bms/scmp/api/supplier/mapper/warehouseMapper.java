package com.aiqin.bms.scmp.api.supplier.mapper;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.Warehouse;

public interface warehouseMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Warehouse record);

    int insertSelective(Warehouse record);

    Warehouse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Warehouse record);

    int updateByPrimaryKey(Warehouse record);
}