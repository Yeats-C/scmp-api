package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPictures;

public interface ProductSkuPicturesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPictures record);

    int insertSelective(ProductSkuPictures record);

    ProductSkuPictures selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPictures record);

    int updateByPrimaryKey(ProductSkuPictures record);
}