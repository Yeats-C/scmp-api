package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuConfigSpareWarehouse;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface ProductSkuConfigSpareWarehouseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuConfigSpareWarehouse record);

    int insertSelective(ProductSkuConfigSpareWarehouse record);

    ProductSkuConfigSpareWarehouse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuConfigSpareWarehouse record);

    int updateByPrimaryKey(ProductSkuConfigSpareWarehouse record);

    int deleteByConfigCodes(List<String> skuCodes);

    int insertBatch(List<ProductSkuConfigSpareWarehouse> skuConfigSpareWarehouses);
    @MapKey("configCode")
    Map<String, ProductSkuConfigSpareWarehouse> selectByConfigCode(List<String> list);
}