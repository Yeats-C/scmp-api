package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.request.SkuSaleVolumeVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;

/**
 * @author knight.xie
 * @className SkuSaleVolumeService
 * @date 2019/1/3 11:28
 * @description sku销量统计
 * @version 1.0
 */
public interface SkuSaleVolumeService {

    /**
     *
     * 功能描述: 批量插入sku销量
     * @param skuSaleVolumes
     * @auther knight.xie
     * @date 2019/1/3 11:35
     */
    HttpResponse saveBatch(List<SkuSaleVolumeVo> skuSaleVolumes);

}
