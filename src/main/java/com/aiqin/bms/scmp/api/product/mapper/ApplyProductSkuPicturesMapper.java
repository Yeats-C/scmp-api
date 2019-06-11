package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPictures;

public interface ApplyProductSkuPicturesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyProductSkuPictures record);

    int insertSelective(ApplyProductSkuPictures record);

    ApplyProductSkuPictures selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyProductSkuPictures record);

    int updateByPrimaryKey(ApplyProductSkuPictures record);
}