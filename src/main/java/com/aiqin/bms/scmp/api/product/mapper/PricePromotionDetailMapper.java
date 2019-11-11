package com.aiqin.bms.scmp.api.product.mapper;

import com.aiqin.bms.scmp.api.product.domain.request.price.PricePromotionDetailReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionDetailRespVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2019-11-05 15:07
 * @Description:
 */
@Mapper
public interface PricePromotionDetailMapper {
    /**
     * 对规则进行批量添加
      * @param pricePromotionDetailList
     * @return
     */
    Integer insertSelective(List<PricePromotionDetailReqVo> pricePromotionDetailList);

    /**
     * 对规则进行批量添加
     * @return
     */
    Integer insert(PricePromotionDetailReqVo pricePromotionDetailReqVo);
    /**
     * 根据促销获取详情
     * @param
     * @return
     */
    List<PricePromotionDetailRespVo> loadByPromotionId(@Param("promotionId") Long promotionId);

    /**
     * 删除在促销的规则
     * @param id
     */
    void deteleByPromotionId(@Param("promotionId")Long id);
}
