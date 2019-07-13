package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-07-12
 **/
@Data
public class PurchaseFlowPathResponse{

    @ApiModelProperty(value="采购数量")
    @JsonProperty("purchase_count")
    private Integer purchaseCount;

    @ApiModelProperty(value="采购价格")
    @JsonProperty("purchase_amount")
    private Integer purchaseAmount;

    @ApiModelProperty(value="采购总价")
    @JsonProperty("purchase_amount_sum")
    private Integer purchaseAmountSum;

    @ApiModelProperty("物流中心(仓库)编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("物流中心(仓库)名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("到货周期/供应商发货-库房收货天数")
    @JsonProperty("arrival_cycle")
    private Integer arrivalCycle;

    @ApiModelProperty("订货周期(采购周期)")
    @JsonProperty("order_cycle")
    private Integer orderCycle;

    @ApiModelProperty("基本库存天数(安全库存)")
    @JsonProperty("basic_inventory_day")
    private Integer basicInventoryDay;

    @ApiModelProperty("大库存预警天数")
    @JsonProperty("large_inventory_warnDay")
    private Integer largeInventoryWarnDay;

    @ApiModelProperty("大效期预警天数")
    @JsonProperty("big_effect_period_warnDay")
    private Integer bigEffectPeriodWarnDay;

    @ApiModelProperty("到货后周转期")
    @JsonProperty("turnover_period_after_arrival")
    private Integer turnoverPeriodAfterArrival;

    @ApiModelProperty("下单-审核完成天数")
    @JsonProperty("day_one")
    private Integer dayOne;

    @ApiModelProperty("审核完成-财务付款天数")
    @JsonProperty("day_two")
    private Integer dayTwo;

    @ApiModelProperty("财务付款-供应商确认天数")
    @JsonProperty("day_three")
    private Integer dayThree;

    @ApiModelProperty("供应商确认-供应商发货天数")
    @JsonProperty("day_four")
    private Integer dayFour;

    @ApiModelProperty("可消耗库存")
    @JsonProperty("consume_day")
    private Integer consumeDay;

    @ApiModelProperty("采购周转期")
    @JsonProperty("purchase_turnover")
    private Integer purchaseTurnover;

    @ApiModelProperty("在途库存的查询")
    @JsonProperty("afloat_list")
    private List<PurchaseAfloatResponse> afloatList;
}
