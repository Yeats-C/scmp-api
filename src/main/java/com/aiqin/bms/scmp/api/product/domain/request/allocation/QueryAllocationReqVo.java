package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 描述:调拨列表展示请求实体
 *
 * @Author: Kt.w
 * @Date: 2019/1/9
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("调拨列表展示请求实体")
public class QueryAllocationReqVo extends PageReq {

    @ApiModelProperty("创建时间起始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建结束时间")
    private Date createEndTime;

    @ApiModelProperty("调拨单编码")
    private String allocationCode;

    @ApiModelProperty("状态编码")
    private Byte allocationStatusCode;

    @ApiModelProperty("调出(仓库)物流中心名称")
    private String callOutLogisticsCenterName;
    @ApiModelProperty("调出(仓库)物流中心编码")
    private String callOutLogisticsCenterCode;

    @ApiModelProperty("调出库房名称")
    private String callOutWarehouseName;

    @ApiModelProperty("调出库房编码")
    private String callOutWarehouseCode;

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("调入(仓库)物流中心名称")
    private String callInLogisticsCenterName;

    @ApiModelProperty("调入(仓库)物流中心编码")
    private String callInLogisticsCenterCode;

    @ApiModelProperty("调入库房名称")
    private String callInWarehouseName;

    @ApiModelProperty("调入库房编码")
    private String callInWarehouseCode;

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty(value = "公司编码", hidden = true)
    private String companyCode;

}
