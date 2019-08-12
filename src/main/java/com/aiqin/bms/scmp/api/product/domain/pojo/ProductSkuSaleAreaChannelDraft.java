package com.aiqin.bms.scmp.api.product.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("销售区域渠道临时表")
@Data
public class ProductSkuSaleAreaChannelDraft {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("主表编码")
    private String code;

    @ApiModelProperty("价格渠道编码")
    private String priceChannelCode;

    @ApiModelProperty("价格渠道编码")
    private String priceChannelName;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;
}