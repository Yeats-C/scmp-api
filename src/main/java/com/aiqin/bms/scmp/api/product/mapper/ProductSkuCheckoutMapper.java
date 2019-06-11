package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuCheckout;

import java.util.List;

public interface ProductSkuCheckoutMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuCheckout record);

    int insertSelective(ProductSkuCheckout record);

    ProductSkuCheckout selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuCheckout record);

    int updateByPrimaryKey(ProductSkuCheckout record);

    Integer delete(List<String> skuCodes);

}