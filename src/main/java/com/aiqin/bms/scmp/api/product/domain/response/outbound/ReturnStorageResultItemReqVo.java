package com.aiqin.bms.scmp.api.product.domain.response.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author HuangLong
 * @date 2018/12/27
 */
@Data
@ApiModel("退供出库数据对象")
public class ReturnStorageResultItemReqVo {

    @ApiModelProperty("商品sku编码")
    private String skuCode;

    @ApiModelProperty("商品入库数量")
    @JsonProperty("num")
    private Long praOutboundNum;

    @ApiModelProperty("商品入库主数量")
    @JsonProperty("mainNum")
    private Long praOutboundMainNum;

    @ApiModelProperty("实际价格")
    @JsonProperty("price")
    private BigDecimal praTaxPurchaseAmount;



}
