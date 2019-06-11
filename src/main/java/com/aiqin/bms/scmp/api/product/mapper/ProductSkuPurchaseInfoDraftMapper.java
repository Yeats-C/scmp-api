package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPurchaseInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;

import java.util.List;

public interface ProductSkuPurchaseInfoDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuPurchaseInfoDraft record);

    int insertSelective(ProductSkuPurchaseInfoDraft record);

    ProductSkuPurchaseInfoDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuPurchaseInfoDraft record);

    int updateByPrimaryKey(ProductSkuPurchaseInfoDraft record);

    List<PurchaseSaleStockRespVo> getList(String skuCode);

    int delete(List<String> skuCodes);
}