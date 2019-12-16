package com.aiqin.bms.scmp.api.product.domain.response.sku.oms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/28 0028 15:29
 */
@Data
@ApiModel("商品下sku项数据返回")
public class OmsProductSkuItemResp {
    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("动销价")
    @JsonProperty("sku_price")
    private BigDecimal skuPrice;

    @ApiModelProperty("库存")
    @JsonProperty("sku_stock")
    private Long skuStock;

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

    @JsonProperty("brand_id")
    @ApiModelProperty("品牌ID")
    private String brandId;

    @JsonProperty("image_path")
    @ApiModelProperty("图片路径")
    private String imagePath;

    @JsonProperty("category_ids")
    @ApiModelProperty("品类ID集合（当前品类及其父类）")
    private List<String> categoryIds;



}
