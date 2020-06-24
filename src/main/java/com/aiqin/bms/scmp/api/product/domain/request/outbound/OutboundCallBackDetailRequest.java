package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: scmp-api
 * @author: zhao shuai
 * @create: 2019-12-27
 **/
@Data
public class OutboundCallBackDetailRequest {

    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value = "实发数量")
    @JsonProperty("actual_product_count")
    private Long actualProductCount;

    @ApiModelProperty("行号")
    @JsonProperty("line_code")
    private Long lineCode;

    @ApiModelProperty(value="商品唯一码")
    @JsonProperty(value = "unique_code")
    private String uniqueCode;
}
