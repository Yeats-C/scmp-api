package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

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

    @ApiModelProperty("商品属性编码")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty("销售成本")
    @JsonProperty("sales_cost")
    private Long salesCost;

    @ApiModelProperty("渠道销售额")
    @JsonProperty("channel_sales")
    private Long channelSales;

    @ApiModelProperty("去年同期渠道销售额")
    @JsonProperty("channel_last_year_sales")
    private Long channelLastYearSales;

    @ApiModelProperty("上期渠道销售额")
    @JsonProperty("channel_last_month_sales")
    private Long channelLastMonthSales;

    @ApiModelProperty("渠道销售额同比")
    @JsonProperty("channel_compared_same")
    private Double channelComparedSame;

    @ApiModelProperty("渠道销售额环比")
    @JsonProperty("channel_sequential")
    private Double channelSequential;

    @ApiModelProperty("渠道毛利额")
    @JsonProperty("channel_maori")
    private Double channelMaori;

    @ApiModelProperty("渠道毛利率")
    @JsonProperty("channel_maori_rate")
    private Double channelMaoriRate;

    @ApiModelProperty("渠道上期毛利率")
    @JsonProperty("channel_last_month_maori_rate")
    private Double channelLastMonthMaoriRate;

    @ApiModelProperty("去年同期渠道毛利率")
    @JsonProperty("last_year_channel_maori_rate")
    private Double lastYearChannelMaoriRate;

    @ApiModelProperty("渠道毛利率环比")
    @JsonProperty("channel_margin_rate_sequential")
    private Double channelMarginRateSequential;

    @ApiModelProperty("渠道毛利率同比")
    @JsonProperty("channel_gross_margin_compared_same")
    private Double channelGrossMarginComparedSame;

    @ApiModelProperty("分销销售额")
    @JsonProperty("distribution_sales")
    private Long distributionSales;

    @ApiModelProperty("去年同期分销销售额")
    @JsonProperty("distribution_last_year_sales")
    private Long distributionLastYearSales;

    @ApiModelProperty("上期分销销售额")
    @JsonProperty("distribution_last_month_sales")
    private Long distributionLastMonthSales;

    @ApiModelProperty("分销销售额同比")
    @JsonProperty("distribution_compared_same")
    private Double distributionComparedSame;

    @ApiModelProperty("分销销售额环比")
    @JsonProperty("distribution_sequential")
    private Double distributionSequential;

    @ApiModelProperty("分销毛利额")
    @JsonProperty("distribution_maori")
    private Double distributionMaori;

    @ApiModelProperty("分销毛利率")
    @JsonProperty("distribution_maori_rate")
    private Double distributionMaoriRate;

    @ApiModelProperty("分销上期毛利率")
    @JsonProperty("distribution_last_maori_rate")
    private Double distributionLastMaoriRate;

    @ApiModelProperty("去年同期分销毛利率")
    @JsonProperty("last_year_distribution_maori_rate")
    private Double lastYearDistributionMaoriRate;

    @ApiModelProperty("分销毛利率环比")
    @JsonProperty("distribution_margin_rate_sequential")
    private Double distributionMarginRateSequential;

    @ApiModelProperty("分销毛利率同比")
    @JsonProperty("distribution_gross_margin_compared_same")
    private Double distributionGrossMarginComparedSame;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;

}
