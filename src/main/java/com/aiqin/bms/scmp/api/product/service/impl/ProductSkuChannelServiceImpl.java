package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.product.mapper.ProductSkuChannelDraftMapper;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuChannelDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuChannelRespVo;
import com.aiqin.bms.scmp.api.product.service.ProductSkuChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuChannelServiceImpl
 * @date 2019/5/7 17:33
 * @description TODO
 */
@Service
public class ProductSkuChannelServiceImpl implements ProductSkuChannelService {

    @Autowired
    private ProductSkuChannelDraftMapper draftMapper;
    /**
     * 保存信息到临时表
     *
     * @param productSkuChannelDrafts
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SaveList
    public int insertDraftList(List<ProductSkuChannelDraft> productSkuChannelDrafts) {
        return draftMapper.saveBatch(productSkuChannelDrafts);
    }

    /**
     * 通过SKU获取临时表渠道信息
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<ProductSkuChannelRespVo> getList(String skuCode) {
        return draftMapper.getList(skuCode);
    }

    /**
     * 删除临时表数据
     *
     * @param skuCodes
     * @return
     */
    @Override
    public Integer deleteDrafts(List<String> skuCodes) {
        return draftMapper.delete(skuCodes);
    }
}
