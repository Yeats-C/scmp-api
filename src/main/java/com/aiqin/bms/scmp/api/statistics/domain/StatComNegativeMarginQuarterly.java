package com.aiqin.bms.scmp.api.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class StatComNegativeMarginQuarterly {

    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty(value="季度")
    @JsonProperty("quarter")
    private Long quarter;

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

    @ApiModelProperty(value="品类code")
    @JsonProperty("lv1")
    private String lv1;

    @ApiModelProperty(value="品类")
    @JsonProperty("lv1_category_name")
    private String lv1CategoryName;

    @ApiModelProperty(value="季度销售数量")
    @JsonProperty("quarter_sales_num")
    private Long quarterSalesNum;

    @ApiModelProperty(value="渠道销售额")
    @JsonProperty("quarter_channel_sales_amount")
    private Long quarterChannelSalesAmount;

    @ApiModelProperty(value="渠道销售额同比")
    @JsonProperty("q_channel_sales_amount_yearonyear")
    private BigDecimal qChannelSalesAmountYearonyear;

    @ApiModelProperty(value="渠道成本")
    @JsonProperty("quarter_channel_sales_cost")
    private Long quarterChannelSalesCost;

    @ApiModelProperty(value="渠道毛利")
    @JsonProperty("quarter_channel_margin")
    private Long quarterChannelMargin;

    @ApiModelProperty(value="渠道毛利同比")
    @JsonProperty("q_channel_margin_yearonyear")
    private BigDecimal qChannelMarginYearonyear;

    @ApiModelProperty(value="分销销售额")
    @JsonProperty("quarter_distribution_sales_amount")
    private Long quarterDistributionSalesAmount;

    @ApiModelProperty(value="分销销售额同比")
    @JsonProperty("q_distribution_sales_amount_yearonyear")
    private BigDecimal qDistributionSalesAmountYearonyear;

    @ApiModelProperty(value="分销毛利")
    @JsonProperty("quarter_distribution_margin")
    private Long quarterDistributionMargin;

    @ApiModelProperty(value="分销毛利同比")
    @JsonProperty("q_distribution_margin_yearonyear")
    private BigDecimal qDistributionMarginYearonyear;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

}