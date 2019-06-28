package com.aiqin.bms.scmp.api.purchase.domain.request.transport;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransportOrdersResquest extends PageReq {

    @ApiModelProperty("仓库编码")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty(value = "transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty(value = "warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty(value = "warehouse_name")
    private String warehouseName;

    @ApiModelProperty("客户编码")
    @JsonProperty(value = "customer_code")
    private String customerCode;

    @ApiModelProperty("客户名称")
    @JsonProperty(value = "customer_name")
    private String customerName;
}
