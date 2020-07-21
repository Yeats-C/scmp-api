package com.aiqin.bms.scmp.api.abutment.domain.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("DL- 商品推送采购、分销、门店销售、库存信息信息")
public class ProductUnitRequest {

    @ApiModelProperty(value = "规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty(value="单位编码")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value="单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value = "单位的长")
    @JsonProperty("box_length")
    private Long boxLength;

    @ApiModelProperty(value = "单位的宽")
    @JsonProperty("box_width")
    private Long boxWidth;

    @ApiModelProperty(value = "单位的高")
    @JsonProperty("box_height")
    private Long boxHeight;

    @ApiModelProperty(value = "单位的体积")
    @JsonProperty("box_volume")
    private Long boxVolume;

    @ApiModelProperty(value = "单位的毛重")
    @JsonProperty("box_gross_weight")
    private BigDecimal boxGrossWeight;

    @ApiModelProperty(value = "单位的净重")
    @JsonProperty("net_weight")
    private BigDecimal netWeight;

    @ApiModelProperty(value = "条码")
    @JsonProperty("bar_code")
    private String barCode;

    @ApiModelProperty(value = "单位含量")
    @JsonProperty("base_product_content")
    private Integer baseProductContent;

    @ApiModelProperty(value = "交易倍数")
    @JsonProperty("zero_removal_coefficient")
    private Long zeroRemovalCoefficient;

}
