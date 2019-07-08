package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuPurchaseInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPurchaseInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPurchaseInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/12 0012 15:14
 */
public interface ProductSkuPurchaseInfoDao {

    ProductSkuPurchaseInfoDraft getDraft(String skuCode);

    ProductSkuPurchaseInfo getInfo(String skuCode);

    List<ProductSkuPurchaseInfoDraft> getDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int insertApplyList(@Param("applyProductSkuPurchaseInfos") List<ApplyProductSkuPurchaseInfo> applyProductSkuPurchaseInfos);

    int insertList(@Param("productSkuPurchaseInfos") List<ProductSkuPurchaseInfo> productSkuPurchaseInfos);

    List<PurchaseSaleStockRespVo> getApplyList(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);


    List<ApplyProductSkuPurchaseInfo> getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

}
