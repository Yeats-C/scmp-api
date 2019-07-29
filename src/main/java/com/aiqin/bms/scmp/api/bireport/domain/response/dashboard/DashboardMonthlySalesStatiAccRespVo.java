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

    @ApiModelProperty("年月")
    @JsonProperty("stat_month")
    private String statMonth;

    @ApiModelProperty("渠道成本")
    @JsonProperty("channel_costs")
    private Integer channelCosts;

    @ApiModelProperty("当前渠道销售额")
    @JsonProperty("curr_channel_amount")
    private Integer currChannelAmount;

    @ApiModelProperty("同比渠道销售额")
    @JsonProperty("pre_channel_amount")
    private Integer preChannelAmount;

    @ApiModelProperty("当前渠道累计额")
    @JsonProperty("curr_acc_sum_channel_amount")
    private Integer currAccSumChannelAmount;

    @ApiModelProperty("渠道毛利")
    @JsonProperty("channel_maori")
    private Integer channelMaori;

    @ApiModelProperty("渠道累计毛利额")
    @JsonProperty("acc_sum_channel_maori")
    private Integer accSumChannelMaori;

    @ApiModelProperty("分销销售额")
    @JsonProperty("distribution_amount")
    private Integer distributionAmount;

    @ApiModelProperty("分销毛利额")
    @JsonProperty("distribution_maori")
    private Integer distributionMaori;

    @ApiModelProperty("当月预算额")
    @JsonProperty("curr_supplier_content")
    private Integer currSupplierContent;

    @ApiModelProperty("同比预算额")
    @JsonProperty("pre_supplier_content")
    private Integer preSupplierContent;

    @ApiModelProperty("当月达成率")
    @JsonProperty("curr_achieve_rate")
    private String currAchieveRate;

    @ApiModelProperty("同比达成率")
    @JsonProperty("pre_achieve_rate")
    private String preAchieveRate;

    @ApiModelProperty("同比渠道累计额")
    @JsonProperty("pre_acc_sum_channel_amount")
    private Integer preAccSumChannelAmount;

}
