package com.aiqin.bms.scmp.api.product.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("销售区域附表草稿")
@Data
public class ProductSkuSaleAreaInfoDraft {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("主表编码")
    private String mainCode;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("1区域2门店")
    private Integer type;

    @ApiModelProperty("0禁止1开放")
    private Integer status;

    @ApiModelProperty("区域或门店名称")
    private String name;

    @ApiModelProperty("区域或门店编码")
    private String code;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("省id")
    private String provinceId;
}