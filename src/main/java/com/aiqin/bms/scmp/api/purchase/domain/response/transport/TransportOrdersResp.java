package com.aiqin.bms.scmp.api.purchase.domain.response.transport;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.TransportOrders;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class TransportOrdersResp extends TransportOrders {
    @ApiModelProperty("客户编码")
//    @JsonProperty("customer_code")
    private String customerCode;
    @ApiModelProperty("客户名称")
//    @JsonProperty("customer_name")
    private String customerName;
}
