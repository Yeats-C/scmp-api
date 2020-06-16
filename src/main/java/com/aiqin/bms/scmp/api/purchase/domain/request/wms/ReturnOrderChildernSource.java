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
public class ReturnOrderChildernSource {
    @ApiModelProperty(value="订单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="SKU编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="数量")
    @JsonProperty("num")
    private String num;

    @ApiModelProperty(value="行号")
    @JsonProperty("line_num")
    private String lineNum;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty(value="尺码")
    @JsonProperty("dd10")
    private String dd10;

    @ApiModelProperty(value="分销单位")
    @JsonProperty("dd11")
    private String dd11;

}
