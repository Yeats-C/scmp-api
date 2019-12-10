package com.aiqin.bms.scmp.api.statistics.domain.response.oem;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-09-18
 **/
@Data
public class OemSaleInfoResponse {

    @ApiModelProperty(value="oem的子集")
    @JsonProperty("subset_list")
    private List<OemSaleInfoResponse> subsetList;

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

    @ApiModelProperty(value="本期销售数量")
    @JsonProperty("sales_num")
    private Long salesNum;

    @ApiModelProperty(value="同期销售数量")
    @JsonProperty("pre_sales_num")
    private Long preSalesNum;

    @ApiModelProperty(value="销售数量WOW")
    @JsonProperty("sales_num_wow")
    private BigDecimal salesNumWow;

    @ApiModelProperty(value="本期销售金额")
    @JsonProperty("sales_amount")
    private BigDecimal salesAmount;

    @ApiModelProperty(value="同期销售金额")
    @JsonProperty("pre_sales_amount")
    private BigDecimal preSalesAmount;

    @ApiModelProperty(value="本期销售金额(公司)")
    @JsonProperty("com_sales_amount")
    private BigDecimal comSalesAmount;

    @ApiModelProperty(value="同期销售金额(公司)")
    @JsonProperty("pre_com_sales_amount")
    private BigDecimal preComSalesAmount;

    @ApiModelProperty(value="销售金额VS")
    @JsonProperty("sales_amount_vs")
    private BigDecimal salesAmountVs;

    @ApiModelProperty(value="销售金额WOW")
    @JsonProperty("sales_amount_wow")
    private BigDecimal salesAmountWow;

    @ApiModelProperty(value="本期销售占比")
    @JsonProperty("sales_rate")
    private BigDecimal salesRate;

    @ApiModelProperty(value="同期销售额占比")
    @JsonProperty("pre_sales_rate")
    private BigDecimal preSalesRate;

    @ApiModelProperty(value="售额占比WOW")
    @JsonProperty("sales_rate_wow")
    private BigDecimal salesRateWow;

    @ApiModelProperty(value="本期毛利额")
    @JsonProperty("sales_margin")
    private BigDecimal salesMargin;

    @ApiModelProperty(value="同期销售毛利额")
    @JsonProperty("pre_sales_margin")
    private BigDecimal preSalesMargin;

    @ApiModelProperty(value="本期毛利额(公司)")
    @JsonProperty("com_sales_margin")
    private BigDecimal comSalesMargin;

    @ApiModelProperty(value="同期毛利额(公司)")
    @JsonProperty("pre_com_sales_margin")
    private BigDecimal preComSalesMargin;

    @ApiModelProperty(value="销售毛利额WOW")
    @JsonProperty("sales_margin_wow")
    private BigDecimal salesMarginWow;

    @ApiModelProperty(value="本期毛利占比")
    @JsonProperty("sales_margin_ratio")
    private BigDecimal salesMarginRatio;

    @ApiModelProperty(value="同期毛利占比")
    @JsonProperty("pre_sales_margin_ratio")
    private BigDecimal preSalesMarginRatio;

    @ApiModelProperty(value="毛利占比WOW")
    @JsonProperty("sales_margin_ratio_wow")
    private BigDecimal salesMarginRatioWow;

    @ApiModelProperty(value="本期毛利率")
    @JsonProperty("sales_margin_rate")
    private BigDecimal salesMarginRate;

    @ApiModelProperty(value="同期毛利率")
    @JsonProperty("pre_sales_margin_rate")
    private BigDecimal preSalesMarginRate;

    @ApiModelProperty(value="毛利率WOW")
    @JsonProperty("sales_margin_rate_wow")
    private BigDecimal salesMarginRateWow;

}
