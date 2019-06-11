package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.ProductBrowse;

public interface ProductBrowseDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductBrowse record);

    int insertSelective(ProductBrowse record);

    ProductBrowse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductBrowse record);

    int updateByPrimaryKey(ProductBrowse record);
}