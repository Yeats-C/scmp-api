package com.aiqin.bms.scmp.api.purchase.domain.request.wms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel(value = "wms取消推送")
@Data
public class CancelSource {

    @ApiModelProperty(value="取消单号")
    //@JsonProperty("order_code")
    @NotEmpty(message = "取消单号不能为空")
    private String orderCode;

    @ApiModelProperty(value="库房编码")
    //@JsonProperty("warehouse_name")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    //@JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="(取消订单类型 JYCK:销售订单 XTRK:销售退货 CGRK:采购订单 CGTH:采购退货 TRAN:调拨计划)(巨沃)" +
            "取消类型-单据类型\n" +
            "1:销售-销售单\n" +
            "2:退货-入库单\n" +
            "3:采购-入库单\n" +
            "4:退供-退供单\n" +
            "5:调拨-出库单\n" +
            "6:移库-移库单(耘链传入)")
    //@JsonProperty("order_type")
    @NotEmpty(message = "取消类型不能为空")
    private String orderType;

    @ApiModelProperty(value = "备注")
    private String remark;
}
