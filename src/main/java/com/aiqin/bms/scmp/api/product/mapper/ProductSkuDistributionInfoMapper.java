package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDistributionInfo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;

import java.util.List;

public interface ProductSkuDistributionInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuDistributionInfo record);

    int insertSelective(ProductSkuDistributionInfo record);

    ProductSkuDistributionInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuDistributionInfo record);

    int updateByPrimaryKey(ProductSkuDistributionInfo record);

    List<PurchaseSaleStockRespVo> getList(String skuCode);

}