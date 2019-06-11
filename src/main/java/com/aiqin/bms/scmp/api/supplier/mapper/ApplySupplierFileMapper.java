package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplierFile;

public interface ApplySupplierFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplySupplierFile record);

    int insertSelective(ApplySupplierFile record);

    ApplySupplierFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplySupplierFile record);

    int updateByPrimaryKey(ApplySupplierFile record);
}