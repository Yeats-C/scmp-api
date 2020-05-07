package com.aiqin.bms.scmp.api.product.domain.response.allocation;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
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
public class QueryAllocationResVo {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("调拨单编号")
    private String allocationCode;

    @ApiModelProperty("调出物流中心(仓库)编号")
    private String callOutLogisticsCenterCode;

    @ApiModelProperty("调出物流中心(仓库)名称")
    private String callOutLogisticsCenterName;

    @ApiModelProperty("调出库房编码")
    private String callOutWarehouseCode;

    @ApiModelProperty("调出库房名称")
    private String callOutWarehouseName;

    @ApiModelProperty("调入物流中心(仓库)编码")
    private String callInLogisticsCenterCode;

    @ApiModelProperty("调入物流中心(仓库)名称")
    private String callInLogisticsCenterName;

    @ApiModelProperty("调入库房编码")
    private String callInWarehouseCode;

    @ApiModelProperty("调入库房名称")
    private String callInWarehouseName;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("出库数量")
    private Long callOutQuantity;

    @ApiModelProperty("入库数量")
    private Long  callInQuantity;

   // @ApiModelProperty("采购组编码")
   // private String purchaseGroupCode;

   // @ApiModelProperty("采购组名称")
   // private String purchaseGroupName;

    @ApiModelProperty("含税调拨金额(含税库存成本)")
    private BigDecimal taxRefundAmount;

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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("负责人")
    private String principal;

    @ApiModelProperty("审批流水编号")
    private String formNo;

    @ApiModelProperty(value = "调拨差异类型（1.有 2.无）")
    private Integer differenceType;

    @ApiModelProperty(value = "调拨差异名称")
    private String differenceName;

}
