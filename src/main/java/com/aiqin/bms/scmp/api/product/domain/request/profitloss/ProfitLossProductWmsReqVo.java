package com.aiqin.bms.scmp.api.product.domain.request.profitloss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("损溢wms回传商品表")
@Data
public class ProfitLossProductWmsReqVo {

    @ApiModelProperty("行号")
    private Long lineNum;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("数量(必须大于0)")
    private Long quantity;

    @ApiModelProperty("损溢类型编号（1 增加库存，2 减少库存）")
    private String lossOrderCode;

    @ApiModelProperty("损溢类型名称（1-报溢、2-报损）")
    private String lossOrderName;

    @ApiModelProperty("损溢原因")
    private String reason;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

}
