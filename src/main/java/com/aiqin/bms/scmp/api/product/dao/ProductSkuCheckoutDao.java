package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.*;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuCheckoutRespVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/12 0012 14:26
 */
public interface ProductSkuCheckoutDao {
    ProductSkuCheckoutRespVo getDraftInfo(String skuCode);

    ProductSkuCheckoutRespVo getBySkuCode(String skuCode);

    ProductSkuCheckout getInfo(String skuCode);

    int insertApply(@Param("applyProductSkuCheckouts") List<ApplyProductSkuCheckout> applyProductSkuCheckouts);

    List<ProductSkuCheckoutDraft> getDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);



    List<ProductSkuCheckout> getSkuCheckOuts(List<String> skuCodes);

    ApplyProductSkuCheckout getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<ApplyProductSkuCheckout> getApplys( @Param("applyCode") String applyCode);

    ProductSkuCheckoutRespVo getApplyInfo(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    int insertCheckOuts(@Param("productSkuCheckouts") List<ProductSkuCheckout> productSkuCheckouts);

    @MapKey("skuCode")
    Map<String, ProductSkuCheckoutRespVo> selectBySkuCodes(@Param("list") Set<String> skuList);
}
