package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuStockInfo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;

import java.util.List;

public interface ProductSkuStockInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuStockInfo record);

    int insertSelective(ProductSkuStockInfo record);

    ProductSkuStockInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuStockInfo record);

    int updateByPrimaryKey(ProductSkuStockInfo record);

    List<PurchaseSaleStockRespVo> getList(String skuCode);

    ProductSkuStockInfo getBySkuCode(String skuCode);
}