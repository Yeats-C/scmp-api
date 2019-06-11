package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitCapacityDraft;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuSupplyUnitCapacityService
 * @date 2019/5/8 15:55
 * @description TODO
 */
public interface ProductSkuSupplyUnitCapacityService {

    /**
     * 批量添加临时表
     * @param draftList
     * @return
     */
    int insertDraftList(List<ProductSkuSupplyUnitCapacityDraft> draftList);


    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);
}
