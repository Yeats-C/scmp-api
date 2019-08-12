package com.aiqin.bms.scmp.api.product.domain.response.inbound;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@ApiModel("WMS传入入库单sku批次实体")
public class InboundBatchWmsReqVO {

    @ApiModelProperty("sku编号")
    @JsonProperty("goodsId")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("goodsName")
    private String skuName;

    @ApiModelProperty("预计数量")
    @JsonProperty("pre_qty")
    private Long preQty;

    @ApiModelProperty("实际数量")
    @JsonProperty("pra_qty")
    private Long praQty;

    @ApiModelProperty("行号")
    @JsonProperty("linenum")
    private Long linenum;

    @ApiModelProperty("批次号")
    @JsonProperty("inbound_batch_code")
    private String inboundBatchCode;

    @ApiModelProperty("条形码")
    @JsonProperty("goodsCode")
    private String goodsCode;

}
