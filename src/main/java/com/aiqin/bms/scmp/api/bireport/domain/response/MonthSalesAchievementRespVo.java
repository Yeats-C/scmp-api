package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@ApiModel("月销售达成情况respVo")
@Data
public class MonthSalesAchievementRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("日期")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("年")
    @JsonProperty("stat_year")
    private String statYear;

    @ApiModelProperty("月份")
    @JsonProperty("stat_month")
    private String statMonth;

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
    @JsonProperty("channel_amount")
    private Long channelAmount;

    @ApiModelProperty("渠道预算额")
    @JsonProperty("channel_budget")
    private Long channelBudget;

    @ApiModelProperty("渠道达成率")
    @JsonProperty("channel_yield_rate")
    private Double channelYieldRate;

    @ApiModelProperty("分销销售额")
    @JsonProperty("distribution_amount")
    private Long distributionlAmount;

    @ApiModelProperty("分销预算额")
    @JsonProperty("distribution_budget")
    private Long distributionbBudget;

    @ApiModelProperty("分销达成率")
    @JsonProperty("distribution_yield_rate")
    private Double distributionYieldRate;


    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;
}
