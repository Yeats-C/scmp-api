package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnit;

public interface ProductSkuSupplyUnitMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSupplyUnit record);

    int insertSelective(ProductSkuSupplyUnit record);

    ProductSkuSupplyUnit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSupplyUnit record);

    int updateByPrimaryKey(ProductSkuSupplyUnit record);
}