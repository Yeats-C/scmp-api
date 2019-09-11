package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.request.SkuPurchaseVolumeVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

/**
 * @author knight.xie
 * @className SkuPurchaseVolumeService
 * @date 2019/1/3 11:28
 * @description sku进货量统计
 * @version 1.0
 */
public interface SkuPurchaseVolumeService {

    /**
     *
     * 功能描述: 批量插入sku进货量
     * @param skuPurchaseVolumes
     * @auther knight.xie
     * @date 2019/1/3 11:35
     */
    HttpResponse saveBatch(List<SkuPurchaseVolumeVo> skuPurchaseVolumes);

}
