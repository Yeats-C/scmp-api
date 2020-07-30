package com.aiqin.bms.scmp.api.purchase.domain.pojo.transport;

import com.aiqin.bms.scmp.api.base.OrderStatus;
import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransportOrders extends CommonBean {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("运输单号")
//    @JsonProperty("transport_code")
    private String transportCode;

    @ApiModelProperty("订单号")
//    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("商品金额")
//    @JsonProperty("commodity_amount")
    private BigDecimal commodityAmount;

    @ApiModelProperty("订单金额")
//    @JsonProperty("order_amount")
    private BigDecimal orderAmount;

    @ApiModelProperty("订单状态")
//    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty("类型")
//    @JsonProperty("type")
    private Integer type;

    @ApiModelProperty("仓编码")
//    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓名称")
//    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
//    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
//    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("发货时间")
//    @JsonProperty("deliver_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliverTime;

    @ApiModelProperty("商品件数")
//    @JsonProperty("product_num")
    private Integer productNum;

    @ApiModelProperty("客户编码")
//    @JsonProperty("customer_code")
    private String customerCode;

    @ApiModelProperty("客户名称")
//    @JsonProperty("customer_name")
    private String customerName;

//    public void setStatus(Integer status) {
//        this.status = OrderStatus.getAllStatus().get(status).getBackgroundOrderStatus();
//    }
}
