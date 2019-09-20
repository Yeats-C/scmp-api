package com.aiqin.bms.scmp.api.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class StatOemCateSalesWeekly {

    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty(value="周")
    @JsonProperty("stat_week")
    private Long statWeek;

    @ApiModelProperty(value="渠道code")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty(value="渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty(value="一级品类编码")
    @JsonProperty("lv1")
    private String lv1;

    @ApiModelProperty(value="一级品类")
    @JsonProperty("lv1_category_name")
    private String lv1CategoryName;

    @ApiModelProperty(value="二级品类编码")
    @JsonProperty("lv2")
    private String lv2;

    @ApiModelProperty(value="二级品类")
    @JsonProperty("lv2_category_name")
    private String lv2CategoryName;

    @ApiModelProperty(value="销售数量")
    @JsonProperty("sales_num")
    private Long salesNum;

    @ApiModelProperty(value="同期销售数量")
    @JsonProperty("pre_sales_num")
    private Long preSalesNum;

    @ApiModelProperty(value="本期销售数量(公司)")
    @JsonProperty("com_sales_num")
    private Long comSalesNum;

    @ApiModelProperty(value="同期销售数量(公司)")
    @JsonProperty("pre_com_sales_num")
    private Long preComSalesNum;

    @ApiModelProperty(value="本期/同期-1")
    @JsonProperty("sales_num_wow")
    private BigDecimal salesNumWow;

    @ApiModelProperty(value="销售金额")
    @JsonProperty("sales_amount")
    private Long salesAmount;

    @ApiModelProperty(value="同期销售金额")
    @JsonProperty("pre_sales_amount")
    private Long preSalesAmount;

    @ApiModelProperty(value="本期销售金额(公司)")
    @JsonProperty("com_sales_amount")
    private Long comSalesAmount;

    @ApiModelProperty(value="同期销售金额(公司)")
    @JsonProperty("pre_com_sales_amount")
    private Long preComSalesAmount;

    @ApiModelProperty(value="本期-同期")
    @JsonProperty("sales_amount_vs")
    private BigDecimal salesAmountVs;

    @ApiModelProperty(value="本期/同期-1")
    @JsonProperty("sales_amount_wow")
    private BigDecimal salesAmountWow;

    @ApiModelProperty(value="本期各品类销售额占比")
    @JsonProperty("cate_sales_rate")
    private BigDecimal cateSalesRate;

    @ApiModelProperty(value="同期各品类销售额占比")
    @JsonProperty("pre_cate_sales_rate")
    private BigDecimal preCateSalesRate;

    @ApiModelProperty(value="本期各品类销售额占比-同期各品类销售额占比")
    @JsonProperty("cate_sales_rate_wow")
    private BigDecimal cateSalesRateWow;

    @ApiModelProperty(value="销售毛利")
    @JsonProperty("sales_margin")
    private Long salesMargin;

    @ApiModelProperty(value="同期销售毛利")
    @JsonProperty("pre_sales_margin")
    private Long preSalesMargin;

    @ApiModelProperty(value="本期毛利额(公司)")
    @JsonProperty("com_sales_margin")
    private Long comSalesMargin;

    @ApiModelProperty(value="同期毛利额(公司)")
    @JsonProperty("pre_com_sales_margin")
    private Long preComSalesMargin;

    @ApiModelProperty(value="本期/同期-1")
    @JsonProperty("sales_margin_wow")
    private BigDecimal salesMarginWow;

    @ApiModelProperty(value="本期各品类毛利占比")
    @JsonProperty("cate_sales_margin_rate")
    private BigDecimal cateSalesMarginRate;

    @ApiModelProperty(value="同期各品类毛利占比")
    @JsonProperty("pre_cate_sales_margin_rate")
    private BigDecimal preCateSalesMarginRate;

    @ApiModelProperty(value="本期各品类毛利占比-同期各品类毛利占比")
    @JsonProperty("cate_sales_margin_rate_wow")
    private BigDecimal cateSalesMarginRateWow;

    @ApiModelProperty(value="毛利率")
    @JsonProperty("sales_margin_rate")
    private BigDecimal salesMarginRate;

    @ApiModelProperty(value="同期毛利率")
    @JsonProperty("pre_sales_margin_rate")
    private BigDecimal preSalesMarginRate;

    @ApiModelProperty(value="本期毛利率-同期毛利率")
    @JsonProperty("sales_margin_rate_wow")
    private BigDecimal salesMarginRateWow;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

}