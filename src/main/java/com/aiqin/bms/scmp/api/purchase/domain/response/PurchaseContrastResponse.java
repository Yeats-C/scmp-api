package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.aiqin.bms.scmp.api.purchase.domain.BiAClassification;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

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
    @JsonProperty("front_category")
    private List<BiAClassification> frontCategory;

    @ApiModelProperty(value="采购后_A品占比")
    @JsonProperty("after_category")
    private List<BiAClassification> afterCategory;

}

