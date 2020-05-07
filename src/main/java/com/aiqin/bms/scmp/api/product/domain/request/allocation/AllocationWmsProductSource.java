package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "调拨出入库推送源数据明细")
@Data
public class AllocationWmsProductSource implements Serializable {

    @NotBlank(message = "行号不能为空")
    @ApiModelProperty(value="行号")
    @JsonProperty("line_code")
    private String lineCode;

    @NotBlank(message = "SKU编号不能为空")
    @ApiModelProperty(value="SKU编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @NotNull(message = "最小单位数量不能为空")
    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("quantity")
    private Integer quantity;

    @ApiModelProperty(value="库存单位编号")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value="库存单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;
}
