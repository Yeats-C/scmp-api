package com.aiqin.bms.scmp.api.product.domain.response.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("查询商品数据")
@Data
public class StockBatchProductSkuRespVO {

    @ApiModelProperty("sku编码")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("品类编码")
    @JsonProperty(value = "product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty(value = "product_category_name")
    private String productCategoryName;

    @ApiModelProperty("规格")
    @JsonProperty(value = "spec")
    private String spec;

    @ApiModelProperty("颜色编码")
    @JsonProperty(value = "color_code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    @JsonProperty(value = "color_name")
    private String colorName;

    @ApiModelProperty("型号")
    @JsonProperty(value = "model_number")
    private String modelNumber;

    @ApiModelProperty("单位编码")
    @JsonProperty(value = "unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty(value = "unit_name")
    private String unitName;

    @ApiModelProperty("基商品含量")
    @JsonProperty(value = "base_product_content")
    private String baseProductContent;

    @ApiModelProperty("包装")
    @JsonProperty(value = "pack")
    private String pack;

    @ApiModelProperty("配置状态(进货销售的)")
    @JsonProperty(value = "config_status")
    private Long configStatus;

}
