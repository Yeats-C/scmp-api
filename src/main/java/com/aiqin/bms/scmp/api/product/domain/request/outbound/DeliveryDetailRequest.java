package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhao shuai
 * @create: 2019-12-31
 **/
@Data
public class DeliveryDetailRequest {

    @ApiModelProperty("销售单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("发运单运费")
    @JsonProperty("transport_amount")
    private BigDecimal transportAmount;
}
