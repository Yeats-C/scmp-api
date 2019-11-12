package com.aiqin.bms.scmp.api.product.domain.response.price;

import com.aiqin.bms.scmp.api.product.domain.request.price.PriceApplyPromotionReqVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2019-11-12 10:36
 * @Description:
 */
@Data
public class LoadGeneratePromotionRespVo {

    /**
     *陈列列表
     */
    @ApiModelProperty("陈列列表")
    private List<PriceApplyPromotionRespVo> priceApplyPromotionRespVos;

    /**
     * 买赠列表
     */
    @ApiModelProperty("买赠列表")
    private List<PricePromotionDetailRespVo> buyPromotionDetailList;

    /**
     * 满赠列表
     */
    @ApiModelProperty("满赠列表")
    private List<PricePromotionDetailRespVo> enoughPromotionDetailList;

    /**
     * 满减列表
     */
    @ApiModelProperty("满减列表")
    private List<PricePromotionDetailRespVo> reducePromotionDetailList;

    /**
     * 满折列表
     */
    @ApiModelProperty("满折列表")
    private List<PricePromotionDetailRespVo> discountPromotionDetailList;


}
