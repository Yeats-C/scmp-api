package com.aiqin.bms.scmp.api.product.service.impl;

import com.aiqin.bms.scmp.api.product.mapper.ProductSkuStockInfoDraftMapper;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuStockInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;
import com.aiqin.bms.scmp.api.product.service.ProductSkuStockInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuStockInfoServiceImpl
 * @date 2019/5/9 15:12
 * @description TODO
 */
@Service
public class ProductSkuStockInfoServiceImpl implements ProductSkuStockInfoService {

    @Autowired
    private ProductSkuStockInfoDraftMapper draftMapper;

    /**
     * 保存临时表数据
     *
     * @param draft
     * @return
     */
    @Override
    @Save
    @Transactional(rollbackFor = Exception.class)
    public int insertDraft(ProductSkuStockInfoDraft draft) {
        return draftMapper.insertSelective(draft);
    }

    /**
     * 获取SKU库存配置信息-临时表
     *
     * @param skuCode
     * @return
     */
    @Override
    public List<PurchaseSaleStockRespVo> getDraftList(String skuCode) {
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
