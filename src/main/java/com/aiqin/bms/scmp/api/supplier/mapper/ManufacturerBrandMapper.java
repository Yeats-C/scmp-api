package com.aiqin.bms.scmp.api.supplier.mapper;


import com.aiqin.bms.scmp.api.supplier.domain.pojo.ManufacturerBrand;

public interface ManufacturerBrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ManufacturerBrand record);

    int insertSelective(ManufacturerBrand record);

    ManufacturerBrand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ManufacturerBrand record);

    int updateByPrimaryKey(ManufacturerBrand record);
}