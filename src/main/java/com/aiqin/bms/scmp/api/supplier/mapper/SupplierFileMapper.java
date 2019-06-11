package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierFile;

public interface SupplierFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierFile record);

    int insertSelective(SupplierFile record);

    SupplierFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierFile record);

    int updateByPrimaryKey(SupplierFile record);
}