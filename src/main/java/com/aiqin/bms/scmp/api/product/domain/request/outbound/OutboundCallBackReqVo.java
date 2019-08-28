package com.aiqin.bms.scmp.api.product.domain.request.outbound;

import com.aiqin.bms.scmp.api.product.domain.request.BaseDateRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
//
    @ApiModelProperty("id")
    private Long id;
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
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("出库单编号")
    @NotEmpty(message = "出库单编号不能为空")
    private String outboundOderCode;

    @ApiModelProperty("来源单号")
    @NotEmpty(message = "来源单号不能为空")
    private String sourceOderCode;

    @ApiModelProperty("出库类型编码")
    @NotEmpty(message = "出库类型编码")
    private Byte outboundTypeCode;

    @ApiModelProperty("出库类型名称")
    @NotEmpty(message = "出库类型名称")
    private String outboundTypeName;

    @ApiModelProperty("物流中心编码")
    @NotEmpty(message = "物流中心编码不能为空")
    private String logisticsCenterCode;

    @ApiModelProperty("物流中心名称")
    @NotEmpty(message = "物流中心名称不能为空")
    private String logisticsCenterName;

    @ApiModelProperty("库房编码")
    @NotEmpty(message = "库房编码不能为空")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @NotEmpty(message = "库房名称不能为空")
    private String warehouseName;

    @ApiModelProperty("出库时间")
    @NotEmpty(message = "出库时间不能为空")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date outboundTime;

    @ApiModelProperty("实际主单位数量")
    @NotNull(message = "实际主单位数量不能为空")
    private Long praMainUnitNum;

    @ApiModelProperty("实际含税总金额")
    private Long praTaxAmount;

    @ApiModelProperty("实际无税总金额")
    private Long praAmount;

    @ApiModelProperty("实际税额")
    private Long praTax;

    @ApiModelProperty("回调sku列表")
    private List<OutboundProductCallBackReqVo> list;


}
