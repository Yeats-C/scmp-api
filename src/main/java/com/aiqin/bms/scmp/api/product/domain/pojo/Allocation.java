package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
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
    private BigDecimal totalCostRate;

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

    @ApiModelProperty("审批名称")
    private String approvalName;

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

    @ApiModelProperty("物流费用")
    @NotEmpty(message = "物流费用不能为空")
    private Long logisticsOutlay;


    @ApiModelProperty(value = "调拨移库损益模式（1，我方发起 2，wms方发起）")
    private Integer patternType;

    @ApiModelProperty(value = "调拨移库损益模式")
    private String patternName;

    @ApiModelProperty(value = "创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value = "创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    /** 以下是dl回调需要用的字段*/

    private List<AllocationProduct> detailList;

    private List<AllocationProductBatch> detailBatchList;
}