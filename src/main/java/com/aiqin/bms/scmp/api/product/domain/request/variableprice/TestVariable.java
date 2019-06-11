package com.aiqin.bms.scmp.api.product.domain.request.variableprice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestVariable {
    @ApiModelProperty("sku编号")
    private String skuCode;
    @ApiModelProperty("sku名称")
    private String skuName;
    @ApiModelProperty("供应商")
    private String supplierName;
}
