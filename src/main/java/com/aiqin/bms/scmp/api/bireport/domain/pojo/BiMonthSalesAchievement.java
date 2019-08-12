package com.aiqin.bms.scmp.api.bireport.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("月销售达成情况实体Model")
@Data
public class BiMonthSalesAchievement {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("月份")
    @JsonProperty("month")
    private String month;

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

    @ApiModelProperty("数据类型")
    @JsonProperty("data_style")
    private Integer dataStyle;

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

    @ApiModelProperty("渠道销售额")
    @JsonProperty("qun_amount")
    private Integer qunAmount;

    @ApiModelProperty("渠道预算额")
    @JsonProperty("qun_budget")
    private Integer qunBudget;

    @ApiModelProperty("渠道达成率")
    @JsonProperty("qun_yield_rate")
    private Integer qunYieldRate;

    @ApiModelProperty("分销销售额")
    @JsonProperty("fen_amount")
    private Integer fenAmount;

    @ApiModelProperty("分销预算额")
    @JsonProperty("fen_budget")
    private Integer feb_Budget;

    @ApiModelProperty("分销达成率")
    @JsonProperty("fen_yield_rate")
    private Integer fenYieldRate;

}
