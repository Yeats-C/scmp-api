package com.aiqin.bms.scmp.api.bireport.domain.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("渠道部门月销售情况respVo")
@Data
public class ChannelSectorMonthSalesRespVo {

    @ApiModelProperty("查询部门销售额贡献率")
    @JsonProperty("sales_contribution_rate")
    private Long salesContributionRate;

    @ApiModelProperty("查询负毛利SKU数")
    @JsonProperty("negative_margin_sku_number")
    private Long negativeMarginSkuNumber;

    @ApiModelProperty("查询缺货率")
    @JsonProperty("out_stock_rate")
    private Long outStockRate;

    @ApiModelProperty("渠道退供金额(元)")
    @JsonProperty("amount_channel_refund")
    private Long amountChannelRefund;

}
