package com.aiqin.bms.scmp.api.bireport.domain.request.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("月销售情况（不累计）reqVo")
@Data
public class DashboardMonthlySalesStatiReqVo {

    @ApiModelProperty("年")
    @JsonProperty("stat_year")
    private String statYear;
}
