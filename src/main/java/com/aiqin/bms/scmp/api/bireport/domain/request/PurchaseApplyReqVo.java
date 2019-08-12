package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("编辑采购申请request")
@Data
public class PurchaseApplyReqVo extends PagesRequest implements Serializable {

    @ApiModelProperty("sku编码")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("供应商code")
    @JsonProperty(value = "supplier_code")
    private String supplierCode;

    @ApiModelProperty("仓库编码")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    public PurchaseApplyReqVo(String skuCode, String supplierCode, String transportCenterCode) {
        this.skuCode = skuCode;
        this.supplierCode = supplierCode;
        this.transportCenterCode = transportCenterCode;
    }

    public PurchaseApplyReqVo() {

    }
}
