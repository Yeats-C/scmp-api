package com.aiqin.bms.scmp.api.product.domain.response.sku;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuSubRespVo
 * @date 2019/7/6 17:59
 * @description TODO
 */
@ApiModel("组合商品子SKU列表信息")
@Data
public class ProductSkuSubRespVo {

    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("组合SKU编码")
    private String mainSkuCode;

    @ApiModelProperty("组合SKU名称")
    private String mainSkuName;

    @ApiModelProperty("子SKU编码")
    private String subSkuCode;

    @ApiModelProperty("子SKU名称")
    private String subSkuName;

    @ApiModelProperty("子SKU数量")
    private Integer subSkuNum;

    @ApiModelProperty("是否组商品(0:不是 1:是)")
    private Byte mainProduct;
}
