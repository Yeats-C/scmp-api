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
    private String calloutLogisticsCenterName;
    @ApiModelProperty("调出(仓库)物流中心编码")
    private String calloutLogisticsCenterCode;

    @ApiModelProperty("调出库房名称")
    private String calloutWarehouseName;

    @ApiModelProperty("调出库房编码")
    private String calloutWarehouseCode;

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("调入物流中心名称")
    private String callinLogisticsCenterName;

    @ApiModelProperty("调入仓库名称")
    private String callinWarehouseName;

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;
}
