package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("库存流水实体Model")
@Data
public class StockFlow extends CommonBean {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("库存编码")
    @JsonProperty(value = "stock_code")
    private String stockCode;

    @ApiModelProperty("流水编码")
    @JsonProperty(value = "flow_code")
    private String flowCode;

    @ApiModelProperty("订单编号")
    @JsonProperty(value = "order_code")
    private String orderCode;

    @ApiModelProperty("订单类型")
    @JsonProperty(value = "order_type")
    private Integer orderType;

    @ApiModelProperty("订单来源")
    @JsonProperty(value = "order_source")
    private String orderSource;

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("修改之前的库存数")
    @JsonProperty(value = "before_inventory_num")
    private Long beforeInventoryNum;

    @ApiModelProperty("修改之后的库存数")
    @JsonProperty(value = "after_inventory_num")
    private Long afterInventoryNum;

    @ApiModelProperty("修改之前的可用库存数")
    @JsonProperty(value = "before_available_num")
    private Long beforeAvailableNum;

    @ApiModelProperty("修改之后的可用库存数")
    @JsonProperty(value = "after_available_num")
    private Long afterAvailableNum;

    @ApiModelProperty("修改之后的锁定库存数")
    @JsonProperty(value = "before_lock_num")
    private Long beforeLockNum;

    @ApiModelProperty("修改之后的锁定库存数")
    @JsonProperty(value = "after_lock_num")
    private Long afterLockNum;

    @ApiModelProperty("修改之前的采购在途数")
    @JsonProperty(value = "before_purchase_way_num")
    private Long beforePurchaseWayNum;

    @ApiModelProperty("修改之后的采购在途数")
    @JsonProperty(value = "after_purchase_way_num")
    private Long afterPurchaseWayNum;

    @ApiModelProperty("修改之前的调拨在途数")
    @JsonProperty(value = "before_allocation_way_num")
    private Long beforeAllocationWayNum;

    @ApiModelProperty("修改之后的调拨在途数")
    @JsonProperty(value = "after_allocation_way_num")
    private Long afterAllocationWayNum;

    @ApiModelProperty("修改之前的总在途数")
    @JsonProperty(value = "before_total_way_num")
    private Long beforeTotalWayNum;

    @ApiModelProperty("修改之后的总在途数")
    @JsonProperty(value = "after_total_way_num")
    private Long afterTotalWayNum;

    @ApiModelProperty("变化数")
    @JsonProperty(value = "change_num")
    private Long changeNum;

    @ApiModelProperty("操作类型")
    @JsonProperty(value = "operation_type")
    private Integer operationType;

    @ApiModelProperty("状态")
    @JsonProperty(value = "lock_status")
    private Integer lockStatus;

    @ApiModelProperty("单据类型")
    @JsonProperty(value = "document_type")
    private Integer documentType;

    @ApiModelProperty("单据号")
    @JsonProperty(value = "document_num")
    private String documentNum;

    @ApiModelProperty("来源单据类型")
    @JsonProperty(value = "source_document_type")
    private Integer sourceDocumentType;

    @ApiModelProperty("来源单据号")
    @JsonProperty(value = "source_document_num")
    private String sourceDocumentNum;

    @ApiModelProperty("商品备注")
    @JsonProperty(value = "remark")
    private String remark;

    @ApiModelProperty("库存成本")
    @JsonProperty(value = "stock_cost")
    private String stockCost;
}
