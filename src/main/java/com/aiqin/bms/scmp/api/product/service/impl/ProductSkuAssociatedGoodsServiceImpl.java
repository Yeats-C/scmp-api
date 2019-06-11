package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.product.mapper.ProductSkuAssociatedGoodsDraftMapper;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuAssociatedGoodsDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuAssociatedGoodsRespVo;
import com.aiqin.bms.scmp.api.product.service.ProductSkuAssociatedGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuAssociatedGoodsServiceImpl
 * @date 2019/5/8 16:05
 * @description TODO
 */
@Service
public class ProductSkuAssociatedGoodsServiceImpl implements ProductSkuAssociatedGoodsService {
    @Autowired
    private ProductSkuAssociatedGoodsDraftMapper draftMapper;

    /**
     * 保存临时表数据
     *
     * @param draftList
     * @return
     */
    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public int insertDraftList(List<ProductSkuAssociatedGoodsDraft> draftList) {
        return draftMapper.saveBatch(draftList);
    }

    /**
     * 获取临时表数据
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<ProductSkuAssociatedGoodsRespVo> getDraftList(String skuCode) {
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

