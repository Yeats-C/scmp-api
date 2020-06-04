package com.aiqin.bms.scmp.api.product.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class ProductSkuChangePriceSaleArea {
    @ApiModelProperty(value="主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="变价编码")
    @JsonProperty("change_price_code")
    private String changePriceCode;

    @ApiModelProperty(value="销售区域编码")
    @JsonProperty("sale_area_code")
    private String saleAreaCode;

    @ApiModelProperty(value="销售区域名称")
    @JsonProperty("sale_area_name")
    private String saleAreaName;

    @ApiModelProperty(value="0禁止1开放")
    @JsonProperty("status")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChangePriceCode() {
        return changePriceCode;
    }

    public void setChangePriceCode(String changePriceCode) {
        this.changePriceCode = changePriceCode;
    }

    public String getSaleAreaCode() {
        return saleAreaCode;
    }

    public void setSaleAreaCode(String saleAreaCode) {
        this.saleAreaCode = saleAreaCode;
    }

    public String getSaleAreaName() {
        return saleAreaName;
    }

    public void setSaleAreaName(String saleAreaName) {
        this.saleAreaName = saleAreaName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}