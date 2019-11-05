package com.aiqin.bms.scmp.api.product.domain.response.inbound;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname: InboundProductWmsReqVO
 * 描述:WMS传入入库单sku实体
 * @Author: Kt.w
 * @Date: 2019/3/7
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("WMS传入入库单sku实体")
public class InboundProductWmsReqVO {

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("行号")
    private Long linenum;

    @ApiModelProperty("预计入库数量")
    private Long preInboundMainNum;

    @ApiModelProperty("0商品 1赠品 2实物返回")
    private int productType;

    @ApiModelProperty("采购单价")
    private Long productAmount;

    @ApiModelProperty("税率")
    private int taxRate;
}
