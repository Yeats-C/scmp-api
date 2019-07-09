package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.*;

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


    /**
     *
     * 功能描述: 根据供应商信息查询
     *
     * @param skuSupplyUnitDrafts
     * @return
     * @auther knight.xie
     * @date 2019/7/4 16:46
     */
    List<ProductSkuSupplyUnitCapacityDraft> getDraftsBySupplyUnitDrafts(List<ProductSkuSupplyUnitDraft> skuSupplyUnitDrafts);

    /**
     *
     * 功能描述: 根据Ids批量删除
     *
     * @param ids
     * @return
     * @auther knight.xie
     * @date 2019/7/4 16:53
     */
    int deleteDraftByIds(List<Long> ids);

    /**
     *
     * 功能描述: 根据申请编码保存正式数据
     *
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/4 20:30
     */
    int saveList(String skuCode,String applyCode);

    /**
     *
     * 功能描述: 批量插入数据库
     *
     * @param capacities
     * @return
     * @auther knight.xie
     * @date 2019/7/4 20:52
     */
    int insertList( List<ProductSkuSupplyUnitCapacity> capacities);

}
