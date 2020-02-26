package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryItemReqVo {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("实发数量")
    private Long actualDeliverNum;
}
