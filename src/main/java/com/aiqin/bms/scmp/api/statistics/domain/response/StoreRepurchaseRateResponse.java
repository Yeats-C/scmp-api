package com.aiqin.bms.scmp.api.statistics.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhao shuai
 * @create: 2019-08-23
 **/
@Data
public class StoreRepurchaseRateResponse {

    @ApiModelProperty(value="年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty(value="月")
    @JsonProperty("stat_month")
    private Long statMonth;

    @ApiModelProperty(value="部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="部门名")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value="省区code")
    @JsonProperty("province_code")
    private String provinceCode;

    @ApiModelProperty(value="省区")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value="销售数量")
    @JsonProperty("sales_num")
    private Long salesNum;

    @ApiModelProperty(value="渠道销售金额")
    @JsonProperty("channel_sales_amount")
    private Long channelSalesAmount;

    @ApiModelProperty(value="分销销售金额")
    @JsonProperty("distribution_sales_amount")
    private Long distributionSalesAmount;

    @ApiModelProperty(value="购物频次")
    @JsonProperty("shopping_frequency")
    private Long shoppingFrequency;

    @ApiModelProperty(value="复购率")
    @JsonProperty("repurchase_rate")
    private BigDecimal repurchaseRate;

    @ApiModelProperty(value="渠道销售金额小计")
    @JsonProperty("subtotal_channel_sales_amount")
    private Long subtotalChannelSalesAmount;

    @ApiModelProperty(value="分销销售金额小计")
    @JsonProperty("subtotal_distribution_sales_amount")
    private Long subtotalDistributionSalesAmount;

    @ApiModelProperty(value="购物频次小计")
    @JsonProperty("subtotal_shopping_frequency")
    private Long subtotalShoppingFrequency;

    @ApiModelProperty(value="复购率小计")
    @JsonProperty("subtotal_repurchase_rate")
    private BigDecimal subtotalRepurchaseRate;

    @ApiModelProperty(value="渠道销售金额小计")
    @JsonProperty("sum_channel_sales_amount")
    private Long sumChannelSalesAmount;

    @ApiModelProperty(value="分销销售金额小计")
    @JsonProperty("sum_distribution_sales_amount")
    private Long sumDistributionSalesAmount;

    @ApiModelProperty(value="购物频次小计")
    @JsonProperty("sum_shopping_frequency")
    private Long sumShoppingFrequency;

    @ApiModelProperty(value="复购率小计")
    @JsonProperty("sum_repurchase_rate")
    private BigDecimal sumRepurchaseRate;
}
