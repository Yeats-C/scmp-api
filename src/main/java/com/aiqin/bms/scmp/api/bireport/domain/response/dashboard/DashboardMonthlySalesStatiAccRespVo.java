package com.aiqin.bms.scmp.api.bireport.domain.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("月销售情况（月累计）respVo")
@Data
public class DashboardMonthlySalesStatiAccRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty("月")
    @JsonProperty("stat_month")
    private Long statMonth;

    @ApiModelProperty("同比渠道累计额")
    @JsonProperty("pre_acc_sum_channel_amount")
    private BigDecimal preAccSumChannelAmount;

    @ApiModelProperty("累计渠道销售额")
    @JsonProperty("acc_sum_channel_amount")
    private BigDecimal accSumChannelAmount;

    @ApiModelProperty("渠道累计毛利额")
    @JsonProperty("acc_sum_channel_margin")
    private BigDecimal accSumChannelMargin;

    @ApiModelProperty("累计预算同比额")
    @JsonProperty("pre_supplier_content")
    private BigDecimal preSupplierContent;

    @ApiModelProperty("当前累计渠道达成率")
    @JsonProperty("curr_achieve_rate")
    private BigDecimal currAchieveRate;

    @ApiModelProperty("同比渠道达成率")
    @JsonProperty("pre_achieve_rate")
    private BigDecimal preAchieveRate;

}
