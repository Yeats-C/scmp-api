package com.aiqin.bms.scmp.api.product.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author knight.xie
 * @className QueryStockSkuRespVo
 * @date 2019/1/4 17:00
 * @description 查询库存商品sku返回VO
 * @version 1.0
 */
@ApiModel("查询库存商品sku返回VO")
@Data
public class QueryStockSkuRespVo implements Serializable {

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品code")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("sku品类编码")
    @JsonProperty("productCategoryCode")
    private String skuCategoryCode;

    @ApiModelProperty("sku品类名称")
    @JsonProperty("productCategoryName")
    private String skuCategoryName;

    @ApiModelProperty("sku品牌编码")
    @JsonProperty("productBrandCode")
    private String skuBrandCode;

    @ApiModelProperty("sku品牌名称")
    @JsonProperty("productBrandName")
    private String skuBrandName;

    @ApiModelProperty("sku规格编码")
    @JsonProperty("specCode")
    private String skuSpecCode;

    @ApiModelProperty("sku规格名称")
    @JsonProperty("spec")
    private String skuSpecName;

    @ApiModelProperty("sku颜色编码")
    @JsonProperty("colorCode")
    private String skuColorCode;

    @ApiModelProperty("sku颜色名称")
    @JsonProperty("colorName")
    private String skuColorName;

    @ApiModelProperty("sku型号编码")
    private String skuModelNumberCode;

    @ApiModelProperty("sku型号名称")
    @JsonProperty("modelNumber")
    private String skuModelNumberName;

    @ApiModelProperty("采购单位编码")
    @JsonProperty("unitCode")
    private String skuUnitCode;

    @ApiModelProperty("采购单位名称")
    @JsonProperty("unitName")
    private String skuUnitName;

    @ApiModelProperty("sku类别编码")
    @JsonProperty("productSortCode")
    private String skuSortCode;

    @ApiModelProperty("sku类别名称")
    @JsonProperty("productSortName")
    private String skuSortName;

    @ApiModelProperty("sku类型编码")
    @JsonProperty("productTypeCode")
    private Byte skuTypeCode;

    @ApiModelProperty("库存单位编码")
    @JsonProperty("saleUnitCode")
    private String stockUnitCode;

    @ApiModelProperty("库存单位名称")
    @JsonProperty("saleUnitName")
    private String stockUnitName;

    @ApiModelProperty("库存")
    @JsonProperty("stockNum")
    private Long stock;

    @ApiModelProperty("税率")
    @JsonProperty("taxRate")
    private Long taxRate;

    @ApiModelProperty("含税单价")
    @JsonProperty("price")
    private BigDecimal price;

    @ApiModelProperty("拆零系数")
    @JsonProperty("convertNum")
    private Long zeroRemovalCoefficient;

}
