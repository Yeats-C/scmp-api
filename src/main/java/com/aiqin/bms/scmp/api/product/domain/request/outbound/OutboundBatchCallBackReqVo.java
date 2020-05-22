package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname: OutboundProductCallBackReqVo
 * 描述: 出库单skuWMS回调申请实体
 * @Author: Kt.w
 */
@Data
@ApiModel("出库单批次skuWMS回调申请实体")
public class OutboundBatchCallBackReqVo {

    @ApiModelProperty("出库单号")
    @JsonProperty(value = "outbound_oder_code")
    private String outboundOderCode;

    @ApiModelProperty("sku编号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("预计数量(最小单位数量)")
    @JsonProperty(value = "total_count")
    private Long totalCount;

    @ApiModelProperty("实际最小单位数量")
    @JsonProperty(value = "actual_total_count")
    private Long actualTotalCount;

    @ApiModelProperty("行号")
    @JsonProperty(value = "line_code")
    private Long lineCode;

    @ApiModelProperty("批次号")
    @JsonProperty(value = "batch_code")
    private String batchCode;

    @ApiModelProperty("批次编号")
    @JsonProperty(value = "batch_info_code")
    private String batchInfoCode;

    @ApiModelProperty("生产日期")
    @JsonProperty(value = "product_date")
    private String productDate;

    @ApiModelProperty(value="过期日期")
    @JsonProperty("be_overdue_date")
    private String beOverdueDate;

    @ApiModelProperty("批次备注")
    @JsonProperty(value = "batch_remark")
    private String batchRemark;

    @ApiModelProperty(value="库位号")
    @JsonProperty("location_code")
    private String locationCode;

}
