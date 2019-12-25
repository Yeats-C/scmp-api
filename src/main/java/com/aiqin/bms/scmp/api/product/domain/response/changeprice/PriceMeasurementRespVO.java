package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("变价测算返回vo")
public class PriceMeasurementRespVO {
    @ApiModelProperty("毛利率增加数")
    private BigDecimal increaseCount;
    @ApiModelProperty("毛利率减少数")
    private BigDecimal decreaseCount;
    @ApiModelProperty("毛利率增加额度")
    private BigDecimal increaseGrossProfit;
    @ApiModelProperty("毛利率减少额度")
    private BigDecimal decreaseGrossProfit;

    public String getIncreaseGrossProfit() {
        return "+"+increaseGrossProfit.divide(BigDecimal.valueOf(100),2, BigDecimal.ROUND_HALF_UP).toString();
    }
    public BigDecimal getIncreaseGrossProfit2(){
        return increaseGrossProfit;
    }
    public BigDecimal getDecreaseGrossProfit2(){
        return increaseGrossProfit;
    }
    public String getDecreaseGrossProfit() {
        return "-"+decreaseGrossProfit.divide(BigDecimal.valueOf(100),2, BigDecimal.ROUND_HALF_UP).toString();
    }
}
