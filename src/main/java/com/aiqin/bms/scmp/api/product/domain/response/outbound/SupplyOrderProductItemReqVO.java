package com.aiqin.bms.scmp.api.product.domain.response.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname: SupplyOrderProductItemReqVO
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/3/19
 * @Version 1.0
 * @Since 1.0
 */
@Data
public class SupplyOrderProductItemReqVO {
    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("商品行号")
    @JsonProperty("productLineNum")
    private Long linenum;

    @ApiModelProperty("实发数量")
    @JsonProperty("actualDeliverNum")
    private Long praOutboundMainNum;

}
