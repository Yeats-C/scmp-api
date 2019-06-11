package com.aiqin.bms.scmp.api.product.domain.response.sku.ocenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/15 0015 11:18
 */
@Data
@ApiModel("运营中心查询根据sku编码集合查询sku列表返回")
public class CenterSkuListRespVO {
    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("颜色code")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("属性code")
    @JsonProperty("property_code")
    private String propertyCode;

    @ApiModelProperty("属性名称")
    @JsonProperty("property_name")
    private String propertyName;

    @ApiModelProperty("规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty("单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty("价格")
    @JsonProperty("price")
    private Long price;
}
