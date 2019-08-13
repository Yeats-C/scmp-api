package com.aiqin.bms.scmp.api.product.domain.request.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("变价测算请求vo")
public class PriceMeasurementReqVO {
    @ApiModelProperty("sku编码")
    private String skuCode;
    @ApiModelProperty("毛利率")
    private Long grossProfitMargin;
    @ApiModelProperty("价格")
    private Long price;
}
