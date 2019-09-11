package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@ApiModel("月累计品类销售情况respVo")
@Data
public class MonthCumulativeBrandSalesRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("年月")
    @JsonProperty("year_month")
    private String yearMonth;

    @ApiModelProperty("渠道编码")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty("渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty("门店类型code")
    @JsonProperty("store_type_code")
    private String storeTypeCode;

    @ApiModelProperty("门店类型名称")
    @JsonProperty("store_type")
    private String storeType;

    @ApiModelProperty("数据类型code")
    @JsonProperty("data_type_code")
    private String dataTypeCode;

    @ApiModelProperty("数据类型name")
    @JsonProperty("data_type")
    private String dataType;

    @ApiModelProperty("品类编码")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("商品属性编码")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty("销售成本累计")
    @JsonProperty("total_stock_cost")
    private Long totalStockCost;

    @ApiModelProperty("渠道销售额累计")
    @JsonProperty("total_channel_amount_daily")
    private Long totalChannelAmountDaily;

    @ApiModelProperty("去年同期渠道销售额累计")
    @JsonProperty("total_last_year_channel_amount_daily")
    private Long totalLastYearChannelAmountDaily;

    @ApiModelProperty("上期渠道销售额累计")
    @JsonProperty("total_last_month_channel_amount_daily")
    private Long totalLastMonthChannelAmountDaily;

    @ApiModelProperty("渠道销售额累计同比")
    @JsonProperty("total_channel_amount_daily_compared_same")
    private BigDecimal totalChannelAmountDailyComparedSame;

    @ApiModelProperty("渠道销售额累计环比")
    @JsonProperty("total_channel_amount_daily_sequential")
    private BigDecimal totalChannelAmountDailySequential;

    @ApiModelProperty("渠道毛利额累计")
    @JsonProperty("total_channel_maori")
    private Long totalChannelMaori;

    @ApiModelProperty("渠道毛利率累计")
    @JsonProperty("total_channel_maori_rate")
    private BigDecimal totalChannelMaoriRate;

    @ApiModelProperty("去年同期渠道毛利率累计")
    @JsonProperty("total_last_year_channel_maori_rate")
    private BigDecimal totalLastYearChannelMaoriRate;

    @ApiModelProperty("上期渠道毛利率累计")
    @JsonProperty("total_last_month_channel_maori_rate")
    private BigDecimal totalLastMonthChannelMaoriRate;

    @ApiModelProperty("渠道毛利率累计同比")
    @JsonProperty("total_channel_gross_margin_compared_same")
    private BigDecimal totalChannelGrossMarginComparedSame;

    @ApiModelProperty("渠道毛利率累计环比")
    @JsonProperty("total_channel_gross_margin_rate_sequential")
    private BigDecimal totalChannelGrossMarginRateSequential;

    @ApiModelProperty("分销销售额累计")
    @JsonProperty("total_distribution_amount_daily")
    private Long totalDistributionAmountDaily;

    @ApiModelProperty("去年同期分销销售额累计")
    @JsonProperty("total_last_year_distribution_amount_daily")
    private Long totalLastYearDistributionAmountDaily;

    @ApiModelProperty("上期分销销售额累计")
    @JsonProperty("total_last_month_distribution_amount_daily")
    private Long totalLastMonthDistributionAmountDaily;

    @ApiModelProperty("分销销售额累计同比")
    @JsonProperty("total_distribution_amount_daily_compared_same")
    private BigDecimal totalDistributionAmountDailyComparedSame;

    @ApiModelProperty("分销销售额累计环比")
    @JsonProperty("total_distribution_amount_daily_sequential")
    private BigDecimal totalDistributionAmountDailySequential;

    @ApiModelProperty("分销毛利额累计")
    @JsonProperty("total_distribution_amount")
    private Long totalDistributionAmount;

    @ApiModelProperty("分销毛利率累计")
    @JsonProperty("total_distribution_maori_rate")
    private BigDecimal totalDistributionMaoriRate;

    @ApiModelProperty("去年同期分销毛利率累计")
    @JsonProperty("total_last_year_distribution_maori_rate")
    private BigDecimal totalLastYearDistributionMaoriRate;

    @ApiModelProperty("上期分销毛利率累计")
    @JsonProperty("total_last_month_distribution_maori_rate")
    private BigDecimal totalLastMonthDistributionMaoriRate;

    @ApiModelProperty("分销毛利率累计环比")
    @JsonProperty("total_distribution_margin_rate_sequential")
    private BigDecimal totalDistributionMarginRateSequential;

    @ApiModelProperty("分销毛利率累计同比")
    @JsonProperty("total_distribution_gross_margin_compared_same")
    private BigDecimal totalDistributionGrossMarginComparedSame;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;

}
