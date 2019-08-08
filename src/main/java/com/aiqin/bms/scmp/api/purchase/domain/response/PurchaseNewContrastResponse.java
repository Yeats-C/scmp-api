package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-08-08
 **/
@Data
public class PurchaseNewContrastResponse {

    @ApiModelProperty(value="采购前_预计营业额")
    @JsonProperty("front_turnover")
    private Long frontTurnover;

    @ApiModelProperty(value="采购前_采购成本")
    @JsonProperty("front_purchase_cost")
    private Long frontPurchaseCost;

    @ApiModelProperty(value="采购前_毛利额")
    @JsonProperty("front_gross_profit")
    private Long frontGrossProfit;

    @ApiModelProperty(value="采购前_毛利率")
    @JsonProperty("front_gross_rate")
    private Long frontGrossRate;

    @ApiModelProperty(value="采购后_预计营业额")
    @JsonProperty("after_turnover")
    private Long afterTurnover;

    @ApiModelProperty(value="采购后_采购成本")
    @JsonProperty("after_purchase_cost")
    private Long afterPurchaseCost;

    @ApiModelProperty(value="采购后_毛利额")
    @JsonProperty("after_gross_profit")
    private Long afterGrossProfit;

    @ApiModelProperty(value="采购后_毛利率")
    @JsonProperty("after_gross_rate")
    private Long afterGrossRate;

    @ApiModelProperty(value="采购前_滞销sku数")
    @JsonProperty("front_unsalable_sku")
    private Long frontUnsalableSku;

    @ApiModelProperty(value="采购前_滞销率")
    @JsonProperty("front_unsalable_rate")
    private Long frontUnsalableRate;

    @ApiModelProperty(value="采购后_滞销sku数")
    @JsonProperty("after_unsalable_sku")
    private Long afterUnsalableSku;

    @ApiModelProperty(value="采购前_滞销率")
    @JsonProperty("after_unsalable_rate")
    private Long afterUnsalableRate;

    @ApiModelProperty(value="采购前_缺货率")
    @JsonProperty("front_shortage_rate")
    private Long frontShortageRate;

    @ApiModelProperty(value="采购后_缺货率")
    @JsonProperty("after_shortage_rate")
    private Long afterShortageRate;
}
