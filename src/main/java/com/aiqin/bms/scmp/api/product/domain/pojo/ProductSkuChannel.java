package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("sku渠道管理")
@Data
public class ProductSkuChannel extends CommonBean {
    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("价格渠道编码")
    private String priceChannelCode;

    @ApiModelProperty("价格渠道名称")
    private String priceChannelName;

    @ApiModelProperty("")
    private String applyCode;

}