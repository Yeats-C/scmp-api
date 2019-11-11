package com.aiqin.bms.scmp.api.product.domain.response.price;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 促销商品表
 * PricePromotionProduct
 * 数据库表：price_promotion_product
 */
@Data
@ApiModel("促销规则规则实体Vo")
public class PricePromotionProductRespVo {

    /**
     * 
     * 表字段 : price_promotion_product.id
     */
    @ApiModelProperty("主键id")
    private Long id;

    /**
     * 对应促销规则明细的id
     * 表字段 : price_promotion_product.business_id
     */
    @ApiModelProperty("对应促销规则明细的id")
    private Long businessId;

    /**
     * 1:条件商品 2：赠送商品
     * 表字段 : price_promotion_product.product_type
     */
    @ApiModelProperty("商品类型：1:条件商品 2：赠送商品")
    private Integer productType;

    /**
     * 商品编码
     * 表字段 : price_promotion_product.product_code
     */
    @ApiModelProperty("商品编码")
    private String productCode;

    /**
     * 商品名称
     * 表字段 : price_promotion_product.product_name
     */
    @ApiModelProperty("商品名称")
    private String productName;

    /**
     * 商品品类
     * 表字段 : price_promotion_product.category
     */
    @ApiModelProperty("商品品类")
    private String category;

    /**
     * 商品属性
     * 表字段 : price_promotion_product.attribute
     */
    @ApiModelProperty("商品属性")
    private String attribute;



    /**
     * 商品属性
     */
    @ApiModelProperty("库存成本")
    private BigDecimal stockPrice;


    /**
     * 促销分销价格
     * 表字段 : price_promotion_product.promotion_distribution_price
     */
    @ApiModelProperty("分销价格")
    private BigDecimal distributionPrice;

    /**
     * 促销分销毛利
     */
    @ApiModelProperty("促销分销毛利")
    private BigDecimal distributionGrossProfit;

    /**
     * 补贴后分销毛利率
     */
    @ApiModelProperty("补贴后分销毛利率")
    private BigDecimal subsidyDistributionGrossProfit;

    /**
     * 近三个月月均销量
     */
    @ApiModelProperty("近三个月月均销量")
    private BigDecimal averageNumLastThreeMouth;

    /**
     *可用库存数量
     */
    @ApiModelProperty("可用库存数量")
    private Long stockNum;

    /**
     * 近三个月月均销量
     */
    @ApiModelProperty("可用库存金额")
    private BigDecimal stockMoney;

    /**
     * 是否被标注 0：是 1：否
     * 表字段 : price_promotion_detail.is_sign
     */
    @ApiModelProperty("是否被标识")
    private Integer isSign;

    /**
     * 开始时间
     * 表字段 : price_promotion_product.begin_date
     */
    @ApiModelProperty("开始时间")
    private Date beginDate;

    /**
     * 结束时间
     * 表字段 : price_promotion_product.end_date
     */
    @ApiModelProperty("结束时间")
    private Date endDate;

    /**
     * 促销分销价格
     * 表字段 : price_promotion_product.promotion_distribution_price
     */
    @ApiModelProperty("促销分销价格")
    private BigDecimal promotionDistributionPrice;

    /**
     * 补贴成本
     * 表字段 : price_promotion_product.subsidy_cost
     */
    @ApiModelProperty("补贴成本")
    private BigDecimal subsidyCost;

}