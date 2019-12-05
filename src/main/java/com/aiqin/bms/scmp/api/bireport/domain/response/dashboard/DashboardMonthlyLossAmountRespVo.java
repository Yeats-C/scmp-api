package com.aiqin.bms.scmp.api.bireport.domain.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("月亏损")
@Data
public class DashboardMonthlyLossAmountRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("年")
    @JsonProperty("stat_year")
    private String statYear;

    @ApiModelProperty("月")
    @JsonProperty("stat_month")
    private String statMonth;

    @ApiModelProperty("次品影响金额")
    @JsonProperty("defective_effect_amount_monthly")
    private BigDecimal defectiveEffectAmountMonthly;

    @ApiModelProperty("次品影响金额累计值")
    @JsonProperty("defective_effect_amount_acc")
    private BigDecimal defectiveEffectAmountAcc;

    @ApiModelProperty("缺货影响金额")
    @JsonProperty("stockout_sales_amount_monthly")
    private BigDecimal stockoutSalesAmountMonthly;

    @ApiModelProperty("缺货影响金额累计值")
    @JsonProperty("stockout_sales_amount_monthly_acc")
    private BigDecimal stockoutSalesAmountMonthlyAcc;

    @ApiModelProperty("月亏损总额")
    @JsonProperty("monthly_loss_amount")
    private BigDecimal monthlyLossAmount;
}
