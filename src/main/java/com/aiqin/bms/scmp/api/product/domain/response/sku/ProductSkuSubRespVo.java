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

    @ApiModelProperty("子SKU数量")
    private Integer subSkuNum;

    @ApiModelProperty("是否主商品(0:不是 1:是)")
    private Byte mainProduct;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("所属商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("品类名称")
    private String productCategoryName;

    @ApiModelProperty("属性名称")
    private String productPropertyName;

    @ApiModelProperty("品牌名称")
    private String productBrandName;

    @ApiModelProperty("颜色")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("规格")
    private String spec;
}
