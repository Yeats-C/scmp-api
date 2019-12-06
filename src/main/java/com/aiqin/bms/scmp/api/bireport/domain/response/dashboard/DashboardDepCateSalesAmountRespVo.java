package com.aiqin.bms.scmp.api.bireport.domain.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("当月各部门品类下的销售情况respVo")
@Data
public class DashboardDepCateSalesAmountRespVo {

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

    @ApiModelProperty("部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名")
    @JsonProperty("product_category_name")
    private String productCategoryName;

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

    @ApiModelProperty("分销销售额")
    @JsonProperty("distribution_sales_amount")
    private BigDecimal distributionSalesAmount;

    @ApiModelProperty("渠道毛利")
    @JsonProperty("channel_margin")
    private BigDecimal channelMargin;

    @ApiModelProperty("渠道毛利同比")
    @JsonProperty("channel_margin_yearonyear")
    private BigDecimal channelMarginYearonyear;

    @ApiModelProperty("渠道毛利环比")
    @JsonProperty("channel_margin_link_relative")
    private BigDecimal channelMarginLinkRelative;

    @ApiModelProperty("分销毛利")
    @JsonProperty("distribution_margin")
    private BigDecimal distributionMargin;


}
