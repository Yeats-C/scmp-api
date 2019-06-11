package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuConfigSpareWarehouse;

import java.util.List;

public interface ProductSkuConfigSpareWarehouseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuConfigSpareWarehouse record);

    int insertSelective(ProductSkuConfigSpareWarehouse record);

    ProductSkuConfigSpareWarehouse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuConfigSpareWarehouse record);

    int updateByPrimaryKey(ProductSkuConfigSpareWarehouse record);

    int deleteByConfigCodes(List<String> skuCodes);

    int insertBatch(List<ProductSkuConfigSpareWarehouse> skuConfigSpareWarehouses);
}