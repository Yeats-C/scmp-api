package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("结算信息")
@Data
public class ProductSkuCheckoutDraft {

    @ApiModelProperty("结算方式编码")
    private String settlementMethodCode;

    @ApiModelProperty("结算方式名称")
    private String settlementMethodName;

    @ApiModelProperty("销项税率")
    private Long outputTaxRate;

    @ApiModelProperty("进项税率")
    private Long inputTaxRate;

    @ApiModelProperty("积分系数")
    private Long integralCoefficient;

    @ApiModelProperty(value = "返点", hidden = true)
    private Long rebate;

}