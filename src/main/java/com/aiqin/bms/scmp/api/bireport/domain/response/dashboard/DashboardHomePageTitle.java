package com.aiqin.bms.scmp.api.bireport.domain.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("首页头字段respVo")
@Data
public class DashboardHomePageTitle {

    @ApiModelProperty("年销售额(万元)")
    @JsonProperty("channel_amount")
    private BigDecimal channelAmount;

    @ApiModelProperty("总目标达成率")
    @JsonProperty("achieve_rate")
    private BigDecimal achieveRate;

    @ApiModelProperty("年毛利额（万元）")
    @JsonProperty("channel_margin")
    private BigDecimal channelMargin;

    @ApiModelProperty("年毛利率")
    @JsonProperty("margin_rate")
    private BigDecimal marginRate;

    @ApiModelProperty("缺货/残次品/大效期影响金额")
    @JsonProperty("monthly_loss_amount")
    private BigDecimal monthlyLossAmount;

    @ApiModelProperty("A 品年销售额（万元）")
    @JsonProperty("channel_sales_amount")
    private BigDecimal channelSalesAmount;

    @ApiModelProperty("A 品贡献率")
    @JsonProperty("contribution_rate")
    private BigDecimal contributionRate;
}
