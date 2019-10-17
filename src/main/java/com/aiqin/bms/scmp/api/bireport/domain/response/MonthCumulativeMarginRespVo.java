package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("月销售达成率部门预算额resp")
@Data
public class MonthCumulativeMarginRespVo {

    @ApiModelProperty("预算额部门名称")
    @JsonProperty("budget_product_name")
    private String budgetProductName;

    @ApiModelProperty("预算额部门")
    @JsonProperty("budget_product")
    private String budgetProduct;

 /*   @ApiModelProperty("渠道预算额")
    @JsonProperty("channel_budget")
    private String channelBudget;

    @ApiModelProperty("分销预算额")
    @JsonProperty("distribution_budget")
    private String distributionBudget;
*/
    @ApiModelProperty("月份")
    @JsonProperty("stat_month")
    private String statMonth;


}
