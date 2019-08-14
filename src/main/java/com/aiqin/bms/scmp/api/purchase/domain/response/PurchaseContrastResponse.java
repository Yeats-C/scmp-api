package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.aiqin.bms.scmp.api.purchase.domain.BiAClassification;
import com.aiqin.bms.scmp.api.purchase.domain.BiClassification;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhao shuai
 * @create: 2019-07-27
 **/
@Data
public class PurchaseContrastResponse {

    @ApiModelProperty(value="采购前_总营业额")
    @JsonProperty("front_sum_turnover")
    private BigDecimal frontSumTurnover;

    @ApiModelProperty(value="采购前_总利润")
    @JsonProperty("front_sum_profit")
    private BigDecimal frontSumProfit;

    @ApiModelProperty(value="采购后_总营业额")
    @JsonProperty("after_sum_turnover")
    private BigDecimal afterSumTurnover;

    @ApiModelProperty(value="采购后_总利润")
    @JsonProperty("after_sum_profit")
    private BigDecimal afterSumProfit;

    @ApiModelProperty(value="采购前_缺货占比")
    @JsonProperty("front_shortage_ratio")
    private BigDecimal frontShortageRatio;

    @ApiModelProperty(value="采购后_缺货占比")
    @JsonProperty("after_shortage_ratio")
    private BigDecimal afterShortageRatio;

    @ApiModelProperty(value="采购前_A品占比")
    @JsonProperty("front_a_category")
    private BiAClassification frontACategory;

    @ApiModelProperty(value="采购后_A品占比")
    @JsonProperty("after_a_category")
    private BiAClassification afterACategory;

    @ApiModelProperty(value="采购前_分类占比")
    @JsonProperty("front_category")
    private BiClassification frontCategory;

    @ApiModelProperty(value="采购后_分类占比")
    @JsonProperty("after_category")
    private BiClassification afterCategory;

    private String beginTime;

    private String finishTime;

    private BigDecimal bigMum;

}
