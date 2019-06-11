package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSalesInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;

import java.util.List;

public interface ProductSkuSalesInfoDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuSalesInfoDraft record);

    int insertSelective(ProductSkuSalesInfoDraft record);

    ProductSkuSalesInfoDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuSalesInfoDraft record);

    int updateByPrimaryKey(ProductSkuSalesInfoDraft record);

    List<PurchaseSaleStockRespVo> getList(String skuCode);

    Integer delete(List<String> skuCodes);


}