package com.aiqin.bms.scmp.api.product.domain.request.movement;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Classname: QueryMovementReqVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("移库列表搜索分页查询模糊搜索")
public class QueryMovementReqVo extends PageReq {
    @ApiModelProperty("创建时间起始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建结束时间")
    private Date createEndTime;

    @ApiModelProperty("移库单编码")
    private String movementCode;

    @ApiModelProperty("状态编码")
    private Byte movementStatusCode;

    @ApiModelProperty("所属仓库编码")
    private String logisticsCenterCode;

    @ApiModelProperty("所属仓库名称")
    private String logisticsCenterName;

    @ApiModelProperty("调出库房编码")
    private String callOutWarehouseCode;

    @ApiModelProperty("调出库房名称")
    private String callOutWarehouseName;

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("调入库房编码")
    private String callInWarehouseCode;

    @ApiModelProperty("调入库房名称")
    private String callInWarehouseName;

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty(value = "公司编码",hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "调拨类型",hidden = true)
    private Byte allocationType;
}
