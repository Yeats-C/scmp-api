package com.aiqin.bms.scmp.api.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class StatDeptNegativeMarginQuarterly {

    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty(value="季度")
    @JsonProperty("quarter")
    private Long quarter;

    @ApiModelProperty(value="所属部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="所属部门")
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

    @ApiModelProperty(value="销售数量")
    @JsonProperty("sales_num")
    private Long salesNum;

    @ApiModelProperty(value="渠道销售额")
    @JsonProperty("channel_sales_amount")
    private BigDecimal channelSalesAmount;

    @ApiModelProperty(value="同期渠道销售金额")
    @JsonProperty("pre_channel_sales_amount")
    private BigDecimal preChannelSalesAmount;

    @ApiModelProperty(value="渠道销售额同比")
    @JsonProperty("channel_sales_amount_yearonyear")
    private BigDecimal channelSalesAmountYearonyear;

    @ApiModelProperty(value="渠道成本")
    @JsonProperty("channel_sales_cost")
    private BigDecimal channelSalesCost;

    @ApiModelProperty(value="渠道毛利")
    @JsonProperty("channel_margin")
    private BigDecimal channelMargin;

    @ApiModelProperty(value="同期渠道毛利")
    @JsonProperty("pre_channel_margin")
    private BigDecimal preChannelMargin;

    @ApiModelProperty(value="渠道毛利同比")
    @JsonProperty("channel_margin_yearonyear")
    private BigDecimal channelMarginYearonyear;

    @ApiModelProperty(value="分销销售额")
    @JsonProperty("distribution_sales_amount")
    private BigDecimal distributionSalesAmount;

    @ApiModelProperty(value="同期分销销售额")
    @JsonProperty("pre_distribution_sales_amount")
    private BigDecimal preDistributionSalesAmount;

    @ApiModelProperty(value="分销销售额同比")
    @JsonProperty("distribution_sales_amount_yearonyear")
    private BigDecimal distributionSalesAmountYearonyear;

    @ApiModelProperty(value="分销毛利")
    @JsonProperty("distribution_margin")
    private BigDecimal distributionMargin;

    @ApiModelProperty(value="同期分销毛利额")
    @JsonProperty("pre_distribution_margin")
    private BigDecimal preDistributionMargin;

    @ApiModelProperty(value="分销毛利同比")
    @JsonProperty("distribution_margin_yearonyear")
    private BigDecimal distributionMarginYearonyear;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

}