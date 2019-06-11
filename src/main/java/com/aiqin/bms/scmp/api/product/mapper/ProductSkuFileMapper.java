package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFile;

public interface ProductSkuFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuFile record);

    int insertSelective(ProductSkuFile record);

    ProductSkuFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuFile record);

    int updateByPrimaryKey(ProductSkuFile record);
}