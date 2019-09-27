package com.aiqin.bms.scmp.api.product.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-09-27
 **/
@Data
public class SkuWarehouseResponse {

    @ApiModelProperty(value="sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="采购组名称")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="库房类型编码")
    @JsonProperty("warehouse_type_code")
    private String warehouseTypeCode;

    @ApiModelProperty(value="库房类型编码")
    @JsonProperty("warehouse_type_name")
    private String warehouseTypeName;

    @ApiModelProperty(value="wms库房code")
    @JsonProperty("wms_warehouse_code")
    private String wmsWarehouseCode;

    @ApiModelProperty(value="wms库房名称")
    @JsonProperty("wms_warehouse_name")
    private String wmsWarehouseName;

    @ApiModelProperty(value="wms库房类型 1 存储 2 退货")
    @JsonProperty("wms_warehouse_type")
    private Integer wmsWarehouseType;

}
