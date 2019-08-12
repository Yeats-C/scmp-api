package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("批次库存流水实体Model")
@Data
public class StockBatchFlow{
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("批次库存编码")
    @JsonProperty(value = "stock_batch_code")
    private String stockBatchCode;

    @ApiModelProperty("流水编码")
    @JsonProperty(value = "flow_batch_code")
    private String flowBatchCode;

    @ApiModelProperty("批次号")
    @JsonProperty(value = "batch_code")
    private String batchCode;

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

    @ApiModelProperty("状态(锁状态-1加库存2锁定库存3减库存并解锁4解锁库存5减库存6锁定库存转移7加并锁定库存)")
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

    @ApiModelProperty("变化数")
    @JsonProperty(value = "change_num")
    private Long changeNum;

    @ApiModelProperty("操作类型")
    @JsonProperty(value = "operation_type")
    private Integer operationType;

    @ApiModelProperty("创建时间")
    @JsonProperty(value = "create_time")
    private String createTime;

    @ApiModelProperty("创建人code")
    @JsonProperty(value = "create_by_code")
    private String createByCode;

    @ApiModelProperty("创建人名称")
    @JsonProperty(value = "create_by_name")
    private String createByName;

    @ApiModelProperty("更新时间")
    @JsonProperty(value = "update_time")
    private String updateTime;

    @ApiModelProperty("更新人code")
    @JsonProperty(value = "update_by_code")
    private String updateByCode;

    @ApiModelProperty("更新人名称")
    @JsonProperty(value = "update_by_name")
    private String updateByName;

    }
