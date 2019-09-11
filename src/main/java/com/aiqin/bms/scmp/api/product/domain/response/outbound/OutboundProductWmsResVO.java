package com.aiqin.bms.scmp.api.product.domain.response.outbound;

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
@ApiModel("WMS传入出库单sku实体")
public class OutboundProductWmsResVO {

    @ApiModelProperty("sku编号")
    public String skuCode;

    @ApiModelProperty("预计入库主数量")
    public Long preInboundMainNum;

    @ApiModelProperty("行号")
    @JsonProperty("linenum")
    public Long linenum;

}
