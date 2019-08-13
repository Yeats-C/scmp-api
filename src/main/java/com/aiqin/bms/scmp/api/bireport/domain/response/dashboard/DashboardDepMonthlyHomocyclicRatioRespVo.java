package com.aiqin.bms.scmp.api.bireport.domain.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty("部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("部门名")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("渠道成本")
    @JsonProperty("channel_costs")
    private Long channelCosts;

    @ApiModelProperty("渠道销售额")
    @JsonProperty("channel_sales_amount")
    private Long channelSalesAmount;

    @ApiModelProperty("渠道销售同比")
    @JsonProperty("channel_sales_yearonyear")
    private Double channelSalesYearonyear;

    @ApiModelProperty("渠道销售环比")
    @JsonProperty("channel_sales_link_relative")
    private Double channelSalesLinkRelative;

    @ApiModelProperty("渠道毛利")
    @JsonProperty("channel_margin")
    private Long channelMargin;

    @ApiModelProperty("渠道毛利同比")
    @JsonProperty("channel_margin_yearonyear")
    private Double channelMarginYearonyear;

    @ApiModelProperty("渠道毛利环比")
    @JsonProperty("channel_margin_link_relative")
    private Double channelMarginLinkRelative;

    @ApiModelProperty("分销销售额")
    @JsonProperty("distribution_sales_amount")
    private Long distributionSalesAmount;

    @ApiModelProperty("分销毛利额")
    @JsonProperty("distribution_margin")
    private Long distributionMargin;

    @ApiModelProperty("渠道成本同比")
    @JsonProperty("channel_costs_yearonyear")
    private Long channelCostsYearonyear;

    @ApiModelProperty("渠道成本环比")
    @JsonProperty("channel_costs_link_relative")
    private Long channelCostsLinkRelative;
}