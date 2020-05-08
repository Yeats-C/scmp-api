package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * wms回传订单商品明细行实体
 */
@Data
public class WmsOrderItem {

    @ApiModelProperty(value = "行号")
    @JsonProperty("line_code")
    private String lineCode;

    @ApiModelProperty(value = "SKU编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "实际最小单位数量")
    @JsonProperty("product_count")
    private String productCount;
}
