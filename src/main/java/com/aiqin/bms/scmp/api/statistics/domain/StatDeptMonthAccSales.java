package com.aiqin.bms.scmp.api.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class StatDeptMonthAccSales {

    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty(value="月")
    @JsonProperty("stat_month")
    private Long statMonth;

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

    @ApiModelProperty(value="商品属性code(1代表A品，2代表B品，3代表C品，5代表D品，6代表其他)")
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

    @ApiModelProperty(value="累计渠道成本")
    @JsonProperty("channel_sales_cost_acc")
    private BigDecimal channelSalesCostAcc;

    @ApiModelProperty(value="累计渠道成本同比")
    @JsonProperty("channel_sales_cost_acc_yearonyear")
    private BigDecimal channelSalesCostAccYearonyear;

    @ApiModelProperty(value="累计渠道成本环比")
    @JsonProperty("channel_sales_cost_acc_link_rela")
    private BigDecimal channelSalesCostAccLinkRela;

    @ApiModelProperty(value="累计渠道销售额")
    @JsonProperty("channel_sales_amount_acc")
    private BigDecimal channelSalesAmountAcc;

    @ApiModelProperty(value="累计渠道销售额同比")
    @JsonProperty("channel_sales_amount_acc_yearonyear")
    private BigDecimal channelSalesAmountAccYearonyear;

    @ApiModelProperty(value="累计渠道销售额环比")
    @JsonProperty("channel_sales_amount_acc_link_rela")
    private BigDecimal channelSalesAmountAccLinkRela;

    @ApiModelProperty(value="累计渠道毛利")
    @JsonProperty("channel_margin_acc")
    private BigDecimal channelMarginAcc;

    @ApiModelProperty(value="累计渠道毛利同比")
    @JsonProperty("channel_margin_acc_yearonyear")
    private BigDecimal channelMarginAccYearonyear;

    @ApiModelProperty(value="累计渠道毛利环比")
    @JsonProperty("channel_margin_acc_link_rela")
    private BigDecimal channelMarginAccLinkRela;

    @ApiModelProperty(value="累计渠道毛利率")
    @JsonProperty("channel_margin_acc_rate")
    private BigDecimal channelMarginAccRate;

    @ApiModelProperty(value="累计渠道毛利率同比")
    @JsonProperty("channel_margin_acc_rate_yearonyear")
    private BigDecimal channelMarginAccRateYearonyear;

    @ApiModelProperty(value="累计渠道毛利率环比")
    @JsonProperty("channel_margin_acc_rate_link_rela")
    private BigDecimal channelMarginAccRateLinkRela;

    @ApiModelProperty(value="累计分销成本")
    @JsonProperty("distribution_sales_cost_acc")
    private BigDecimal distributionSalesCostAcc;

    @ApiModelProperty(value="累计分销成本同比")
    @JsonProperty("distribution_sales_cost_acc_yearonyear")
    private BigDecimal distributionSalesCostAccYearonyear;

    @ApiModelProperty(value="累计分销成本环比")
    @JsonProperty("distribution_sales_cost_acc_link_rela")
    private BigDecimal distributionSalesCostAccLinkRela;

    @ApiModelProperty(value="累计分销销售额")
    @JsonProperty("distribution_sales_amount_acc")
    private BigDecimal distributionSalesAmountAcc;

    @ApiModelProperty(value="累计分销销售额同比")
    @JsonProperty("distribution_sales_amount_acc_yearonyear")
    private BigDecimal distributionSalesAmountAccYearonyear;

    @ApiModelProperty(value="累计分销销售额环比")
    @JsonProperty("distribution_sales_amount_acc_link_rela")
    private BigDecimal distributionSalesAmountAccLinkRela;

    @ApiModelProperty(value="累计分销毛利")
    @JsonProperty("distribution_margin_acc")
    private BigDecimal distributionMarginAcc;

    @ApiModelProperty(value="累计分销毛利同比")
    @JsonProperty("distribution_margin_acc_yearonyear")
    private BigDecimal distributionMarginAccYearonyear;

    @ApiModelProperty(value="累计分销毛利环比")
    @JsonProperty("distribution_margin_acc_link_rela")
    private BigDecimal distributionMarginAccLinkRela;

    @ApiModelProperty(value="累计分销毛利率")
    @JsonProperty("distribution_margin_acc_rate")
    private BigDecimal distributionMarginAccRate;

    @ApiModelProperty(value="累计分销毛利率同比")
    @JsonProperty("distribution_margin_acc_rate_yearonyear")
    private BigDecimal distributionMarginAccRateYearonyear;

    @ApiModelProperty(value="累计分销毛利率环比")
    @JsonProperty("distribution_margin_acc_rate_link_rela")
    private BigDecimal distributionMarginAccRateLinkRela;

    @ApiModelProperty(value="累计渠道预算")
    @JsonProperty("channel_budget_acc")
    private BigDecimal channelBudgetAcc;

    @ApiModelProperty(value="累计分销预算")
    @JsonProperty("distribution_budget_acc")
    private BigDecimal distributionBudgetAcc;

    @ApiModelProperty(value="渠道累计达成率")
    @JsonProperty("channel_acc_achievement")
    private BigDecimal channelAccAchievement;

    @ApiModelProperty(value="分销累计达成率")
    @JsonProperty("distribution_acc_achievement")
    private BigDecimal distributionAccAchievement;

    @ApiModelProperty(value="渠道累计占比")
    @JsonProperty("channe_acc_rate")
    private BigDecimal channeAccRate;

    @ApiModelProperty(value="分销累计占比")
    @JsonProperty("distribution_acc_rate")
    private BigDecimal distributionAccRate;

    @ApiModelProperty(value="渠道同比月累计销售金额")
    @JsonProperty("channel_amount_acc_last_year")
    private BigDecimal channelAmountAccLastYear;

    @ApiModelProperty(value="渠道环比月累计销售金额")
    @JsonProperty("channel_amount_acc_last_month")
    private BigDecimal channelAmountAccLastMonth;

    @ApiModelProperty(value="渠道同比月累计毛利额")
    @JsonProperty("channel_margin_acc_last_year")
    private BigDecimal channelMarginAccLastYear;

    @ApiModelProperty(value="渠道环比月累计毛利额")
    @JsonProperty("channel_margin_acc_last_month")
    private BigDecimal channelMarginAccLastMonth;

    @ApiModelProperty(value="分销同比月累计销售金额")
    @JsonProperty("distribution_amount_acc_last_year")
    private BigDecimal distributionAmountAccLastYear;

    @ApiModelProperty(value="分销环比月累计销售金额")
    @JsonProperty("distribution_amount_acc_last_month")
    private BigDecimal distributionAmountAccLastMonth;

    @ApiModelProperty(value="分销同比月累计毛利额")
    @JsonProperty("distribution_margin_acc_last_year")
    private BigDecimal distributionMarginAccLastYear;

    @ApiModelProperty(value="分销环比月累计毛利额")
    @JsonProperty("distribution_margin_acc_last_month")
    private BigDecimal distributionMarginAccLastMonth;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

}