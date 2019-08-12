package com.aiqin.bms.scmp.api.bireport.domain.request.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("当月部门销售同环比")
@Data
public class DashboardDepMonthlyHomocyclicRatioReqVo {

    @ApiModelProperty("月")
    @JsonProperty("stat_month")
    private String statMonth;

    @ApiModelProperty("部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("渠道编码")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    public DashboardDepMonthlyHomocyclicRatioReqVo() {
    }

    public DashboardDepMonthlyHomocyclicRatioReqVo(String statMonth, String productSortCode, String priceChannelCode) {
        this.statMonth = statMonth;
        this.productSortCode = productSortCode;
        this.priceChannelCode = priceChannelCode;
    }
}
