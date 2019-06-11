package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuStockInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;

import java.util.List;

public interface ProductSkuStockInfoDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuStockInfoDraft record);

    int insertSelective(ProductSkuStockInfoDraft record);

    ProductSkuStockInfoDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuStockInfoDraft record);

    int updateByPrimaryKey(ProductSkuStockInfoDraft record);

    List<PurchaseSaleStockRespVo> getList(String skuCode);

    Integer delete(List<String> skuCodes);
}