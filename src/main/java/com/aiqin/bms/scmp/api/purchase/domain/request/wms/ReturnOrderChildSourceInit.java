package com.aiqin.bms.scmp.api.purchase.domain.request.wms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author:zfy
 * @Date:2020/3/6
 * @Content:
 */
@ApiModel("退货单推送字表")
@Data
public class ReturnOrderChildSourceInit {
    @ApiModelProperty(value="行号")
    @JsonProperty("line_code")
    private String lineCode;

    @ApiModelProperty(value="SKU编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="最小单位数量")
    @JsonProperty("total_count")
    private String totalCount;

    @ApiModelProperty(value="库存单位编号")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value="库存单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty(value="型号")
    @JsonProperty("model")
    private String model;

    /*******新增数据********/
    @ApiModelProperty(value="SKU条码")
    @JsonProperty("sku_bar_code")
    private String skuBarCode;

    @ApiModelProperty(value="分销单位")
    @JsonProperty("packge_unit")
    private String packgeUnit;
    /*******新增数据********/

}
