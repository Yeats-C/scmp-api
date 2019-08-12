package com.aiqin.bms.scmp.api.bireport.domain.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("月销售情况（月累计）respVo")
@Data
public class DashboardMonthlySalesStatiAccRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("年")
    @JsonProperty("stat_year")
    private String statYear;

    @ApiModelProperty("月")
    @JsonProperty("stat_month")
    private String statMonth;

    @ApiModelProperty("部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("渠道成本")
    @JsonProperty("channel_costs")
    private Integer channelCosts;

    @ApiModelProperty("当前渠道销售额")
    @JsonProperty("channel_amount")
    private Integer channelAmount;

    @ApiModelProperty("同比渠道销售额")
    @JsonProperty("pre_channel_amount")
    private Integer preChannelAmount;

    @ApiModelProperty("当前渠道累计额")
    @JsonProperty("acc_sum_channel_amount")
    private Integer accSumChannelAmount;

    @ApiModelProperty("同比渠道累计额")
    @JsonProperty("pre_acc_sum_channel_amount")
    private Integer preAccSumChannelAmount;

    @ApiModelProperty("渠道毛利")
    @JsonProperty("channel_margin")
    private Integer channelMargin;

    @ApiModelProperty("渠道累计毛利额")
    @JsonProperty("acc_sum_channel_margin")
    private Integer accSumChannelMargin;

    @ApiModelProperty("分销销售额")
    @JsonProperty("distribution_amount")
    private Integer distributionAmount;

    @ApiModelProperty("分销毛利额")
    @JsonProperty("distribution_margin")
    private Integer distributionMargin;

    @ApiModelProperty("当月预算额")
    @JsonProperty("supplier_content")
    private Integer supplierContent;

    @ApiModelProperty("同比预算额")
    @JsonProperty("pre_supplier_content")
    private Integer preSupplierContent;

    @ApiModelProperty("当月达成率")
    @JsonProperty("curr_achieve_rate")
    private String currAchieveRate;

    @ApiModelProperty("同比达成率")
    @JsonProperty("pre_achieve_rate")
    private String preAchieveRate;

}
