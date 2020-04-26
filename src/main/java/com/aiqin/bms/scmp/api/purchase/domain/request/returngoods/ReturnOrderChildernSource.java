package com.aiqin.bms.scmp.api.purchase.domain.request.returngoods;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author:ch
 * @Date:2020/4/24
 * @Content:
 */
@ApiModel("退货单推送字表")
@Data
public class ReturnOrderChildernSource implements Serializable
{
    @ApiModelProperty(value="SKU编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU编号")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="数量（最小单位数量）")
    @JsonProperty("total_count")
    private String totalCount;

    @ApiModelProperty(value="库存单位编号")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value="库存单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value="行号")
    @JsonProperty("line_code")
    private String lineCode;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty(value="型号")
    @JsonProperty("model")
    private String model;


}
