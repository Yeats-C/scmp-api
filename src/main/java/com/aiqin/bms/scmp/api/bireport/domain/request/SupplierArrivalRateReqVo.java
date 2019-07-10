package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("供应商到货率request")
@Data
public class SupplierArrivalRateReqVo {

    @ApiModelProperty("供应商code")
    @JsonProperty(value = "supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商name")
    @JsonProperty(value = "supplier_name")
    private String supplierName;

    @ApiModelProperty("仓库编码")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("一级品类编号")
    @JsonProperty("category_level_code")
    private String categoryLevelCode;

    @ApiModelProperty("一级品类名称")
    @JsonProperty("category_level_name")
    private String categoryLevelName;

    @ApiModelProperty("入库时间begin")
    @JsonProperty("begin_inbound_time")
    private String beginInboundTime;

    @ApiModelProperty("入库时间finish")
    @JsonProperty("finish_inbound_time")
    private String finishInboundTime;

    public SupplierArrivalRateReqVo(String supplierCode, String supplierName, String transportCenterCode, String categoryLevelCode, String categoryLevelName, String beginInboundTime, String finishInboundTime) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.transportCenterCode = transportCenterCode;
        this.categoryLevelCode = categoryLevelCode;
        this.categoryLevelName = categoryLevelName;
        this.beginInboundTime = beginInboundTime;
        this.finishInboundTime = finishInboundTime;
    }

    public SupplierArrivalRateReqVo() {
    }
}
