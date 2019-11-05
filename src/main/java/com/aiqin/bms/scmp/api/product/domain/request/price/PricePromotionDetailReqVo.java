package com.aiqin.bms.scmp.api.product.domain.request.price;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 促销规则表
 * PricePromotionDetail
 * 数据库表：price_promotion_detail
 */
@Data
@ApiModel("促销规则请求vo")
public class PricePromotionDetailReqVo extends PageReq {
    /**
     *
     * 表字段 : price_promotion_detail.id
     */
    @ApiModelProperty("主键id")
    private Long id;

    /**
     * 所属促销id
     * 表字段 : price_promotion_detail.promotion_id
     */
    @ApiModelProperty("所属促销id")
    private Long promotionId;

    /**
     * 条件数量
     * 表字段 : price_promotion_detail.condition_num
     */
    @ApiModelProperty("条件数量")
    private Float conditionNum;

    /**
     * 赠送多少箱产品
     * 表字段 : price_promotion_detail.give_num
     */
    @ApiModelProperty("赠送数量")
    private Float giveNum;

    /**
     * 1:买赠 ,2:满赠,3:满减,4:满折
     * 表字段 : price_promotion_detail.promotion_type
     */
    @ApiModelProperty(" 促销类型 1:买赠 ,2:满赠,3:满减,4:满折")
    private Integer promotionType;

    /**
     * 0:金额 1:数量
     * 表字段 : price_promotion_detail.rule_type
     */
    @ApiModelProperty(" 规则类型：0:金额 1:数量")
    private Integer ruleType;

    /**
     * 是否被标注 0：是 1：否
     * 表字段 : price_promotion_detail.is_sign
     */
    @ApiModelProperty("是否被标识")
    private Integer isSign;

    /**
     * 规则对应的列表
     */
    @ApiModelProperty("规则对应的列表")
    private List<PricePromotionProductReqVo> pricePromotionProductReqVoList;

}