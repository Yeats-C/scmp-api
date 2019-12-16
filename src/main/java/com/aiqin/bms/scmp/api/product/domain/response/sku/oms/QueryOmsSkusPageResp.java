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
 * @date: 2019/3/6 0006 19:45
 */
@Data
@ApiModel("分页查询sku列表返回参数")
public class QueryOmsSkusPageResp {
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
    private BigDecimal skuPrice;

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

    @JsonProperty("promotion_type")
    @ApiModelProperty("活动类型：0-优惠、1-特价,9-不参加活动")
    private Integer promotionType;

    @Deprecated
    @ApiModelProperty("活动标签")
    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("special_price")
    @ApiModelProperty("特价价格")
    private BigDecimal specialPrice;

    @ApiModelProperty(value = "供应单位编码")
    @JsonProperty("supply_company_code")
    private String supplyCompanyCode;

    @ApiModelProperty(value = "供应单位名称")
    @JsonProperty("supply_company_name")
    private String supplyCompanyName;

    @ApiModelProperty(value = "商品单位重量")
    @JsonProperty("weight")
    private Integer weight;

    private Byte isMainPush;

    private Byte newProduct;
}
