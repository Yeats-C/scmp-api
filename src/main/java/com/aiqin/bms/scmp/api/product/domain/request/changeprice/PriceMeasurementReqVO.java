package com.aiqin.bms.scmp.api.product.domain.request.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("变价测算请求vo")
public class PriceMeasurementReqVO {
    @ApiModelProperty("sku编码")
    private String skuCode;
    @ApiModelProperty("原毛利率")
    private BigDecimal oldGrossProfitMargin;
    @ApiModelProperty("现毛利率")
    private BigDecimal newGrossProfitMargin;
    @ApiModelProperty("价格")
    private BigDecimal price;
}
