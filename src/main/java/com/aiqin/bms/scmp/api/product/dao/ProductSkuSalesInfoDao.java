package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSalesInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSalesInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSalesInfoDraft;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/29 0029 10:15
 */
public interface ProductSkuSalesInfoDao {
    /**
     * 批量新增草稿
     * @param productSkuSalesInfoDrafts
     * @return
     */
    int insertDraftList(List<ProductSkuSalesInfoDraft> productSkuSalesInfoDrafts);

    int insertList(List<ProductSkuSalesInfo> productSkuSalesInfos);

    int insertApplyList(List<ApplyProductSkuSalesInfo> applyProductSkuSalesInfos);

    List<ProductSkuSalesInfoDraft> getDraft(String skuCode);

    List<ProductSkuSalesInfo> getInfo(String skuCode);

    List<ProductSkuSalesInfoDraft> getDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteList(String skuCode);

    List<ApplyProductSkuSalesInfo> getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<ApplyProductSkuSalesInfo> getApplys(@Param("applyProductSkus") List<ApplyProductSku> applyProductSkus);
}
