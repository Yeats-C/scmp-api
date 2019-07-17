package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("供应商到货率request")
@Data
public class SupplierArrivalRateReqVo extends PageReq implements Serializable {

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
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("一级品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_run_time")
    private String beginRunTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_run_time")
    private String finishRunTime;

    public SupplierArrivalRateReqVo(String supplierCode, String supplierName, String transportCenterCode, String categoryCode, String categoryName, String beginRunTime, String finishRunTime) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.transportCenterCode = transportCenterCode;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.beginRunTime = beginRunTime;
        this.finishRunTime = finishRunTime;
    }

    public SupplierArrivalRateReqVo() {
    }
}
