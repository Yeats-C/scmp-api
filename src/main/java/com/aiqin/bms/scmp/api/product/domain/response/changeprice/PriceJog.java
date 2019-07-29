package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("点动图")
@Data
public class PriceJog {
    @ApiModelProperty("sku编码")
    private String skuCode;
    @ApiModelProperty("月份")
    private String month;
    @ApiModelProperty("价格")
    private String price;
}
