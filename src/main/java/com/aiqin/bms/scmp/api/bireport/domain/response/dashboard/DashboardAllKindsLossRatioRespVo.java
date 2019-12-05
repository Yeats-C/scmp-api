package com.aiqin.bms.scmp.api.bireport.domain.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("今年各亏损占比respVo")
@Data
public class DashboardAllKindsLossRatioRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty("残次品影响金额")
    @JsonProperty("defective_effect_amount")
    private BigDecimal defectiveEffectAmount;

    @ApiModelProperty("缺货影响金额")
    @JsonProperty("stockout_sales_amount")
    private BigDecimal stockoutSalesAmount;

    @ApiModelProperty("残次品金额占比")
    @JsonProperty("defective_effect_ratio")
    private BigDecimal defectiveEffectRatio;

    @ApiModelProperty("缺货金额占比")
    @JsonProperty("stockout_ratio")
    private BigDecimal stockoutRatio;
}
