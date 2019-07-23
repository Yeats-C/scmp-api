package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author HuangLong
 * @date 2018/12/27
 */
@Data
@ApiModel("商品入库数据对象")
public class StorageResultItemReqVo {

    @ApiModelProperty("商品sku编码")
    private String skuCode;

    @ApiModelProperty("商品入库数量")
     @JsonProperty("num")
    private Long praInboundNum;

    @ApiModelProperty("商品入库主数量")
    @JsonProperty("mainNum")
    private Long praInboundMainNum;

    @ApiModelProperty("实际价格")
    @JsonProperty("price")
    private Long praTaxPurchaseAmount;

    @ApiModelProperty("行号")
    @JsonProperty("lineNum")
    private Long lineNum;
}
