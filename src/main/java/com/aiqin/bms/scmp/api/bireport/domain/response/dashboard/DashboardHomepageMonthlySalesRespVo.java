package com.aiqin.bms.scmp.api.bireport.domain.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("首页跳转的月不累计respVo")
@Data
public class DashboardHomepageMonthlySalesRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("年")
    @JsonProperty("stat_year")
    private String statYear;

    @ApiModelProperty("月")
    @JsonProperty("stat_month")
    private String statMonth;

    @ApiModelProperty("渠道成本")
    @JsonProperty("channel_costs")
    private Integer channelCosts;

    @ApiModelProperty("渠道销售额")
    @JsonProperty("channel_amount")
    private Integer channelAmount;

    @ApiModelProperty("同比渠道销售额")
    @JsonProperty("pre_channel_amount")
    private Integer preChannelAmount;

    @ApiModelProperty("渠道毛利")
    @JsonProperty("channel_margin")
    private Integer channelMargin;

    @ApiModelProperty("分销销售额")
    @JsonProperty("distribution_amount")
    private Integer distributionAmount;

    @ApiModelProperty("同期分销销售额")
    @JsonProperty("pre_distribution_amount")
    private Integer preDistributionAmount;

    @ApiModelProperty("同期预算额")
    @JsonProperty("pre_supplier_content")
    private Integer preSupplierContent;

    @ApiModelProperty("分销毛利")
    @JsonProperty("distribution_margin")
    private Integer distributionMargin;

    @ApiModelProperty("总利润")
    @JsonProperty("margin_total")
    private Integer marginTotal;

    @ApiModelProperty("当期预算额")
    @JsonProperty("supplier_content")
    private Integer supplierContent;

    @ApiModelProperty("当前达成率")
    @JsonProperty("curr_achieve_rate")
    private Double currAchieveRate;

    @ApiModelProperty("同比达成率")
    @JsonProperty("pre_achieve_rate")
    private Double preAchieveRate;
}
