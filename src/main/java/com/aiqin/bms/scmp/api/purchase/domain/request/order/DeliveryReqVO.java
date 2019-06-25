package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-24
 * @time: 17:47
 */
@Data
@ApiModel("发货请求vo")
public class DeliveryReqVO {
    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("实发数量")
    private Long actualDeliverNum;
}
