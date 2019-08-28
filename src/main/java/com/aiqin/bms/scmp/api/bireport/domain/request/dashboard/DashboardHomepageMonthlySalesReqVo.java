package com.aiqin.bms.scmp.api.bireport.domain.request.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("首页跳转的月不累计reqVo")
@Data
public class DashboardHomepageMonthlySalesReqVo {

    @ApiModelProperty("年")
    @JsonProperty("stat_year")
    private String statYear;
}
