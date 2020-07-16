package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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

    @ApiModelProperty("库存数量")
    @JsonProperty("inventory_num")
    private Integer inventoryNum;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("仓库编码")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty(value = "transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty(value = "warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty(value = "warehouse_name")
    private String warehouseName;

    @ApiModelProperty("锁定库存数")
    @JsonProperty(value = "lock_num")
    private Long lockNum;

    @ApiModelProperty("库房类型")
    @JsonProperty(value = "warehouse_type")
    private Integer warehouseType;

    @ApiModelProperty("库存数量")
    @JsonProperty("available_num")
    private Long availableNum;

    @ApiModelProperty("sku编码")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("库存数")
    @JsonProperty(value = "inventory_count")
    private Long inventoryCount;

    @ApiModelProperty("可用库存数")
    @JsonProperty(value = "available_count")
    private Long availableCount;

    @ApiModelProperty("锁定库存数")
    @JsonProperty(value = "lock_count")
    private Long lockCount;

    @ApiModelProperty("采购在途数")
    @JsonProperty(value = "purchase_way_count")
    private Long purchaseWayCount;

    @ApiModelProperty("调拨在途数")
    @JsonProperty(value = "allocation_way_count")
    private Long allocationWayCount;

    @ApiModelProperty("总在途数")
    @JsonProperty(value = "total_way_count")
    private Long totalWayCount;

    @ApiModelProperty("最新供货单位")
    @JsonProperty(value = "new_delivery")
    private String newDelivery;

    @ApiModelProperty("最新供货单位名称")
    @JsonProperty(value = "new_delivery_name")
    private String newDeliveryName;

    @ApiModelProperty("最新采购价")
    @JsonProperty(value = "new_purchase_price")
    private BigDecimal newPurchasePrice;

    @ApiModelProperty("库存单位编码")
    @JsonProperty(value = "unit_code")
    private String unitCode;

    @ApiModelProperty("库存单位名称")
    @JsonProperty(value = "unit_name")
    private String unitName;

    @ApiModelProperty("税率")
    @JsonProperty(value = "tax_rate")
    private BigDecimal taxRate;


    @ApiModelProperty("含税成本")
    @JsonProperty("tax_price")
    private Long taxPrice;

    @ApiModelProperty("昨天含税成本")
    @JsonProperty(value = "tax_cost")
    private BigDecimal taxCost;

    @ApiModelProperty(value="0 启用  1.禁用")
    @JsonProperty("use_status")
    private Integer useStatus;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value="创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="更新时间")
    @JsonProperty("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value="更新人编码")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="更新人名称")
    @JsonProperty("update_by_name")
    private String updateByName;

}