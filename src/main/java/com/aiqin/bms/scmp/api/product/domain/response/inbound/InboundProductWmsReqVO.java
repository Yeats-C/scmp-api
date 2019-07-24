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
@ApiModel("WMS传入入库单sku实体")
public class InboundProductWmsReqVO {

    @ApiModelProperty("sku编号")
    @JsonProperty("goodsId")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("goodsName")
    private String skuName;

    @ApiModelProperty(value = "商品条码 必填")
    private String goodsCode;

    @ApiModelProperty("预计入库主数量")
    @JsonProperty("quantity")
    private Long preInboundMainNum;

    @ApiModelProperty("入库单位名称")
    @JsonProperty("packageName")
    private String unitName;

    @ApiModelProperty("行号")
    @JsonProperty("linenum")
    private Long linenum;

    @ApiModelProperty("预计入库数量")
    @JsonProperty("preInboundNum")
    private Long preInboundNum;
}
