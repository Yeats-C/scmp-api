package com.aiqin.bms.scmp.api.product.domain.response.wms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "采购入库推送源数据明细")
@Data
public class PurchaseInboundDetailSource implements Serializable {

    @NotBlank(message = "Sku行号不能为空")
    @ApiModelProperty(value="Sku行号")
    @JsonProperty("line_code")
    private String lineCode;

    @NotBlank(message = "Sku编码不能为空")
    @ApiModelProperty(value="Sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @NotBlank(message = "Sku编码不能为空")
    @ApiModelProperty(value="Sku编码")
    @JsonProperty("sku_name")
    private String skuName;

    @NotNull(message = "数量不能为空")
    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("total_count")
    private Integer totalCount;

    @ApiModelProperty(value="库存单位编号")
    private String stockUnitCode;

    @ApiModelProperty(value="库存单位名称")
    private String stockUnitName;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value="入库单位编号")
    @JsonProperty("inbound_unit_code")
    private String inboundUnitCode;

    @ApiModelProperty(value="入库单位名称")
    @JsonProperty("inbound_unit_name")
    private String inboundUnitName;

    @ApiModelProperty(value="入库数量")
    @JsonProperty("inbound_num")
    private String inboundNum;
}
