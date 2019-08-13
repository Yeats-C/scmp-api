package com.aiqin.bms.scmp.api.product.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("变价图片上传附表")
@Data
public class ProductSkuChangePriceImage {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("主表编码")
    private String code;

    @ApiModelProperty("图片名称")
    private String name;

    @ApiModelProperty("图片地址")
    private String url;
}