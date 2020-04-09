package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class StockBatchFlow {
    @ApiModelProperty(value="")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="批次库存编码")
    @JsonProperty("stock_batch_code")
    private String stockBatchCode;

    @ApiModelProperty(value="流水编码")
    @JsonProperty("flow_batch_code")
    private String flowBatchCode;

    @ApiModelProperty(value="批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="状态(锁状态 1.加库存 2.锁定库存 3.减库存并解锁 4.解锁库存5.减库存 6.锁定库存移出 7.加并锁定库存 8.锁定库存移入)")
    @JsonProperty("operation_type")
    private Integer operationType;

    @ApiModelProperty(value="单据类型 0.出库单 1.入库单 2.销售单 3.退货单 4.采购单 5.退供单 5.调拨单 6.移库单 7.损溢单 8.报废单")
    @JsonProperty("document_type")
    private Integer documentType;

    @ApiModelProperty(value="单据号")
    @JsonProperty("document_code")
    private String documentCode;

    @ApiModelProperty(value="来源单据类型 0.出库单 1.入库单 2.销售单 3.退货单 4.采购单 5.退供单 5.调拨单 6.移库单 7.损溢单 8.报废单")
    @JsonProperty("source_document_type")
    private Integer sourceDocumentType;

    @ApiModelProperty(value="来源单据号")
    @JsonProperty("source_document_code")
    private String sourceDocumentCode;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="修改前总库存数")
    @JsonProperty("before_inventory_count")
    private Long beforeInventoryCount;

    @ApiModelProperty(value="修改后总库存数")
    @JsonProperty("after_inventory_count")
    private Long afterInventoryCount;

    @ApiModelProperty(value="修改前可用库存数")
    @JsonProperty("before_available_count")
    private Long beforeAvailableCount;

    @ApiModelProperty(value="修改后可用库存数")
    @JsonProperty("after_available_count")
    private Long afterAvailableCount;

    @ApiModelProperty(value="修改前锁定库存数")
    @JsonProperty("before_lock_count")
    private Long beforeLockCount;

    @ApiModelProperty(value="修改后锁定库存数")
    @JsonProperty("after_lock_count")
    private Long afterLockCount;

    @ApiModelProperty(value="修改数")
    @JsonProperty("change_count")
    private Long changeCount;

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