package com.aiqin.bms.scmp.api.product.domain.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("价格管理列表页显示销售区域")
@Data
public class ProductSkuSaleAreaInfoRespVo {


    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("主表编码")
    private String mainCode;

    @ApiModelProperty("销售区域名称")
    private String name;

    @ApiModelProperty("区域或门店编码")
    private String code;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

}
