package com.aiqin.bms.scmp.api.statistics.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class StatComStoreRepurchaseRate {

    @JsonProperty("id")
    private Long id;

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

    @ApiModelProperty(value="连续2次购买次数")
    @JsonProperty("repurchase_num")
    private Long repurchaseNum;

    @ApiModelProperty(value="购买总次数")
    @JsonProperty("purchase_num")
    private Long purchaseNum;

    @ApiModelProperty(value="复购率")
    @JsonProperty("repurchase_rate")
    private BigDecimal repurchaseRate;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}