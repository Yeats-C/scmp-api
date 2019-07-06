package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuBoxPacking;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuBoxPacking;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuBoxPackingDraft;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuBoxPackingRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/12 0012 15:25
 */
public interface ProductSkuBoxPackingDao {

    List<ProductSkuBoxPackingRespVo> getDraft(String skuCode);

    ProductSkuBoxPacking getInfo(String skuCode);

    List<ProductSkuBoxPackingDraft> getDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int deleteDrafts(@Param("productSkus") List<ApplyProductSku> productSkus);

    int insertApplyList(@Param("applyProductSkuBoxPackings") List<ApplyProductSkuBoxPacking> applyProductSkuBoxPackings);

    ApplyProductSkuBoxPacking getApply(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    List<ProductSkuBoxPackingRespVo> getApplys(@Param("skuCode") String skuCode, @Param("applyCode") String applyCode);

    int insertBoxList(@Param("productSkuBoxPackings") List<ProductSkuBoxPacking> productSkuBoxPackings);
}
