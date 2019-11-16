package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionProductRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2019-11-05 15:07
 * @Description:
 */
public interface PricePromotionProductMapper {

    Integer insertSelective(@Param("pricePromotionProductReqVoList")List<PricePromotionProductReqVo> pricePromotionProductReqVoList);

    List<PricePromotionProductRespVo> loadByBusinessId(@Param("businessId") Long businessId);

    void deteleByBusinessId(@Param("businessId") Long businessId);

    BasePage<PricePromotionRespVo> list(PricePromotionReqVo priceApplyPromotionReqVo);

    /**
     * 获取赠送codes
     * @param promotionId
     * @param businessId
     * @param productCode
     * @return
     */
    List<PricePromotionProductRespVo> getGiveCodes(Long promotionId, Long businessId, String productCode);
}
