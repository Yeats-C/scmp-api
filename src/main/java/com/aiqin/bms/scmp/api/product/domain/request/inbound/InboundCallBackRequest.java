package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 描述:入库单回调请求实体
 */
@Data
@ApiModel("入库单回调请求实体")
public class InboundCallBackRequest {

    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("入库单编号")
    @JsonProperty(value = "inbound_oder_code")
    private String inboundOderCode;

    @ApiModelProperty("入库类型编码 1.采购 2.调拨 3.退货  4.移库")
    @JsonProperty(value = "inbound_type_code")
    private Integer inboundTypeCode;

    @ApiModelProperty("入库类型名称")
    @JsonProperty(value = "inbound_type_name")
    private String inboundTypeName;

    @ApiModelProperty("入库开始时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty(value = "begin_inbound_time")
    private Date beginInboundTime;

    @ApiModelProperty("入库完成时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty(value = "finish_inbound_time")
    private Date finishInboundTime;

    @ApiModelProperty("入库状态 采购、退货、调拨（1.入库开始 2.入库完成） 移库（1.移库新建 2,。移库完成）")
    @JsonProperty(value = "inbound_status")
    private Integer inboundStatus;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty("操作人")
    @JsonProperty(value = "operator_id")
    private String operatorId;

    @ApiModelProperty("操作人名称")
    @JsonProperty(value = "operator_name")
    private String operatorName;

    // 移库参数
//    @ApiModelProperty("移库单号")
//    @JsonProperty(value = "move_code")
//    private String moveCode;
//
//    @ApiModelProperty("出库库房编码")
//    @JsonProperty(value = "out_warehouse_code")
//    private String outWarehouseCode;
//
//    @ApiModelProperty("出库库房名称")
//    @JsonProperty(value = "out_warehouse_name")
//    private String outWarehouseName;
//
//    @ApiModelProperty("入库库房编码")
//    @JsonProperty(value = "in_warehouse_code")
//    private String inWarehouseCode;
//
//    @ApiModelProperty("入库库房名称")
//    @JsonProperty(value = "in_warehouse_name")
//    private String inWarehouseName;

    @ApiModelProperty("商品列表")
    @JsonProperty(value = "product_list")
    private List<InboundProductCallBackRequest> productList;

    @ApiModelProperty("批次列表")
    @JsonProperty(value = "batch_list")
    private List<InboundBatchCallBackRequest> batchList;

}
