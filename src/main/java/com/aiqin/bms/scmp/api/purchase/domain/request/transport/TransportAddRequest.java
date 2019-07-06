package com.aiqin.bms.scmp.api.purchase.domain.request.transport;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportOrders;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TransportAddRequest {
    @ApiModelProperty("仓编码")
    //@JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓名称")
    //@JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    //@JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    //@JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("客户编码")
    //@JsonProperty("customer_code")
    private String customerCode;

    @ApiModelProperty("客户名称")
    //@JsonProperty("customer_name")
    private String customerName;


    @ApiModelProperty("物流公司编码")
    //@JsonProperty("logistics_company")
    private String logisticsCompany;

    @ApiModelProperty("物流公司名称")
    //@JsonProperty("logistics_company_name")
    private String logisticsCompanyName;

    @ApiModelProperty("物流单号")
    //@JsonProperty("logistics_number")
    private String logisticsNumber;

    @ApiModelProperty("发货至")
    //@JsonProperty("deliver_to")
    private String deliverTo;

    @ApiModelProperty("标准物流费")
    //@JsonProperty("standard_logistics_fee")
    private Long standardLogisticsFee;

    @ApiModelProperty("选加物流费")
    //@JsonProperty("additional_logistics_fee")
    private Long additionalLogisticsFee;

    @ApiModelProperty("总体积")
    //@JsonProperty("total_volume")
    private BigDecimal totalVolume;

    @ApiModelProperty("包装数")
    //@JsonProperty("packing_num")
    private Long packingNum;

    @ApiModelProperty("总重量")
    //@JsonProperty("total_weight")
    private BigDecimal totalWeight;

    @ApiModelProperty("备注")
    //@JsonProperty("remark")
    private String remark;

    @ApiModelProperty("订单列表")
    //@JsonProperty("orders_list")
    List<TransportOrders> ordersList;
}
