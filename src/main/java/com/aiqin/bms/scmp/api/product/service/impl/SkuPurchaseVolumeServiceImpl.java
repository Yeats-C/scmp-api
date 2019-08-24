package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.mapper.SkuPurchaseVolumeMapper;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.SkuPurchaseVolume;
import com.aiqin.bms.scmp.api.product.domain.request.SkuPurchaseVolumeVo;
import com.aiqin.bms.scmp.api.product.service.SkuPurchaseVolumeService;
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
 * @className SkuPurchaseVolumeServieImpl
 * @date 2019/1/3 11:29
 * @description 进货量统计
 * @version 1.0
 */
@Service
@Slf4j
public class SkuPurchaseVolumeServiceImpl implements SkuPurchaseVolumeService {

    @Autowired
    private SkuPurchaseVolumeMapper skuPurchaseVolumeMapper;
    /**
     * 功能描述: 批量插入sku进货量
     *
     * @param skuPurchaseVolumes
     * @param SkuSaleVolumes
     * @auther knight.xie
     * @date 2019/1/3 11:35
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResponse saveBatch(List<SkuPurchaseVolumeVo> skuPurchaseVolumes) throws BizException{
        try {
            log.info("进入批量插入sku销量表");
            log.info("入参参数:{}", JSONObject.toJSON(skuPurchaseVolumes));
            //检查传入进来的sku,是否已经存在,存在更新,不存在新增
            List<SkuPurchaseVolumeVo> addSkuPurchaseVolumes = Lists.newLinkedList();
            List<SkuPurchaseVolume> updateSkuPurchaseVolumes = Lists.newLinkedList();
            //验证数据
            skuPurchaseVolumes.forEach(item-> {
                //根据skuCode检查销量是否存在
                if(StringUtils.isBlank(item.getSkuCode())){
                    throw new BizException(ResultCode.SAVE_BATCH_SKU_PURCHASE_VOLIME_SKU_EMPTY);
                }
                SkuPurchaseVolume skuPurchaseVolume = skuPurchaseVolumeMapper.selectBySkuCode(item.getSkuCode());
                if(null != skuPurchaseVolume){
                    skuPurchaseVolume.setPurchaseVolume(skuPurchaseVolume.getPurchaseVolume()+item.getPurchaseVolume());
                    updateSkuPurchaseVolumes.add(skuPurchaseVolume);
                }else{
                    addSkuPurchaseVolumes.add(item);
                }
            });
            log.info("需要更新的数据:{}",JSONObject.toJSON(updateSkuPurchaseVolumes));
            log.info("需要新增的数据:{}",JSONObject.toJSON(addSkuPurchaseVolumes));
            if(CollectionUtils.isNotEmpty(updateSkuPurchaseVolumes)){
                skuPurchaseVolumeMapper.updateBatch(updateSkuPurchaseVolumes);
            }
            if(CollectionUtils.isNotEmpty(addSkuPurchaseVolumes)){
                skuPurchaseVolumeMapper.saveBatch(addSkuPurchaseVolumes);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            log.error("error", e);
            if(e instanceof BizException){
                throw e;
            }else{
                throw new BizException(ResultCode.SAVE_BATCH_SKU_PURCHASE_VOLIME_ERROR);
            }

        }
    }

}
