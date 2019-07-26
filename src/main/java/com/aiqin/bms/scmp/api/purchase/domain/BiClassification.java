package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class BiClassification {
    @ApiModelProperty(value="商品属性编码")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty(value="商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty(value="可用库存数")
    @JsonProperty("available_num")
    private Long availableNum;

    @ApiModelProperty(value="")
    @JsonProperty("classification")
    private BigDecimal classification;

    @ApiModelProperty(value="")
    @JsonProperty("run_time")
    private Date runTime;
}