package com.aiqin.bms.scmp.api.product.domain.response.allocation;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 描述:调拨列表展示返回实体
 *
 * @Author: Kt.w
 * @Date: 2019/1/9
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("调拨列表展示返回实体")
public class QueryAllocationResVo extends PageReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("调拨单编号")
    private String allocationCode;

    @ApiModelProperty("调出物流中心(仓库)编号")
    private String calloutLogisticsCenterCode;

    @ApiModelProperty("调出物流中心(仓库)名称")
    private String calloutLogisticsCenterName;

    @ApiModelProperty("调出库房编码")
    private String calloutWarehouseCode;

    @ApiModelProperty("调出库房名称")
    private String calloutWarehouseName;

    @ApiModelProperty("调入物流中心(仓库)编码")
    private String callinLogisticsCenterCode;

    @ApiModelProperty("调入物流中心(仓库)名称")
    private String callinLogisticsCenterName;

    @ApiModelProperty("调入库房编码")
    private String callinWarehouseCode;

    @ApiModelProperty("调入库房名称")
    private String callinWarehouseName;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税调拨金额")
    private Long taxRefundAmount;

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("状态编码")
    private Byte allocationStatusCode;

    @ApiModelProperty("状态名称")
    private String allocationStatusName;

    @ApiModelProperty("删除状态")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;
}
