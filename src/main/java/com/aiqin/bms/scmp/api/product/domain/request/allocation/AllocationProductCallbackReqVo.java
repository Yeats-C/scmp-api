package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by 爱亲 on 2019/8/12.
 */
@Data
@ApiModel("移库回传商品vo")
public class AllocationProductCallbackReqVo {

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("数量")
    private Long quantity;
}