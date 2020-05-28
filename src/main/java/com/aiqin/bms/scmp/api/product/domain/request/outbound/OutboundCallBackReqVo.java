package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 描述: 出库单wms回调请求实体
 * @Author: Kt.w
 * @Date: 2019/3/6
 */
@Data
@ApiOperation("出库单wms回调请求实体")
public class OutboundCallBackReqVo{

    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("出库单编号")
    @JsonProperty(value = "outbound_oder_code")
    private String outboundOderCode;

    @ApiModelProperty("出库类型编码 1.退供 2.调拨 3.订单 4.移库")
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

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty("操作人")
    @JsonProperty(value = "operator_id")
    private String operatorId;

    @ApiModelProperty("操作人名称")
    @JsonProperty(value = "operator_name")
    private String operatorName;

    @ApiModelProperty("回调sku列表")
    @JsonProperty(value = "detail_list")
    private List<OutboundProductCallBackReqVo> detailList;

    @ApiModelProperty("回调sku批次列表")
    @JsonProperty(value = "batch_list")
    private List<OutboundBatchCallBackReqVo> batchList;

}
