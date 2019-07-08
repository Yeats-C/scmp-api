package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 15:32
 */
public interface ProductSkuSupplyUnitService {
    int insertDraftList(List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts);

    int insertList(List<ProductSkuSupplyUnit> productSkuSupplyUnits);

    int saveList(String skuCode,String applyCode);

    int insertApplyList(List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits);

    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    /**
     * 供应商信息-临时表
     * @param skuCode
     * @return
     */
    List<ProductSkuSupplyUnitRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);
    /**
     * 查询供应商
     * @author NullPointException
     * @date 2019/7/2
     * @param skuCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo>
     */
    List<ProductSkuSupplyUnitRespVo> selectBySkuCode(String skuCode);
    
    /**
     *
     * 功能描述: 根据Id批量查询临时表信息
     *
     * @param ids
     * @return 
     * @auther knight.xie
     * @date 2019/7/4 16:09
     */
    List<ProductSkuSupplyUnitDraft> getDraftByIds(List<Long> ids);

    /**
     *
     * 功能描述: 根据Id批量删除临时表信息
     *
     * @param ids
     * @return
     * @auther knight.xie
     * @date 2019/7/4 16:19
     */
    int deleteDraftByIds(List<Long> ids);

    /**
     *
     * 功能描述: 获取申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return 
     * @auther knight.xie
     * @date 2019/7/6 22:59
     */
    List<ProductSkuSupplyUnitRespVo> getApply(String skuCode, String applyCode);
}
