package com.aiqin.bms.scmp.api.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class StatOemBrandSalesMonthly {

    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty(value="月")
    @JsonProperty("stat_month")
    private Long statMonth;

    @ApiModelProperty(value="渠道code")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty(value="渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty(value="品牌code")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty(value="品牌")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty(value="")
    @JsonProperty("lv2")
    private String lv2;

    @ApiModelProperty(value="二级品类")
    @JsonProperty("lv2_category_name")
    private String lv2CategoryName;

    @ApiModelProperty(value="本期销售数量(OEM)")
    @JsonProperty("sales_num")
    private Long salesNum;

    @ApiModelProperty(value="同期销售数量(OEM)")
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

    @ApiModelProperty(value="本期销售金额(OEM)")
    @JsonProperty("sales_amount")
    private BigDecimal salesAmount;

    @ApiModelProperty(value="同期销售金额(OEM)")
    @JsonProperty("pre_sales_amount")
    private BigDecimal preSalesAmount;

    @ApiModelProperty(value="本期销售金额(公司)")
    @JsonProperty("com_sales_amount")
    private BigDecimal comSalesAmount;

    @ApiModelProperty(value="同期销售金额(公司)")
    @JsonProperty("pre_com_sales_amount")
    private BigDecimal preComSalesAmount;

    @ApiModelProperty(value="本期-同期")
    @JsonProperty("sales_amount_vs")
    private BigDecimal salesAmountVs;

    @ApiModelProperty(value="本期/同期-1")
    @JsonProperty("sales_amount_wow")
    private BigDecimal salesAmountWow;

    @ApiModelProperty(value="本期各品牌销售额占比")
    @JsonProperty("brand_sales_rate")
    private BigDecimal brandSalesRate;

    @ApiModelProperty(value="同期各品牌销售额占比")
    @JsonProperty("pre_brand_sales_rate")
    private BigDecimal preBrandSalesRate;

    @ApiModelProperty(value="本期各品牌销售额占比-同期各品牌销售额占比")
    @JsonProperty("brand_sales_rate_wow")
    private BigDecimal brandSalesRateWow;

    @ApiModelProperty(value="销售毛利")
    @JsonProperty("sales_margin")
    private BigDecimal salesMargin;

    @ApiModelProperty(value="同期销售毛利")
    @JsonProperty("pre_sales_margin")
    private BigDecimal preSalesMargin;

    @ApiModelProperty(value="本期毛利额(公司)")
    @JsonProperty("com_sales_margin")
    private BigDecimal comSalesMargin;

    @ApiModelProperty(value="同期毛利额(公司)")
    @JsonProperty("pre_com_sales_margin")
    private BigDecimal preComSalesMargin;

    @ApiModelProperty(value="本期/同期-1")
    @JsonProperty("sales_margin_wow")
    private BigDecimal salesMarginWow;

    @ApiModelProperty(value="本期各品牌毛利占比")
    @JsonProperty("brand_sales_margin_rate")
    private BigDecimal brandSalesMarginRate;

    @ApiModelProperty(value="同期各品牌毛利占比")
    @JsonProperty("pre_brand_sales_margin_rate")
    private BigDecimal preBrandSalesMarginRate;

    @ApiModelProperty(value="本期各品牌毛利占比-同期各品牌毛利占比")
    @JsonProperty("brand_sales_margin_rate_wow")
    private BigDecimal brandSalesMarginRateWow;

    @ApiModelProperty(value="本期毛利率")
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