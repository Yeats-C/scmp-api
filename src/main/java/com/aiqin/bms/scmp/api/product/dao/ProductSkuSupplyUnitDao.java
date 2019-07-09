package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnit;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSupplyUnitDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 10:17
 */
public interface ProductSkuSupplyUnitDao {
    /**
     * 批量新增草稿
     * @param productSkuSupplyUnitDrafts
     * @return
     */
    int insertDraftList(List<ProductSkuSupplyUnitDraft> productSkuSupplyUnitDrafts);

    int insertList(List<ProductSkuSupplyUnit> productSkuSupplyUnits);

    int insertApplyList(List<ApplyProductSkuSupplyUnit> applyProductSkuSupplyUnits);

    List<ProductSkuSupplyUnitRespVo> getDraft(String skuCode);

    List<ProductSkuSupplyUnit> getInfo(String skuCode);

    List<ProductSkuSupplyUnitDraft> getDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteList(String skuCode);

    List<ApplyProductSkuSupplyUnit> getApply(@Param("skuCode") String skuCode,@Param("applyCode") String applyCode);

    List<ProductSkuSupplyUnitRespVo> getApplys(@Param("skuCode") String skuCode,@Param("applyCode") String applyCode);
    /**
     * 查询供应商
     * @author NullPointException
     * @date 2019/7/2
     * @param skuCode
     * @return java.util.List<com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo>
     */
    List<ProductSkuSupplyUnitRespVo> selectBySkuCode(String skuCode);
}
