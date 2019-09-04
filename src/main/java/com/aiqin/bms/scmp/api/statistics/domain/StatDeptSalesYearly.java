package com.aiqin.bms.scmp.api.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class StatDeptSalesYearly {
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty(value="部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value="渠道code")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty(value="渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty(value="商品属性code")
    @JsonProperty("product_property_code")
    private Long productPropertyCode;

    @ApiModelProperty(value="商品属性")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty(value="门店类型code(0代表直营，1代表萌贝树，2代表小红马，3代表加盟）")
    @JsonProperty("store_type_code")
    private Long storeTypeCode;

    @ApiModelProperty(value="门店类型")
    @JsonProperty("store_type")
    private String storeType;

    @ApiModelProperty(value="数据类型code(0 代表 经营数据,1 代表 部门数据)")
    @JsonProperty("data_type_code")
    private Long dataTypeCode;

    @ApiModelProperty(value="数据类型")
    @JsonProperty("data_type")
    private String dataType;

    @ApiModelProperty(value="渠道成本")
    @JsonProperty("channel_sales_cost")
    private Long channelSalesCost;

    @ApiModelProperty(value="渠道成本同比")
    @JsonProperty("channel_sales_cost_yearonyear")
    private BigDecimal channelSalesCostYearonyear;

    @ApiModelProperty(value="渠道成本环比")
    @JsonProperty("channel_sales_cost_link_rela")
    private BigDecimal channelSalesCostLinkRela;

    @ApiModelProperty(value="渠道销售额")
    @JsonProperty("channel_sales_amount")
    private Long channelSalesAmount;

    @ApiModelProperty(value="渠道销售额同比")
    @JsonProperty("channel_sales_amount_yearonyear")
    private BigDecimal channelSalesAmountYearonyear;

    @ApiModelProperty(value="渠道销售额环比")
    @JsonProperty("channel_sales_amount_link_rela")
    private BigDecimal channelSalesAmountLinkRela;

    @ApiModelProperty(value="渠道毛利")
    @JsonProperty("channel_margin")
    private Long channelMargin;

    @ApiModelProperty(value="渠道毛利同比")
    @JsonProperty("channel_margin_yearonyear")
    private BigDecimal channelMarginYearonyear;

    @ApiModelProperty(value="渠道毛利环比")
    @JsonProperty("channel_margin_link_rela")
    private BigDecimal channelMarginLinkRela;

    @ApiModelProperty(value="渠道毛利率")
    @JsonProperty("channel_margin_rate")
    private BigDecimal channelMarginRate;

    @ApiModelProperty(value="渠道毛利率同比")
    @JsonProperty("channel_margin_rate_yearonyear")
    private BigDecimal channelMarginRateYearonyear;

    @ApiModelProperty(value="渠道毛利率环比")
    @JsonProperty("channel_margin_rate_link_rela")
    private BigDecimal channelMarginRateLinkRela;

    @ApiModelProperty(value="分销成本")
    @JsonProperty("distribution_sales_cost")
    private Long distributionSalesCost;

    @ApiModelProperty(value="分销成本同比")
    @JsonProperty("distribution_sales_cost_yearonyear")
    private BigDecimal distributionSalesCostYearonyear;

    @ApiModelProperty(value="分销成本环比")
    @JsonProperty("distribution_sales_cost_link_rela")
    private BigDecimal distributionSalesCostLinkRela;

    @ApiModelProperty(value="分销销售额")
    @JsonProperty("distribution_sales_amount")
    private Long distributionSalesAmount;

    @ApiModelProperty(value="分销销售额同比")
    @JsonProperty("distribution_sales_amount_yearonyear")
    private BigDecimal distributionSalesAmountYearonyear;

    @ApiModelProperty(value="分销销售额环比")
    @JsonProperty("distribution_sales_amount_link_rela")
    private BigDecimal distributionSalesAmountLinkRela;

    @ApiModelProperty(value="分销毛利")
    @JsonProperty("distribution_margin")
    private Long distributionMargin;

    @ApiModelProperty(value="分销毛利同比")
    @JsonProperty("distribution_margin_yearonyear")
    private BigDecimal distributionMarginYearonyear;

    @ApiModelProperty(value="分销毛利环比")
    @JsonProperty("distribution_margin_link_rela")
    private BigDecimal distributionMarginLinkRela;

    @ApiModelProperty(value="分销毛利率")
    @JsonProperty("distribution_margin_rate")
    private BigDecimal distributionMarginRate;

    @ApiModelProperty(value="分销毛利率同比")
    @JsonProperty("distribution_margin_rate_yearonyear")
    private BigDecimal distributionMarginRateYearonyear;

    @ApiModelProperty(value="分销毛利率环比")
    @JsonProperty("distribution_margin_rate_link_rela")
    private BigDecimal distributionMarginRateLinkRela;

    @ApiModelProperty(value="渠道预算")
    @JsonProperty("channel_budget")
    private Long channelBudget;

    @ApiModelProperty(value="分销预算")
    @JsonProperty("distribution_budget")
    private Long distributionBudget;

    @ApiModelProperty(value="渠道达成率")
    @JsonProperty("channel_achievement")
    private BigDecimal channelAchievement;

    @ApiModelProperty(value="分销达成率")
    @JsonProperty("distribution_achievement")
    private BigDecimal distributionAchievement;

    @ApiModelProperty(value="渠道占比")
    @JsonProperty("channe_rate")
    private BigDecimal channeRate;

    @ApiModelProperty(value="分销占比")
    @JsonProperty("distribution_rate")
    private BigDecimal distributionRate;

    @ApiModelProperty(value="渠道同比销售金额")
    @JsonProperty("channel_amount_last_year")
    private Long channelAmountLastYear;

    @ApiModelProperty(value="渠道环比销售金额")
    @JsonProperty("channel_amount_last_month")
    private Long channelAmountLastMonth;

    @ApiModelProperty(value="渠道同比毛利额")
    @JsonProperty("channel_margin_last_year")
    private Long channelMarginLastYear;

    @ApiModelProperty(value="渠道环比毛利额")
    @JsonProperty("channel_margin_last_month")
    private Long channelMarginLastMonth;

    @ApiModelProperty(value="分销同比销售金额")
    @JsonProperty("distribution_amount_last_year")
    private Long distributionAmountLastYear;

    @ApiModelProperty(value="分销环比销售金额")
    @JsonProperty("distribution_amount_last_month")
    private Long distributionAmountLastMonth;

    @ApiModelProperty(value="分销同比毛利额")
    @JsonProperty("distribution_margin_last_year")
    private Long distributionMarginLastYear;

    @ApiModelProperty(value="分销环比毛利额")
    @JsonProperty("distribution_margin_last_month")
    private Long distributionMarginLastMonth;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;
}