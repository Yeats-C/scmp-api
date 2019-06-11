package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPurchaseInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPurchaseInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPurchaseInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 14:46
 */
public interface ProductSkuPurchaseInfoService {
    int insertDraft(ProductSkuPurchaseInfoDraft productSkuPurchaseInfoDraft);

    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    int insertApplyList(List<ApplyProductSkuPurchaseInfo> applyProductSkuPurchaseInfos);

    int saveList(String skuCode, String applyCode);

    int insertList(List<ProductSkuPurchaseInfo> productSkuPurchaseInfos);

    int update(ProductSkuPurchaseInfo productSkuPurchaseInfo);

    int insert(ProductSkuPurchaseInfo productSkuPurchaseInfo);

    /**
     * 获取SKU采购配置信息-临时表
     * @param skuCode
     * @return
     */
    List<PurchaseSaleStockRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);

}
