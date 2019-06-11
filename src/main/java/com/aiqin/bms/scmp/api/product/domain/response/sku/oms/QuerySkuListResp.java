package com.aiqin.bms.scmp.api.product.domain.response.sku.oms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/3/6 0006 18:44
 */
@Data
@ApiModel("通过sku集合查询返回sku信息")
public class QuerySkuListResp {
    @JsonProperty("sku_code")
    @ApiModelProperty("sku编码")
    private String skuCode;

    @JsonProperty("sku_name")
    @ApiModelProperty("sku名称")
    private String skuName;

    @JsonProperty("product_code")
    @ApiModelProperty("商品编码")
    private String productCode;

    @JsonProperty("sku_price")
    @ApiModelProperty("动销价")
    private Long skuPrice;

    @JsonProperty("sku_stock")
    @ApiModelProperty("库存")
    private Long skuStock;

    @ApiModelProperty("规格")
    private String spec;

    @JsonProperty("color_code")
    @ApiModelProperty("颜色code")
    private String colorCode;

    @JsonProperty("color_name")
    @ApiModelProperty("颜色名称")
    private String colorName;

    @JsonProperty("model_number")
    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("属性code")
    @JsonProperty("property_code")
    private String propertyCode;

    @ApiModelProperty("属性名称")
    @JsonProperty("property_name")
    private String propertyName;

    @ApiModelProperty("单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @JsonProperty("image_path")
    @ApiModelProperty("图片路径")
    private String imagePath;

    @JsonProperty("brand_id")
    @ApiModelProperty("品牌ID")
    private String brandId;

    @JsonProperty("brand_name")
    @ApiModelProperty("品牌名称")
    private String brandName;

    private String categoryId;

    private String categoryName;

    @JsonProperty("category_ids")
    @ApiModelProperty("品类ID集合（当前品类及其父类）")
    private List<String> categoryIds;

    @ApiModelProperty(value = "供应单位编码")
    @JsonProperty("supply_company_code")
    private String supplyCompanyCode;

    @ApiModelProperty(value = "供应单位名称")
    @JsonProperty("supply_company_name")
    private String supplyCompanyName;

    @ApiModelProperty(value = "商品单位重量")
    @JsonProperty("weight")
    private Integer weight;

}
