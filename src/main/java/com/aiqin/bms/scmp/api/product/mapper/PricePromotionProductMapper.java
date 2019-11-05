package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionProductReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionProductRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2019-11-05 15:07
 * @Description:
 */
public interface PricePromotionProductMapper {
    Integer insertSelective(List<PricePromotionProductReqVo> pricePromotionProductReqVoList);

    List<PricePromotionProductRespVo> loadByBusinessId(@Param("businessId") Long businessId);

    void deteleByBusinessId(Long id);
}
