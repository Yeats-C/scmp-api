package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("变价测算返回vo")
public class PriceMeasurementRespVO {
    @ApiModelProperty("毛利率增加数")
    private Long increaseCount;
    @ApiModelProperty("毛利率减少数")
    private Long decreaseCount;
    @ApiModelProperty("毛利率增加额度")
    private Long increaseGrossProfit;
    @ApiModelProperty("毛利率减少额度")
    private Long decreaseGrossProfit;
}
