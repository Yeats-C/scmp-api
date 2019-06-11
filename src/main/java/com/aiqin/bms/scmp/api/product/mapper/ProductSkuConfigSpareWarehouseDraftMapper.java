package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuConfigSpareWarehouseDraft;

import java.util.List;

public interface ProductSkuConfigSpareWarehouseDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuConfigSpareWarehouseDraft record);

    int insertSelective(ProductSkuConfigSpareWarehouseDraft record);

    ProductSkuConfigSpareWarehouseDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuConfigSpareWarehouseDraft record);

    int updateByPrimaryKey(ProductSkuConfigSpareWarehouseDraft record);

    int insertBatch(List<ProductSkuConfigSpareWarehouseDraft> draftList);

    int deleteByConfigCode(String configCode);

    List<ProductSkuConfigSpareWarehouseDraft> getListByConfigCodes(List<String> configCodes);

    int deleteByConfigCodes(List<String> configCodes);
}