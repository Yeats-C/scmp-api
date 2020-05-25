package com.aiqin.bms.scmp.api.product.domain.response.wms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "退供入库推送源数据明细")
@Data
public class PurchaseOutboundDetailSource {

    @NotBlank(message = "行号不能为空")
    @ApiModelProperty(value="行号")
    //@JsonProperty("line_code")
    private String lineCode;

    @NotBlank(message = "SKU编号不能为空")
    @ApiModelProperty(value="SKU编号")
    //@JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    //@JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="最小单位数量")
    //@JsonProperty("total_count")
    private Integer totalCount;

    @ApiModelProperty(value="库存单位编号")
    //@JsonProperty("stock_unit_code")
    private String stockUnitCode;

    @ApiModelProperty(value="库存单位名称")
    //@JsonProperty("stock_unit_name")
    private String stockUnitName;

    @ApiModelProperty(value="颜色")
    //@JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    //@JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value="SKU条码")
    //@JsonProperty("sku_bar_code")
    private String skuBarCode;
}
