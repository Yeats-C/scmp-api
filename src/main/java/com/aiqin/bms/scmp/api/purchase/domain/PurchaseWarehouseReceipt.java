package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class PurchaseWarehouseReceipt {
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="入库单id")
    @JsonProperty("warehouse_id")
    private String warehouseId;

    @ApiModelProperty(value="入库单号")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="采购单id")
    @JsonProperty("purchase_order_id")
    private String purchaseOrderId;

    @ApiModelProperty(value="入库状态 0.新建、1.入库开始、2.完成、3.取消")
    @JsonProperty("warehouse_status")
    private Integer warehouseStatus;

    @ApiModelProperty(value="创建时间（入库时间）")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="创建人")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value="修改人")
    @JsonProperty("update_by")
    private String updateBy;
}