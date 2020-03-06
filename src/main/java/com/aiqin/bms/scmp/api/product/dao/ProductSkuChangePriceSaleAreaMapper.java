package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.ProductSkuChangePriceSaleArea;


/*
* 功能说明:变价销售区域中间表
*
*/
public interface ProductSkuChangePriceSaleAreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuChangePriceSaleArea record);

    int insertSelective(ProductSkuChangePriceSaleArea record);

    ProductSkuChangePriceSaleArea selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuChangePriceSaleArea record);

    int updateByPrimaryKey(ProductSkuChangePriceSaleArea record);
}