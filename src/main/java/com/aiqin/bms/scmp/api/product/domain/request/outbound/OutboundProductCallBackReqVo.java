package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Classname: OutboundProductCallBackReqVo
 * 描述: 出库单skuWMS回调申请实体
 * @Author: Kt.w
 * @Date: 2019/3/6
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("出库单skuWMS回调申请实体")
public class OutboundProductCallBackReqVo {

//    @ApiModelProperty("sku编号")
//    private String skuCode;
//
//    @ApiModelProperty("实际入库主数量")
//    private Long praOutboundMainNum;
//
//    @ApiModelProperty("实际含税进价")
//    private Long praTaxPurchaseAmount;
//
//    @ApiModelProperty("行号")
//    private Long linenum;

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("预计出库数量")
    private Long preOutboundNum;

    @ApiModelProperty("预计出库主数量")
    private Long preOutboundMainNum;

    @ApiModelProperty("预计含税进价")
    private BigDecimal preTaxPurchaseAmount;

    @ApiModelProperty("预计含税总价")
    private BigDecimal preTaxAmount;

    @ApiModelProperty("实际出库数量")
    private Long praOutboundNum;

    @ApiModelProperty("实际出库主数量")
    private Long praOutboundMainNum;

    @ApiModelProperty("实际含税进价")
    @NotNull(message = "实际含税进价")
    private BigDecimal praTaxPurchaseAmount;

    @ApiModelProperty("实际含税总价")
    @NotNull(message = "实际含税总价")
    private BigDecimal praTaxAmount;

    @ApiModelProperty("行号")
    private Long linenum;
}
