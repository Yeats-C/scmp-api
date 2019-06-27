package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSupplyUnitCapacity;
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

    /**
     * 批量保存申请数据
     * @param applyProductSkus
     * @return
     */
    int saveApplyList(List<ApplyProductSku> applyProductSkus);


    /**
     * 批量插入申请数据到数据库
     * @param applyProductSkuSupplyUnitCapacities
     * @return
     */
    int insertApplyList(List<ApplyProductSkuSupplyUnitCapacity> applyProductSkuSupplyUnitCapacities);


}
