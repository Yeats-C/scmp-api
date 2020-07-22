package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductSkuPushWms {

    @ApiModelProperty("主键id")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("是否推送到wms（0.是 1.不是）")
    @JsonProperty("is_push_wms")
    private Integer isPushWms;
}
