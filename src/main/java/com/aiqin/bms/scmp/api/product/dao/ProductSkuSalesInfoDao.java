package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuSalesInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSalesInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSalesInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;
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

    List<PurchaseSaleStockRespVo> getRespVoBySkuCode(String skuCode);

    List<ProductSkuSalesInfoDraft> getDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteList(String skuCode);

    List<ApplyProductSkuSalesInfo> getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<PurchaseSaleStockRespVo> getApplys(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<String> getSalesCodes(@Param("list") List<String> salesCodes, @Param("skuCode") String skuCode);
    List<String> getApplySalesCodes(@Param("list") List<String> salesCodes, @Param("skuCode") String skuCode);
    List<String> getDraftSalesCodes(@Param("list") List<String> salesCodes, @Param("skuCode") String skuCode);

    List<ApplyProductSkuSalesInfo> getApplysByApplyCode(@Param("applyCode") String applyCode);

    List<String> getSalesCodesByApplyCode(String applyCode);

    List<String> getApplySalesCodesApplyCode(String applyCode);

    List<String> getDraftSalesCodesApplyCode(String applyCode);

    PurchaseSaleStockRespVo selectBarCodeBySkuCode(@Param("skuCode")  String skuCode);
}
