package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("调拨主表")
@Data
public class Allocation extends CommonBean {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("调拨单编号")
    private String allocationCode;

    @ApiModelProperty("调出仓库(物流中心)编号")
    private String callOutLogisticsCenterCode;

    @ApiModelProperty("调出仓库(物流中心)名称")
    private String callOutLogisticsCenterName;

    @ApiModelProperty("调出库房编码")
    private String callOutWarehouseCode;

    @ApiModelProperty("调出库房名称")
    private String callOutWarehouseName;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("调入仓库(物流中心)编码")
    private String callInLogisticsCenterCode;

    @ApiModelProperty("调入仓库(物流中心)名称")
    private String callInLogisticsCenterName;

    @ApiModelProperty("调入库房编码")
    private String callInWarehouseCode;

    @ApiModelProperty("调入库房名称")
    private String callInWarehouseName;

    @ApiModelProperty("负责人")
    private String principal;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    private Long totalCostRate;

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

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("承担单位编码")
    private String undertakingUnitCode;

    @ApiModelProperty("承担单位名称")
    private String undertakingUnitName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("调拨类型(1:调拨 2:移库 3:报废)")
    private Byte allocationType;

    @ApiModelProperty("调拨类型(1:调拨 2:移库 3:报废)")
    private String allocationTypeName;

    @ApiModelProperty("是否是调出仓库")
    private Boolean flag = Boolean.TRUE;

    /** 以下是dl回调需要用的字段*/

    private List<AllocationProduct> detailList;
}