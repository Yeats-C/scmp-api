package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplier;

public interface ApplySupplierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplySupplier record);

    int insertSelective(ApplySupplier record);

    ApplySupplier selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplySupplier record);

    int updateByPrimaryKey(ApplySupplier record);
}