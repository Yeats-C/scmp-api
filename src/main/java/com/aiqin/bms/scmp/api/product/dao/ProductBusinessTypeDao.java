package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.ProductBusinessType;

public interface ProductBusinessTypeDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductBusinessType record);

    int insertSelective(ProductBusinessType record);

    ProductBusinessType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductBusinessType record);

    int updateByPrimaryKey(ProductBusinessType record);
}