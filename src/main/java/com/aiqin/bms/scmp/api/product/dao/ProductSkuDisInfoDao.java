package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuDisInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDisInfoDraft;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuDistributionInfo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.PurchaseSaleStockRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/12 0012 15:21
 */
public interface ProductSkuDisInfoDao {
    ProductSkuDisInfoDraft getDraft(String skuCode);

    ProductSkuDistributionInfo getInfo(String skuCode);

    List<ProductSkuDisInfoDraft> getDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int insertApplyList(@Param("applyProductSkuDisInfos") List<ApplyProductSkuDisInfo> applyProductSkuDisInfos);

    int insertDisInfoList(@Param("productSkuDistributionInfos") List<ProductSkuDistributionInfo> productSkuDistributionInfos);

    ApplyProductSkuDisInfo getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<ApplyProductSkuDisInfo> getApplyInfoByApplyCode(String applyCode);

    List<PurchaseSaleStockRespVo> getApplys(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);
}
