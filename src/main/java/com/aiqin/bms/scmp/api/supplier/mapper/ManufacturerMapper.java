package com.aiqin.bms.scmp.api.supplier.mapper;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.Manufacturer;

public interface ManufacturerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Manufacturer record);

    int insertSelective(Manufacturer record);

    Manufacturer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Manufacturer record);

    int updateByPrimaryKey(Manufacturer record);
}