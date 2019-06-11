package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDisInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;

import java.util.List;

public interface ProductSkuDisInfoDraftMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSkuDisInfoDraft record);

    int insertSelective(ProductSkuDisInfoDraft record);

    ProductSkuDisInfoDraft selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuDisInfoDraft record);

    int updateByPrimaryKey(ProductSkuDisInfoDraft record);

    List<PurchaseSaleStockRespVo> getList(String skuCode);

    Integer delete(List<String> skuCodes);
}