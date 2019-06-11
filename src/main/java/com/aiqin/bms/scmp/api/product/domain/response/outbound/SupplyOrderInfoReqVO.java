package com.aiqin.bms.scmp.api.product.domain.response.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Classname: SupplyOrderInfoReqVO
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/3/19
 * @Version 1.0
 * @Since 1.0
 */
@Data
public class SupplyOrderInfoReqVO {

    @ApiModelProperty("订单编码(订单号)")
    @JsonProperty("")
    private String orderCode;

    private List<SupplyOrderProductItemReqVO> orderItems;
}
