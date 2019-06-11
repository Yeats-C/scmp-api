package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuManufacturerRespVo
 * @date 2019/5/14 16:04
 * @description TODO
 */
@Data
@ApiModel("sku生产厂家信息返回")
public class ProductSkuManufacturerRespVo extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("生产厂家")
    private String manufacturerName;

    @ApiModelProperty("厂方商品编号")
    private String factoryProductNumber;

    @ApiModelProperty("是否缺省（0:否,1：是）")
    private Byte isDefault;

    @ApiModelProperty("保修地址")
    private String address;

    @ApiModelProperty("商品sku code")
    private String productSkuCode;

    @ApiModelProperty("商品sku 名称")
    private String productSkuName;
}
