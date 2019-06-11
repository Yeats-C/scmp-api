package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPrice;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPrice;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceDraft;
import com.aiqin.bms.scmp.api.product.domain.request.variableprice.VariblePriceUpdate;
import com.aiqin.bms.scmp.api.product.domain.response.variableprice.PriceDetailedResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/28 0028 17:36
 */
public interface ProductSkuPriceDao {
    /**
     * 批量新增sku价格草稿
     * @param productSkuPriceDrafts
     * @return
     */
    int insertDraftList(List<ProductSkuPriceDraft> productSkuPriceDrafts);

    int insertList(List<ProductSkuPrice> productSkuPrices);

    int insertApplyList(List<ApplyProductSkuPrice> applyProductSkuPrices);

    List<ProductSkuPriceDraft> getDraft(String skuCode);

    List<ProductSkuPrice> getInfo(String skuCode);

    List<ProductSkuPriceDraft> getDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteList(String skuCode);

    Integer updateList(@Param("list") List<VariblePriceUpdate> list);

    PriceDetailedResponse getPriceSku(@Param("priceSkuId") Long priceSkuId);

    List<ApplyProductSkuPrice> getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<ApplyProductSkuPrice> getApplys(@Param("applyProductSkus") List<ApplyProductSku> applyProductSkus);

}
