package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionary;

public interface SupplierDictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierDictionary record);

    int insertSelective(SupplierDictionary record);

    SupplierDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierDictionary record);

    int updateByPrimaryKey(SupplierDictionary record);
}