package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductChangeRecord;

public interface ProductChangeRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductChangeRecord record);

    int insertSelective(ProductChangeRecord record);

    ProductChangeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductChangeRecord record);

    int updateByPrimaryKey(ProductChangeRecord record);
}