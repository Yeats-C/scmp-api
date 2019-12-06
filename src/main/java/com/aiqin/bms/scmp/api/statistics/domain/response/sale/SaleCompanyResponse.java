package com.aiqin.bms.scmp.api.statistics.domain.response.sale;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-09-04
 **/
@Data
@Api(tags = "销售统计公司小计实体")
public class SaleCompanyResponse {

    @ApiModelProperty(value="门店类型的集合")
    @JsonProperty("store_list")
    private List<SaleStoreResponse> storeList;

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

    @ApiModelProperty(value="渠道成本")
    @JsonProperty("channel_sales_cost")
    private BigDecimal channelSalesCost;

    @ApiModelProperty(value="渠道销售额")
    @JsonProperty("channel_sales_amount")
    private BigDecimal channelSalesAmount;

    @ApiModelProperty(value="渠道销售额同比")
    @JsonProperty("channel_sales_amount_yearonyear")
    private BigDecimal channelSalesAmountYearonyear;

    @ApiModelProperty(value="渠道销售额环比")
    @JsonProperty("channel_sales_amount_link_rela")
    private BigDecimal channelSalesAmountLinkRela;

    @ApiModelProperty(value="渠道毛利")
    @JsonProperty("channel_margin")
    private BigDecimal channelMargin;

    @ApiModelProperty(value="渠道毛利同比")
    @JsonProperty("channel_margin_yearonyear")
    private BigDecimal channelMarginYearonyear;

    @ApiModelProperty(value="渠道毛利环比")
    @JsonProperty("channel_margin_link_rela")
    private BigDecimal channelMarginLinkRela;

    @ApiModelProperty(value="渠道毛利率")
    @JsonProperty("channel_margin_rate")
    private BigDecimal channelMarginRate;

    @ApiModelProperty(value="分销成本")
    @JsonProperty("distribution_sales_cost")
    private BigDecimal distributionSalesCost;

    @ApiModelProperty(value="分销销售额")
    @JsonProperty("distribution_sales_amount")
    private BigDecimal distributionSalesAmount;

    @ApiModelProperty(value="分销销售额同比")
    @JsonProperty("distribution_sales_amount_yearonyear")
    private BigDecimal distributionSalesAmountYearonyear;

    @ApiModelProperty(value="分销销售额环比")
    @JsonProperty("distribution_sales_amount_link_rela")
    private BigDecimal distributionSalesAmountLinkRela;

    @ApiModelProperty(value="分销毛利")
    @JsonProperty("distribution_margin")
    private BigDecimal distributionMargin;

    @ApiModelProperty(value="分销毛利同比")
    @JsonProperty("distribution_margin_yearonyear")
    private BigDecimal distributionMarginYearonyear;

    @ApiModelProperty(value="分销毛利环比")
    @JsonProperty("distribution_margin_link_rela")
    private BigDecimal distributionMarginLinkRela;

    @ApiModelProperty(value="分销毛利率")
    @JsonProperty("distribution_margin_rate")
    private BigDecimal distributionMarginRate;

    @ApiModelProperty(value="渠道占比")
    @JsonProperty("channe_rate")
    private BigDecimal channeRate;

    @ApiModelProperty(value="分销占比")
    @JsonProperty("distribution_rate")
    private BigDecimal distributionRate;

    @ApiModelProperty(value="渠道同比销售金额")
    @JsonProperty("channel_amount_last_year")
    private BigDecimal channelAmountLastYear;

    @ApiModelProperty(value="渠道环比销售金额")
    @JsonProperty("channel_amount_last_month")
    private BigDecimal channelAmountLastMonth;

    @ApiModelProperty(value="渠道同比毛利额")
    @JsonProperty("channel_margin_last_year")
    private BigDecimal channelMarginLastYear;

    @ApiModelProperty(value="渠道环比毛利额")
    @JsonProperty("channel_margin_last_month")
    private BigDecimal channelMarginLastMonth;

    @ApiModelProperty(value="分销同比销售金额")
    @JsonProperty("distribution_amount_last_year")
    private BigDecimal distributionAmountLastYear;

    @ApiModelProperty(value="分销环比销售金额")
    @JsonProperty("distribution_amount_last_month")
    private BigDecimal distributionAmountLastMonth;

    @ApiModelProperty(value="分销同比毛利额")
    @JsonProperty("distribution_margin_last_year")
    private BigDecimal distributionMarginLastYear;

    @ApiModelProperty(value="分销环比毛利额")
    @JsonProperty("distribution_margin_last_month")
    private BigDecimal distributionMarginLastMonth;

}
