package com.aiqin.bms.scmp.api.product.domain.response.sku;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className SkuStatusRespVo
 * @date 2019/7/6 19:42
 * @description TODO
 */
@ApiModel("sku状态返回")
@Data
public class SkuStatusRespVo {

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("SKU状态")
    private Byte skuStatus;

    @ApiModelProperty("销售状态")
    private Byte onSale;
}
