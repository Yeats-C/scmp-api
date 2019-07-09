package com.aiqin.bms.scmp.api.bireport.domain.response.editpurchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("编辑采购申请返回respVo")
@Data
public class PurchaseApplyRespVo {

    @ApiModelProperty("建议订货量")
    @JsonProperty(value = "advice_orders")
    private Long adviceOrders;

    @ApiModelProperty("预测到货时间")
    @JsonProperty(value = "predicted_arrival")
    private String predictedArrival;

    @ApiModelProperty("近三月平均日销量")
    @JsonProperty(value = "average_amount")
    private Long averageAmount;

    @ApiModelProperty("缺货影响的销售额")
    @JsonProperty(value = "out_stock_affect_money")
    private Long outStockAffectMoney;

    @ApiModelProperty("连续缺货天数")
    @JsonProperty(value = "out_stock_continuous_days")
    private Long outStockContinuousDays;

    @ApiModelProperty("到货周期")
    @JsonProperty(value = "arrival_cycle")
    private Long arrivalCycle;

    @ApiModelProperty("需要天数")
    @JsonProperty(value = "need_Days")
    private Long needDays;
}
