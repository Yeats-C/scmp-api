package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuAssociatedGoodsDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuAssociatedGoodsRespVo;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuAssociatedGoodsService
 * @date 2019/5/8 16:05
 * @description TODO
 */
public interface ProductSkuAssociatedGoodsService {

    /**
     * 保存临时表数据
     * @param draftList
     * @return
     */
    int insertDraftList(List<ProductSkuAssociatedGoodsDraft> draftList);

    /**
     * 获取临时表数据
     * @param skuCode
     * @return
     */
    List<ProductSkuAssociatedGoodsRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);
}
