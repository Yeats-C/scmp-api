package com.aiqin.bms.scmp.api.product.domain.response.sku.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 *
 * @auther knight.xie
 * @date 2019/6/29 16:01
 */
@ApiModel("监管仓入库SKU查询返回")
@Data
public class SupervisoryWarehouseSkuRespVo {
    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("规格")
    private String productSpec;

    @ApiModelProperty("商品品类code")
    private String categoryId;

    @ApiModelProperty("商品品类名称")
    private String categoryName;

    @ApiModelProperty("商品品牌编码")
    private String brandId;

    @ApiModelProperty("商品品牌名称")
    private String brandName;

    @ApiModelProperty("单位编码")
    private String unitCode;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("税率")
    private Long taxRate;

    @ApiModelProperty("基商品含量")
    private Integer baseProductContent;

    @ApiModelProperty("商品类型")
    private Byte skuType;

    @ApiModelProperty("商品类型名称")
    private String skuTypeName;

    @ApiModelProperty("毛重")
    private Long boxGrossWeight;

    @ApiModelProperty("净重")
    private Long netWeight;

    public String getSkuTypeName() {
        if(Objects.equals(skuType,Byte.parseByte("0"))){
            this.skuTypeName = "商品";
        } else {
            this.skuTypeName = "赠品";
        }
        return skuTypeName;
    }
}
