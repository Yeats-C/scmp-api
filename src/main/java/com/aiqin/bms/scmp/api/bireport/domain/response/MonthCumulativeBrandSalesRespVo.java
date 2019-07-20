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

    @ApiModelProperty("日期")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("月份")
    @JsonProperty("month")
    private String month;

    @ApiModelProperty("月累计")
    @JsonProperty("cumulative_month")
    private String cumulativeMonth;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("渠道编码")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("渠道")
    @JsonProperty("order_original")
    private String orderOriginal;

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

    @ApiModelProperty("渠道销售额")
    @JsonProperty("channel_sales")
    private String channelSales;

    @ApiModelProperty("去年同期渠道销售额")
    @JsonProperty("channel_last_year_sales")
    private String channelLastYearSales;

    @ApiModelProperty("上月渠道销售额")
    @JsonProperty("channel_last_month_sales")
    private String channelLastMonthSales;

    @ApiModelProperty("渠道同比")
    @JsonProperty("channel_compared_same")
    private String channelComparedSame;

    @ApiModelProperty("渠道环比")
    @JsonProperty("channel_sequential")
    private String channelSequential;

    @ApiModelProperty("分销销售额")
    @JsonProperty("distribution_sales")
    private String distributionSales;

    @ApiModelProperty("去年同期分销销售额")
    @JsonProperty("distribution_last_year_sales")
    private String distributionLastYearSales;

    @ApiModelProperty("上月分销销售额")
    @JsonProperty("distribution_last_month_sales")
    private String distributionLastMonthSales;

    @ApiModelProperty("分销同比")
    @JsonProperty("distribution_compared_same")
    private String distributionComparedSame;

    @ApiModelProperty("分销环比")
    @JsonProperty("distribution_sequential")
    private String distributionSequential;


    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;

}
