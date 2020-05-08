package com.aiqin.bms.scmp.api.purchase.domain.request.returngoods;

import com.aiqin.bms.scmp.api.purchase.domain.request.order.BatchWmsInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @Author:ch
 * @Date:2020/4/24
 * @Content:
 */
@Data
@ApiModel("退货入库单推送主表")
public class ReturnOrderPrimarySource implements Serializable
{
    @ApiModelProperty(value="入库单号")
    @JsonProperty("inbound_order_code")
    private String inboundOrderCode;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="退货单创建时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty(value="创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="物流公司编码")
    @JsonProperty("logistics_center_code")
    private String logisticsCenterCode;

    @ApiModelProperty(value="物流公司名称")
    @JsonProperty("logistics_center_name")
    private String logisticsCenterName;

    @ApiModelProperty(value="单号")
    @JsonProperty("transport_number")
    private String transportNumber;

    @ApiModelProperty(value="发货人")
    @JsonProperty("shipper")
    private String shipper;

    @ApiModelProperty(value="发货人手机号")
    @JsonProperty("shipper_number")
    private String shipperNumber;

    @ApiModelProperty(value="备注")
    @JsonProperty("remake")
    private String remake;

    @ApiModelProperty(value="退货单号")
    @JsonProperty("return_order_code")
    private String returnOrderCode;

    @ApiModelProperty(value="销售订单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="客户编码")
    @JsonProperty("customer_code")
    private double customerCode;

    @ApiModelProperty(value="客户名称")
    @JsonProperty("customer_name")
    private double customerName;

    @Valid
    @ApiModelProperty(value="退货入库单子表")
    @JsonProperty("return_order_childern_source")
    private List<ReturnOrderChildernSource> childrenSourceList;

    @ApiModelProperty(value="批次信息明细")
    @JsonProperty("batch_info")
    private List<BatchWmsInfo> batchInfo;

}
