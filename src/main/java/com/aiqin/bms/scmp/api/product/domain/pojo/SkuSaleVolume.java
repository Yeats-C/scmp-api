package com.aiqin.bms.scmp.api.product.domain.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("sku销量")
public class SkuSaleVolume {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("sku名称")
    private String skuCode;

    @ApiModelProperty("销量")
    private Long saleVolume;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    public Long getSaleVolume() {
        return saleVolume;
    }

    public void setSaleVolume(Long saleVolume) {
        this.saleVolume = saleVolume;
    }
}