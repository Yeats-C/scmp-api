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
@Api(tags = "销售统计总计实体")
public class SaleSumResponse {

    @ApiModelProperty(value="部门的集合")
    @JsonProperty("dept_list")
    private List<SaleDeptResponse> deptList;

    @ApiModelProperty(value="渠道成本")
    @JsonProperty("channel_sales_cost")
    private Long channelSalesCost;

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

    @ApiModelProperty(value="分销成本")
    @JsonProperty("distribution_sales_cost")
    private Long distributionSalesCost;

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

}
