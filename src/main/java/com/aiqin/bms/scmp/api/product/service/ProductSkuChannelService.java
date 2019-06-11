package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChannelDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuChannelRespVo;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuChannelService
 * @date 2019/5/7 17:32
 * @description TODO
 */
public interface ProductSkuChannelService {
    /**
     * 保存信息到临时表
     * @param productSkuChannelDrafts
     * @return
     */
    int insertDraftList(List<ProductSkuChannelDraft> productSkuChannelDrafts);

    /**
     * 通过SKU获取临时表渠道信息
     * @param skuCode
     * @return
     */
    List<ProductSkuChannelRespVo> getList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);
}
