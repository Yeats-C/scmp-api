package com.aiqin.bms.scmp.api.product.domain.request.profitloss;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("损溢wms回传商品表")
@Data
public class ProfitLossProductWmsReqVo {

    @ApiModelProperty("行号")
    @JsonProperty("line_num")
    private Long lineNum;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("数量(必须大于0)")
    @JsonProperty("quantity")
    private Long quantity;

    @ApiModelProperty("wms回传损溢单据类型（1 增加库存，2 减少库存）")
    @JsonProperty("type")
    private Integer type;

    @ApiModelProperty("损溢类型编号（1 增加库存，2 减少库存）")
    @JsonProperty("loss_order_code")
    private String lossOrderCode;

    @ApiModelProperty("损溢类型名称（1-报溢、2-报损）")
    @JsonProperty("loss_order_name")
    private String lossOrderName;

    @ApiModelProperty("损溢原因")
    @JsonProperty("reason")
    private String reason;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

}
