package com.aiqin.bms.scmp.api.product.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("sku进货量")
public class SkuPurchaseVolume  {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("sku名称")
    private String skuCode;

    @ApiModelProperty("进货量")
    private Long purchaseVolume;

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

    public Long getPurchaseVolume() {
        return purchaseVolume;
    }

    public void setPurchaseVolume(Long purchaseVolume) {
        this.purchaseVolume = purchaseVolume;
    }
}