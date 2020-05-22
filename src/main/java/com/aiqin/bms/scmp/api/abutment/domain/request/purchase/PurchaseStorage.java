package com.aiqin.bms.scmp.api.abutment.domain.request.purchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunx
 * @description scmp采购单
 * @date 2019-08-03
 */
@Data
@ApiModel("scmp熙耘采购或退供&销售订单&配送退货业务单据-->结算系统   库存信息")
public class PurchaseStorage {

    @JsonProperty("order_id")
    @ApiModelProperty("出入库单id")
    private String orderId;

    @JsonProperty("order_code")
    @ApiModelProperty("出入库单号")
    private String orderCode;

    @JsonProperty("in_out_flag")
    @ApiModelProperty("出入库标识")
    private Integer inOutFlag;

    @JsonProperty("source_order_id")
    @ApiModelProperty("来源单号id")
    private String sourceOrderId;

    @JsonProperty("source_order_code")
    @ApiModelProperty("来源单编码")
    private String sourceOrderCode;

    @JsonProperty("source_order_type")
    @ApiModelProperty("来源业务单类型0 采购 5 退供 10 配送订单 15 直送订单 20 辅采订单  25 售后退货 30 出入库")
    private String sourceOrderType;

    @JsonProperty("source_order_type_name")
    @ApiModelProperty("类型描述")
    private String sourceOrderTypeName;

    @JsonProperty("sub_order_type")
    @ApiModelProperty("出入库单据类型 0 采购入库 5 退供出库 10 配送订单出库 15 直送订单出库 " +
            "20 辅采订单出库  25 售后退货入库 30 移库 35 调拨入库 40 调拨出库 45 报损 50 报溢")
    private String subOrderType;

    @JsonProperty("sub_order_type_name")
    @ApiModelProperty("出入库单据类型描述，采购入库 退供出库 等")
    private String subOrderTypeName;
}
