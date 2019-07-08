package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuChannel;
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
    List<ProductSkuChannelRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);

    /**
     * 批量保存申请数据
     * @param applyProductSkus
     * @return
     */
    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    /**
     * 批量插入申请数据到数据库
     * @param applyProductSkuChannels
     * @return
     */
    int insertApplyList(List<ApplyProductSkuChannel> applyProductSkuChannels);


    /**
     * 通过SKU获取申请表渠道信息
     * @param skuCode
     * @return
     */
    List<ProductSkuChannelRespVo> getApplyList(String skuCode,String applyCode);

    /**
     *
     * 功能描述: 获取sku渠道
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 16:50
     */
    List<ProductSkuChannelRespVo> getList(String skuCode);
}
