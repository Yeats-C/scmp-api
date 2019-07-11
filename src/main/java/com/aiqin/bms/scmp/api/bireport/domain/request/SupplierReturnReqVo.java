package com.aiqin.bms.scmp.api.bireport.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("供应商退货(退供)request")
@Data
public class SupplierReturnReqVo extends PageReq implements Serializable {

    @ApiModelProperty("供应商编号")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房编码名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("入库时间begin")
    @JsonProperty("begin_create_time")
    private String beginCreateTime;

    @ApiModelProperty("入库时间finish")
    @JsonProperty("finish_create_time")
    private String finishCreateTime;

    public SupplierReturnReqVo(String supplierCode, String supplierName, String transportCenterCode, String transportCenterName, String warehouseCode, String warehouseName, String beginCreateTime, String finishCreateTime) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.transportCenterCode = transportCenterCode;
        this.transportCenterName = transportCenterName;
        this.warehouseCode = warehouseCode;
        this.warehouseName = warehouseName;
        this.beginCreateTime = beginCreateTime;
        this.finishCreateTime = finishCreateTime;
    }

    public SupplierReturnReqVo() {
    }
}
