package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuConfig;
import com.aiqin.bms.scmp.api.product.domain.request.sku.config.QuerySkuConfigReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigDetailRepsVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.config.SkuConfigsRepsVo;

import java.util.List;

public interface ProductSkuConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuConfig record);

    int insertSelective(ProductSkuConfig record);

    ProductSkuConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuConfig record);

    int updateByPrimaryKey(ProductSkuConfig record);

    int insertBatch(List<ProductSkuConfig> configs);

    int updateBatch(List<ProductSkuConfig> configs);

    List<SkuConfigsRepsVo> getList(QuerySkuConfigReqVo reqVo);

    SkuConfigDetailRepsVo detail(String skuCode);
}