package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuStockInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuStockInfoService
 * @date 2019/5/9 15:12
 * @description TODO
 */
public interface ProductSkuStockInfoService {

    /**
     * 保存临时表数据
     * @param draft
     * @return
     */
    int insertDraft(ProductSkuStockInfoDraft draft);

    /**
     * 获取SKU库存配置信息-临时表
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
