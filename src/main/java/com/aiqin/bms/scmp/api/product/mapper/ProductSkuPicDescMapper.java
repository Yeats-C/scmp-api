package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicDesc;

public interface ProductSkuPicDescMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPicDesc record);

    int insertSelective(ProductSkuPicDesc record);

    ProductSkuPicDesc selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPicDesc record);

    int updateByPrimaryKey(ProductSkuPicDesc record);
}