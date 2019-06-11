package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierOperationLog;

public interface SupplierOperationLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierOperationLog record);

    int insertSelective(SupplierOperationLog record);

    SupplierOperationLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierOperationLog record);

    int updateByPrimaryKey(SupplierOperationLog record);
}