package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Classname: OutboundProductCallBackReqVo
 * 描述: 出库单skuWMS回调申请实体
 * @Author: Kt.w
 * @Date: 2019/3/6
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("出库单批次skuWMS回调申请实体")
public class OutboundBatchCallBackReqVo {

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("预计出库数量")
    private Long preQty;

    @ApiModelProperty("实际出库数量")
    private Long praQty;

    @ApiModelProperty("行号")
    private Long linenum;

    @ApiModelProperty("批次号")
    private String outboundBatchCode;

}
