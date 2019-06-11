package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPrice;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPrice;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceDraft;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:12
 */
public interface ProductSkuPriceService {
    int insertDraftList(List<ProductSkuPriceDraft> productSkuPriceDrafts);

    int insertList(List<ProductSkuPrice> productSkuPrices);

    int saveList(String skuCode, String applyCode);

    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    int insertApplyList(List<ApplyProductSkuPrice> applyProductSkuPrices);
}
