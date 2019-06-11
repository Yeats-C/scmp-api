package com.aiqin.bms.scmp.api.product.domain.response.sku.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/30 0030 14:29
 */
@ApiModel("门店sku详情销售信息")
@Data
public class StoreSkuSalesInfo {
    @ApiModelProperty("宽")
    @JsonProperty("product_width")
    private Long productWidth;

    @ApiModelProperty("长")
    @JsonProperty("product_length")
    private Long productLength;

    @ApiModelProperty("高度")
    @JsonProperty("product_height")
    private Long productHeight;

    @ApiModelProperty("销售码")
    @JsonProperty("sales_code")
    private String salesCode;
}
