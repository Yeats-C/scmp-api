package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Classname: InboundCallBackReqVo
 * 描述:入库单回调请求实体
 * @Author: Kt.w
 * @Date: 2019/3/6
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("入库单回调请求实体")
public class InboundCallBackReqVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("入库单编号")
    private String inboundOderCode;

    @ApiModelProperty("入库类型编码")
    private Byte inboundTypeCode;

    @ApiModelProperty("入库类型名称")
    private String inboundTypeName;

    @ApiModelProperty("来源单号")
    private String sourceOderCode;

    @ApiModelProperty("入库时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date inboundTime;

    @ApiModelProperty("物流中心编码")
    private String logisticsCenterCode;

    @ApiModelProperty("物流中心名称")
    private String logisticsCenterName;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("实际入库数量")
    private Long praInboundNum;

    @ApiModelProperty("实际主单位数量")
    private Long praMainUnitNum;

    @ApiModelProperty("实际含税总金额")
    private Long praTaxAmount;

    @ApiModelProperty("实际无税总金额")
    private Long praAmount;

    @ApiModelProperty("实际税额")
    private Long praTax;

    @ApiModelProperty("sku 列表")
    private List<InboundProductCallBackReqVo> list;

    @ApiModelProperty("批次列表")
    private List<InboundBatchCallBackReqVo> inboundBatchCallBackReqVos;

}
