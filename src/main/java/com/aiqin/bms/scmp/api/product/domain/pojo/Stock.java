package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("库存实体Model")
@Data
public class Stock extends CommonBean {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("库存编码")
    @JsonProperty(value = "stock_code")
    private String stockCode;

    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("物流中心编码")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("物流中心名称")
    @JsonProperty(value = "transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房code")
    @JsonProperty(value = "warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty(value = "warehouse_name")
    private String warehouseName;

    @ApiModelProperty("库房类型")
    @JsonProperty(value = "warehouse_type")
    private String warehouseType;

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("库存数")
    @JsonProperty(value = "inventory_num")
    private Long inventoryNum;


    @ApiModelProperty("可用库存数")
    @JsonProperty(value = "available_num")
    private Long availableNum;

    @ApiModelProperty("锁定库存数")
    @JsonProperty(value = "lock_num")
    private Long lockNum;

    @ApiModelProperty("采购在途数")
    @JsonProperty(value = "purchase_way_num")
    private Long purchaseWayNum;

    @ApiModelProperty("调拨在途数")
    @JsonProperty(value = "allocation_way_num")
    private Long allocationWayNum;

    @ApiModelProperty("总在途数")
    @JsonProperty(value = "total_way_num")
    private Long totalWayNum;

    @ApiModelProperty("最新供货单位")
    @JsonProperty(value = "new_delivery")
    private String newDelivery;

    @ApiModelProperty("最新供货单位名称")
    @JsonProperty(value = "new_delivery_name")
    private String newDeliveryName;

    @ApiModelProperty("最新采购价")
    @JsonProperty(value = "new_purchase_price")
    private Long newPurchasePrice;

    @ApiModelProperty("采购组编码")
    @JsonProperty(value = "purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    @JsonProperty(value = "purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty("库存单位编码")
    @JsonProperty(value = "stock_unit_code")
    private String stockUnitCode;

    @ApiModelProperty("库存单位名称")
    @JsonProperty(value = "stock_unit_name")
    private String stockUnitName;

    @ApiModelProperty("库存含税金额")
    @JsonProperty(value = "tax_price")
    private Long taxPrice;

    @ApiModelProperty("含税金额")
    @JsonProperty(value = "tax_rate")
    private Long taxRate;
}