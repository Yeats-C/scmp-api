package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname: InboundProductCallBackReqVo
 * 描述:入库单回调请求sku实体
 * @Author: Kt.w
 * @Date: 2019/3/6
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("入库单回调请求sku实体")
public class InboundProductCallBackReqVo {

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("预计入库数量")
    private Long preInboundNum;

    @ApiModelProperty("预计入库主数量")
    private Long preInboundMainNum;

    @ApiModelProperty("预计含税进价")
    private Long preTaxPurchaseAmount;

    @ApiModelProperty("预计含税总价")
    private Long preTaxAmount;

    @ApiModelProperty("实际入库数量")
    private Long praInboundNum;

    @ApiModelProperty("实际入库主数量")
    private Long praInboundMainNum;

    @ApiModelProperty("实际含税进价")
    private Long praTaxPurchaseAmount;

    @ApiModelProperty("实际含税总价")
    private Long praTaxAmount;

    @ApiModelProperty("行号")
    private Long linenum;
}
