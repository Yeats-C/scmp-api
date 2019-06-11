package com.aiqin.bms.scmp.api.product.domain.response.sku.oms;

import com.aiqin.bms.scmp.api.product.domain.ProductCategory;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuLabelInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/7 0007 15:47
 */
@Data
@ApiModel("oms商品sku信息返回")
public class ProductSkuListInfoRespVO {
    @ApiModelProperty("商品编码")
    @JsonProperty("product_code")
    private String productCode;

    @ApiModelProperty("商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("主图")
    @JsonProperty("product_image")
    private String productImage;

    @ApiModelProperty("动销价")
    @JsonProperty("sku_price")
    private Long skuPrice;

    @ApiModelProperty("标签集合")
    @JsonProperty("product_label_list")
    private List<ProductSkuLabelInfo> productLabelList;

    @ApiModelProperty("品类集合")
    @JsonProperty("product_category_list")
    private List<ProductCategory> productCategories;

    @ApiModelProperty("品牌id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty("品牌名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty("规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty("颜色code")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty("属性code")
    @JsonProperty("property_code")
    private String propertyCode;

    @ApiModelProperty("属性名称")
    @JsonProperty("property_name")
    private String propertyName;
}
