package com.aiqin.bms.scmp.api.purchase.domain.response.transport;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.transport.TransportOrders;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class TransportOrdersResp extends TransportOrders {
    @ApiModelProperty("客户编码")

    private String customerCode;
    @ApiModelProperty("客户名称")
    private String customerName;
}
