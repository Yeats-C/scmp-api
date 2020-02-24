package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseApply;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2020-02-24 20:05
 **/
@Data
public class PurchaseInboundRequest {

    @ApiModelProperty(value="采购单id")
    @JsonProperty("purchase_order_id")
    private String purchaseOrderId;

    @ApiModelProperty(value="入库单编码")
    @JsonProperty("inbound_order_code")
    private String inboundOrderCode;

    @ApiModelProperty("采购调用次数")
    @JsonProperty("purchase_num")
    private Integer purchaseNum;
}
