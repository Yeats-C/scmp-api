package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("调拨主表")
@Data
public class Allocation extends CommonBean {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("调拨单编号")
    private String allocationCode;

    @ApiModelProperty("调出物流中心编号")
    private String calloutLogisticsCenterCode;

    @ApiModelProperty("调出物流中心名称")
    private String calloutLogisticsCenterName;

    @ApiModelProperty("调出仓库编码")
    private String calloutWarehouseCode;

    @ApiModelProperty("调出仓库名称")
    private String calloutWarehouseName;

    @ApiModelProperty("仓库类型编码")
    private String inventoryAttributesCode;

    @ApiModelProperty("仓库类型名称")
    private String inventoryAttributesName;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("调入物流中心编码")
    private String callinLogisticsCenterCode;

    @ApiModelProperty("调入物流中心名称")
    private String callinLogisticsCenterName;

    @ApiModelProperty("调入仓库编码")
    private String callinWarehouseCode;

    @ApiModelProperty("调入仓库名称")
    private String callinWarehouseName;

    @ApiModelProperty("调拨类型编码")
    private Byte allocationTypeCode;

    @ApiModelProperty("调拨类型名称")
    private String allocationTypeName;

    @ApiModelProperty("负责人")
    private String principal;

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

    @ApiModelProperty("审批流程id")
    private String formNo;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;
}