package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StockFlowRequest {

    @ApiModelProperty("锁库单号编码(锁定时不传，解锁时传返回的编号)")
    @JsonProperty("lock_code")
    private String lockCode;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty("仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("解锁或者锁定数")
    @JsonProperty("change_num")
    private Long changeNum;

    @ApiModelProperty("加解锁库存属性正品0备品1")
    @JsonProperty("change_type")
    private Integer changeType;

    @ApiModelProperty("锁库类型:0.锁库 1.解锁")
    @JsonProperty("lock_type")
    private Integer lockType;

    @ApiModelProperty("解锁时历史锁定数")
    @JsonProperty("lock_num")
    private Long lockNum;

    @ApiModelProperty("操作类型")
    @JsonProperty("operation_type")
    private String operationType;

    public StockFlowRequest(){}
    public StockFlowRequest(String lockCode,String companyCode,String companyName,String transportCenterCode,String transportCenterName,String warehouseCode,String warehouseName,String skuCode,String skuName,Long changeNum,Integer changeType,Integer lockType,Long lockNum,String operationType){
        this.lockCode=lockCode;
        this.companyCode=companyCode;
        this.companyName=companyName;
        this.transportCenterCode=transportCenterCode;
        this.transportCenterName=transportCenterName;
        this.warehouseCode=warehouseCode;
        this.warehouseName=warehouseName;
        this.skuCode=skuCode;
        this.skuName=skuName;
        this.changeNum=changeNum;
        this.changeType=changeType;
        this.lockType=lockType;
        this.lockNum=lockNum;
        this.operationType=operationType;
    }
}
