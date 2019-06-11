package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPicturesDraft;

import java.util.List;

public interface ProductSkuPicturesDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPicturesDraft record);

    int insertSelective(ProductSkuPicturesDraft record);

    ProductSkuPicturesDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPicturesDraft record);

    int updateByPrimaryKey(ProductSkuPicturesDraft record);

    Integer delete(List<String> skuCodes);
}