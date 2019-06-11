package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo;

public interface SupplierDictionaryInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierDictionaryInfo record);

    int insertSelective(SupplierDictionaryInfo record);

    SupplierDictionaryInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierDictionaryInfo record);

    int updateByPrimaryKey(SupplierDictionaryInfo record);
}