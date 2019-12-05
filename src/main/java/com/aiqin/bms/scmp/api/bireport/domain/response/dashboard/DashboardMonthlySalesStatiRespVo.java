package com.aiqin.bms.scmp.api.bireport.domain.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("月销售情况（不累计）respVo")
@Data
public class DashboardMonthlySalesStatiRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty("月")
    @JsonProperty("stat_month")
    private Long statMonth;

    @ApiModelProperty("部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("渠道成本")
    @JsonProperty("channel_costs")
    private BigDecimal channelCosts;

    @ApiModelProperty("当前渠道销售额")
    @JsonProperty("channel_amount")
    private BigDecimal channelAmount;

    @ApiModelProperty("同比渠道销售额")
    @JsonProperty("pre_channel_amount")
    private BigDecimal preChannelAmount;

    @ApiModelProperty("渠道毛利")
    @JsonProperty("channel_margin")
    private BigDecimal channelMargin;

    @ApiModelProperty("分销销售额")
    @JsonProperty("distribution_amount")
    private BigDecimal distributionAmount;

    @ApiModelProperty("分销毛利额")
    @JsonProperty("distribution_margin")
    private BigDecimal distributionMargin;

    @ApiModelProperty("当月预算额")
    @JsonProperty("supplier_content")
    private BigDecimal supplierContent;

    @ApiModelProperty("同比预算额")
    @JsonProperty("pre_supplier_content")
    private BigDecimal preSupplierContent;

    @ApiModelProperty("当月达成率")
    @JsonProperty("curr_achieve_rate")
    private BigDecimal currAchieveRate;

    @ApiModelProperty("同比达成率")
    @JsonProperty("pre_achieve_rate")
    private BigDecimal preAchieveRate;

}
