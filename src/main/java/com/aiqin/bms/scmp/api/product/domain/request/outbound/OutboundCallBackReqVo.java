package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.aiqin.bms.scmp.api.product.domain.request.BaseDateRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Classname: OutboundCallBackReqVo
 * 描述: 出库单wms回调请求实体
 * @Author: Kt.w
 * @Date: 2019/3/6
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiOperation("出库单wms回调请求实体")
public class OutboundCallBackReqVo extends BaseDateRequest {

//    @ApiModelProperty("id")
//    private Long id;
//
//    @ApiModelProperty("入库时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private Date outboundTime;
//
//    @ApiModelProperty("实际主单位数量")
//    private Long praMainUnitNum;
//
//    @ApiModelProperty("操作人")
//    private String createById;
//
//    @ApiModelProperty("sku 列表")
//    private List<OutboundProductCallBackReqVo> list;

    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("出库单编号")
    @JsonProperty(value = "outbound_oder_code")
    private String outboundOderCode;

  //  @ApiModelProperty("来源单号")
  //  @JsonProperty(value = "source_oder_code")
  //  private String sourceOderCode;

    @ApiModelProperty("出库类型编码 1.采购 2.调拨 3.退货  4.移库")
    @JsonProperty(value = "outbound_type_code")
    private Integer outboundTypeCode;

    @ApiModelProperty("出库类型名称")
    @JsonProperty(value = "outbound_type_name")
    private String outboundTypeName;

    @ApiModelProperty("出库开始时间（拣货时间）")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty(value = "begin_outbound_time")
    private Date beginOutboundTime;

    @ApiModelProperty("扫描时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty(value = "scan_outbound_time")
    private Date scanOutboundTime;

    @ApiModelProperty("出库完成时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty(value = "finish_outbound_time")
    private Date finishOutboundTime;

    @ApiModelProperty("出库状态 采购、退货、调拨（1.出库开始 2.出库完成） 移库（1.移库新建 2,。移库完成） 销售（1.开始拣货 2.扫描完成） 3.已全部发货")
    @JsonProperty(value = "outbound_status")
    private Integer outboundStatus;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty("操作人")
    @JsonProperty(value = "operator_id")
    private String operatorId;

    @ApiModelProperty("操作人名称")
    @JsonProperty(value = "operator_name")
    private String operatorName;

  //  @ApiModelProperty("物流中心编码")
  //  @NotEmpty(message = "物流中心编码不能为空")
  //  private String logisticsCenterCode;

  //  @ApiModelProperty("物流中心名称")
  //  @NotEmpty(message = "物流中心名称不能为空")
  //  private String logisticsCenterName;

  //  @ApiModelProperty("库房编码")
  //  @NotEmpty(message = "库房编码不能为空")
  //  private String warehouseCode;

  //  @ApiModelProperty("库房名称")
  //  @NotEmpty(message = "库房名称不能为空")
  //  private String warehouseName;

  //  @ApiModelProperty("出库时间")
  //  @NotEmpty(message = "出库时间不能为空")
  //  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  //  private Date outboundTime;

  //  @ApiModelProperty("实际主单位数量")
  //  @NotNull(message = "实际主单位数量不能为空")
  //  private Long praMainUnitNum;

  //  @ApiModelProperty("实际含税总金额")
  //  private BigDecimal praTaxAmount;

  //  @ApiModelProperty("实际无税总金额")
  //  private BigDecimal praAmount;

  //  @ApiModelProperty("实际税额")
  //  private BigDecimal praTax;

    @ApiModelProperty("回调sku列表")
    @JsonProperty(value = "list")
    private List<OutboundProductCallBackReqVo> list;

    @ApiModelProperty("回调sku批次列表")
    @JsonProperty(value = "batch_list")
    private List<OutboundBatchCallBackReqVo> batchList;


}
