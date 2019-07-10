package com.aiqin.bms.scmp.api.bireport.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("月累计品类销售情况实体Model")
@Data
public class BiMonthCumulativeBrandSales {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("月份")
    @JsonProperty("month")
    private String month;

    @ApiModelProperty("月累计")
    @JsonProperty("cumulative_month")
    private Integer cumulativeMonth;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("渠道编码")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty("渠道名称")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty("门店类型")
    @JsonProperty("store_type")
    private Integer storeType;

    @ApiModelProperty("销售额")
    @JsonProperty("data_type")
    private String dataType;

    @ApiModelProperty("去年同期销售额")
    @JsonProperty("last_year_sales")
    private Integer lastYearSales;

    @ApiModelProperty("上月销售额")
    @JsonProperty("last_month_sales")
    private Integer lastMonthSales;

    @ApiModelProperty("同比")
    @JsonProperty("compared_same")
    private Integer comparedSame;

    @ApiModelProperty("环比")
    @JsonProperty("sequential")
    private Integer sequential;

}
