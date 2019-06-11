package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFileDraft;

import java.util.List;

public interface ProductSkuFileDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuFileDraft record);

    int insertSelective(ProductSkuFileDraft record);

    ProductSkuFileDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuFileDraft record);

    int updateByPrimaryKey(ProductSkuFileDraft record);

    Integer delete(List<String> skuCodes);
}