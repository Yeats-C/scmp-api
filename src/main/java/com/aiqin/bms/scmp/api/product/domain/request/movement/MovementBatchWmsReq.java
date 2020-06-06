package com.aiqin.bms.scmp.api.product.domain.request.movement;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("wms移库推送商品批次表")
@Data
public class MovementBatchWmsReq {

    @ApiModelProperty("行号")
    @JsonProperty(value = "line_code")
    private Long lineCode;

    @ApiModelProperty("sku编号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("入库批次号")
    @JsonProperty(value = "call_in_batch_code")
    private String callInBatchCode;

    @ApiModelProperty("出库批次号")
    @JsonProperty(value = "call_out_batch_code")
    private String callOutBatchCode;

    @ApiModelProperty("入库批次编号")
    @JsonProperty(value = "call_in_batch_info_code")
    private String callInBatchInfoCode;

    @ApiModelProperty("出库批次编号")
    @JsonProperty(value = "call_out_batch_info_code")
    private String callOutBatchInfoCode;

    @ApiModelProperty("批次号")
    @JsonProperty(value = "batch_code")
    private String batchCode;

    @ApiModelProperty("生产日期")
    @JsonProperty(value = "product_date")
    private String productDate;

    @ApiModelProperty("过期日期")
    @JsonProperty(value = "be_overdue_date")
    private String beOverdueDate;

    @ApiModelProperty("批次备注")
    @JsonProperty(value = "batch_number_remark")
    private String batchNumberRemark;

    @ApiModelProperty("数量")
    @JsonProperty(value = "quantity")
    private Long quantity;
}
