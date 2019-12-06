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
     * 获取赠送是否有重复的条件数量
     * @return
     */
    Float getConditionNum(@Param("promotionId")Long promotionId,@Param("businessId") Long businessId,@Param("productId") Long productId);

    /**
     * 获取满赠商品code
     * @param businessId
     * @return
     */
    List<String> getGiveCodes(Long businessId);

    /**
     * 商品接口
     * @param priceApplyPromotionReqVo
     * @return
     */
    List<PricePromotionProductRespVo> skuList(PricePromotionProductReqVo priceApplyPromotionReqVo);
}
