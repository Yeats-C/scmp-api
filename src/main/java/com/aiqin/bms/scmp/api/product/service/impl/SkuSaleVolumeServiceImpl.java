package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.pojo.SkuSaleVolume;
import com.aiqin.bms.scmp.api.product.domain.request.SkuSaleVolumeVo;
import com.aiqin.bms.scmp.api.product.mapper.SkuSaleVolumeMapper;
import com.aiqin.bms.scmp.api.product.service.SkuSaleVolumeService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author knight.xie
 * @className SkuSaleVolumeServieImpl
 * @date 2019/1/3 11:29
 * @description 销量统计
 * @version 1.0
 */
@Service
@Slf4j
public class SkuSaleVolumeServiceImpl implements SkuSaleVolumeService {

    @Autowired
    private SkuSaleVolumeMapper skuSaleVolumeMapper;
    /**
     * 功能描述: 批量插入sku销量
     *
     * @param skuSaleVolumes
     * @auther knight.xie
     * @date 2019/1/3 11:35
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse saveBatch(List<SkuSaleVolumeVo> skuSaleVolumes){
        log.info("进入批量插入sku销量表");
        log.info("入参参数:{}", JSONObject.toJSON(skuSaleVolumes));
        //检查传入进来的sku,是否已经存在,存在更新,不存在新增
        List<SkuSaleVolumeVo> addSkuSaleVolumes = Lists.newLinkedList();
        List<SkuSaleVolume> updateSkuSaleVolumes = Lists.newLinkedList();
        //验证数据
        skuSaleVolumes.forEach(item -> {
            //根据skuCode检查销量是否存在
            if (StringUtils.isBlank(item.getSkuCode())) {
                throw new BizException(ResultCode.SAVE_BATCH_SKU_SALE_VOLIME_SKU_EMPTY);
            }
            SkuSaleVolume skuSaleVolume = skuSaleVolumeMapper.selectBySkuCode(item.getSkuCode());
            if (null != skuSaleVolume) {
                skuSaleVolume.setSaleVolume(skuSaleVolume.getSaleVolume() + item.getSaleVolume());
                updateSkuSaleVolumes.add(skuSaleVolume);
            } else {
                addSkuSaleVolumes.add(item);
            }
        });
        log.info("需要更新的数据:{}", JSONObject.toJSON(updateSkuSaleVolumes));
        log.info("需要新增的数据:{}", JSONObject.toJSON(addSkuSaleVolumes));
        if (CollectionUtils.isNotEmpty(updateSkuSaleVolumes)) {
            skuSaleVolumeMapper.updateBatch(updateSkuSaleVolumes);
        }
        if (CollectionUtils.isNotEmpty(addSkuSaleVolumes)) {
            skuSaleVolumeMapper.saveBatch(addSkuSaleVolumes);
        }
        return HttpResponse.success();
    }

}
