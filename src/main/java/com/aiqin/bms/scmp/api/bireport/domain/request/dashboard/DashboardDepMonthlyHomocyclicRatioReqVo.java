package com.aiqin.bms.scmp.api.bireport.domain.request.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("当月部门销售同环比")
@Data
public class DashboardDepMonthlyHomocyclicRatioReqVo {

    @ApiModelProperty("日期begin")
    @JsonProperty("begin_stat_date")
    private String beginStatDate;

    @ApiModelProperty("日期finish")
    @JsonProperty("finish_stat_date")
    private String finishStatDate;

    @ApiModelProperty("部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("渠道编码")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    public DashboardDepMonthlyHomocyclicRatioReqVo() {
    }

    public DashboardDepMonthlyHomocyclicRatioReqVo(String productSortCode, String priceChannelCode) {
        this.productSortCode = productSortCode;
        this.priceChannelCode = priceChannelCode;
    }
}
