package com.aiqin.bms.scmp.api.product.domain.request.variableprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("Excel处理")
@Data
public class ExcelData {
    @ApiModelProperty("sku编码")
    private String skuCode;
    @ApiModelProperty("sku名称")
    private String skuName;
    @ApiModelProperty("含税单价")
    private BigDecimal priceValue;


}
