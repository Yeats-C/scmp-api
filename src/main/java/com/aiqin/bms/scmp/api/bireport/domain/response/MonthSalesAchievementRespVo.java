package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("月销售达成情况respVo")
@Data
public class MonthSalesAchievementRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_run_time")
    private String beginRunTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_run_time")
    private String finishRunTime;

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
    @JsonProperty("store_type_name")
    private String storeTypeName;

    @ApiModelProperty("数据类型code")
    @JsonProperty("data_style_code")
    private String dataStyleCode;

    @ApiModelProperty("数据类型name")
    @JsonProperty("data_style_name")
    private String dataStyleName;

    @ApiModelProperty("品类编码")
    @JsonProperty("category_type_code")
    private String categoryTypeCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_type_name")
    private String categoryTypeName;

    @ApiModelProperty("渠道销售额")
    @JsonProperty("qun_amount")
    private Integer qunAmount;

    @ApiModelProperty("渠道预算额")
    @JsonProperty("qun_budget")
    private Integer qunBudget;

    @ApiModelProperty("渠道达成率")
    @JsonProperty("qun_yield_rate")
    private Double qunYieldRate;

    @ApiModelProperty("分销销售额")
    @JsonProperty("fen_amount")
    private Integer fenAmount;

    @ApiModelProperty("分销预算额")
    @JsonProperty("fen_budget")
    private Integer febBudget;

    @ApiModelProperty("分销达成率")
    @JsonProperty("fen_yield_rate")
    private Double fenYieldRate;
}
