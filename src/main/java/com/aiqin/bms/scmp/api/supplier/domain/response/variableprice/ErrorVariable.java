package com.aiqin.bms.scmp.api.supplier.domain.response.variableprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("批量导入错误信息")
@Data
public class ErrorVariable {
    @ApiModelProperty("sku编号")
    private String skuCode;
    @ApiModelProperty("sku名称")
    private String skuName;
    @ApiModelProperty("供应商")
    private String supplierName;
    @ApiModelProperty("错误原因")
    private String errorReason;

}
