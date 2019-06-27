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
@ApiModel("入库单回调请求sku批次实体")
public class InboundBatchCallBackReqVo {

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("预计数量")
    private Long preQty;

    @ApiModelProperty("实际数量")
    private Long praQty;

    @ApiModelProperty("行号")
    private Long linenum;
}
