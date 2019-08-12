package com.aiqin.bms.scmp.api.product.domain.request.returngoods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-04
 * @time: 10:15
 */
@Data
@ApiModel("退货收货请求vo")
public class ReturnReceiptReqVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("实际入库数")
    private Long actualInboundNum;

}
