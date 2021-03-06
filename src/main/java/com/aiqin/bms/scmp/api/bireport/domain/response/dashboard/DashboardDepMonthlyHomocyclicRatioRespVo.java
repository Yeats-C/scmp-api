package com.aiqin.bms.scmp.api.bireport.domain.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("当月部门销售同环比")
@Data
public class DashboardDepMonthlyHomocyclicRatioRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("月")
    @JsonProperty("stat_month")
    private String statMonth;

    @ApiModelProperty("年")
    @JsonProperty("stat_year")
    private String statYear;

    @ApiModelProperty("渠道编码")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty("渠道名称")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty("部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("部门名")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("渠道成本")
    @JsonProperty("channel_costs")
    private BigDecimal channelCosts;

    @ApiModelProperty("渠道销售额")
    @JsonProperty("channel_sales_amount")
    private BigDecimal channelSalesAmount;

    @ApiModelProperty("渠道销售同比")
    @JsonProperty("channel_sales_yearonyear")
    private BigDecimal channelSalesYearonyear;

    @ApiModelProperty("渠道销售环比")
    @JsonProperty("channel_sales_link_relative")
    private BigDecimal channelSalesLinkRelative;

    @ApiModelProperty("渠道毛利")
    @JsonProperty("channel_margin")
    private BigDecimal channelMargin;

    @ApiModelProperty("渠道毛利同比")
    @JsonProperty("channel_margin_yearonyear")
    private BigDecimal channelMarginYearonyear;

    @ApiModelProperty("渠道毛利环比")
    @JsonProperty("channel_margin_link_relative")
    private BigDecimal channelMarginLinkRelative;

    @ApiModelProperty("分销销售额")
    @JsonProperty("distribution_sales_amount")
    private BigDecimal distributionSalesAmount;

    @ApiModelProperty("分销毛利额")
    @JsonProperty("distribution_margin")
    private BigDecimal distributionMargin;

    @ApiModelProperty("渠道成本同比")
    @JsonProperty("channel_costs_yearonyear")
    private BigDecimal channelCostsYearonyear;

    @ApiModelProperty("渠道成本环比")
    @JsonProperty("channel_costs_link_relative")
    private BigDecimal channelCostsLinkRelative;
}