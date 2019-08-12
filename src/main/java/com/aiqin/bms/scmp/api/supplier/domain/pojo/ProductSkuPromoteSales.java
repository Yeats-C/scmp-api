package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("SKU促销管理")
@Data
public class ProductSkuPromoteSales{

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    public ProductSkuPromoteSales(String skuCode, String skuName) {
        this.skuCode = skuCode;
        this.skuName = skuName;
    }
}