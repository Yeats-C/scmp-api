package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("库存流水实体Model")
@Data
public class StockFlow {
    
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("库存编码")
    @JsonProperty(value = "stock_code")
    private String stockCode;

    @ApiModelProperty("流水编码")
    @JsonProperty(value = "flow_code")
    private String flowCode;

    @ApiModelProperty("sku编码")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("修改之前的库存数")
    @JsonProperty(value = "before_inventory_count")
    private Long beforeInventoryCount;

    @ApiModelProperty("修改之后的库存数")
    @JsonProperty(value = "after_inventory_count")
    private Long afterInventoryCount;

    @ApiModelProperty("修改之前的可用库存数")
    @JsonProperty(value = "before_available_count")
    private Long beforeAvailableCount;

    @ApiModelProperty("修改之后的可用库存数")
    @JsonProperty(value = "after_available_count")
    private Long afterAvailableCount;

    @ApiModelProperty("修改之后的锁定库存数")
    @JsonProperty(value = "before_lock_count")
    private Long beforeLockCount;

    @ApiModelProperty("修改之后的锁定库存数")
    @JsonProperty(value = "after_lock_count")
    private Long afterLockCount;

    @ApiModelProperty("修改之前的采购在途数")
    @JsonProperty(value = "before_purchase_way_count")
    private Long beforePurchaseWayCount;

    @ApiModelProperty("修改之后的采购在途数")
    @JsonProperty(value = "after_purchase_way_count")
    private Long afterPurchaseWayCount;

    @ApiModelProperty("修改之前的调拨在途数")
    @JsonProperty(value = "before_allocation_way_count")
    private Long beforeAllocationWayCount;

    @ApiModelProperty("修改之后的调拨在途数")
    @JsonProperty(value = "after_allocation_way_count")
    private Long afterAllocationWayCount;

    @ApiModelProperty("修改之前的总在途数")
    @JsonProperty(value = "before_total_way_count")
    private Long beforeTotalWayCount;

    @ApiModelProperty("修改之后的总在途数")
    @JsonProperty(value = "after_total_way_count")
    private Long afterTotalWayCount;

    @ApiModelProperty("变化数")
    @JsonProperty(value = "change_count")
    private Long changeCount;

    @ApiModelProperty("操作类型")
    @JsonProperty(value = "operation_type")
    private Integer operationType;

    @ApiModelProperty("单据类型")
    @JsonProperty(value = "document_type")
    private Integer documentType;

    @ApiModelProperty("单据号")
    @JsonProperty(value = "document_code")
    private String documentCode;

    @ApiModelProperty("来源单据类型")
    @JsonProperty(value = "source_document_type")
    private Integer sourceDocumentType;

    @ApiModelProperty("来源单据号")
    @JsonProperty(value = "source_document_code")
    private String sourceDocumentCode;

    @ApiModelProperty("商品备注")
    @JsonProperty(value = "remark")
    private String remark;

    @ApiModelProperty("库存成本")
    @JsonProperty(value = "stock_cost")
    private BigDecimal stockCost;

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
