package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel("调拨主表")
@Data
public class Allocation extends CommonBean {
    @ApiModelProperty("主键")
    @JsonProperty(value = "id")
    private Long id;

    @ApiModelProperty("调拨单编号")
    @JsonProperty(value = "allocation_code")
    private String allocationCode;

    @ApiModelProperty("调出仓库(物流中心)编号")
    @JsonProperty(value = "call_out_logistics_center_code")
    private String callOutLogisticsCenterCode;

    @ApiModelProperty("调出仓库(物流中心)名称")
    @JsonProperty(value = "call_out_logistics_center_name")
    private String callOutLogisticsCenterName;

    @ApiModelProperty("调出库房编码")
    @JsonProperty(value = "call_out_warehouse_code")
    private String callOutWarehouseCode;

    @ApiModelProperty("调出库房名称")
    @JsonProperty(value = "call_out_warehouse_name")
    private String callOutWarehouseName;

    @ApiModelProperty("采购组编码")
    @JsonProperty(value = "purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    @JsonProperty(value = "purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty("调入仓库(物流中心)编码")
    @JsonProperty(value = "call_in_logistics_center_code")
    private String callInLogisticsCenterCode;

    @ApiModelProperty("调入仓库(物流中心)名称")
    @JsonProperty(value = "call_in_logistics_center_name")
    private String callInLogisticsCenterName;

    @ApiModelProperty("调入库房编码")
    @JsonProperty(value = "call_in_warehouse_code")
    private String callInWarehouseCode;

    @ApiModelProperty("调入库房名称")
    @JsonProperty(value = "call_in_warehouse_name")
    private String callInWarehouseName;

    @ApiModelProperty("负责人")
    @JsonProperty(value = "principal")
    private String principal;

    @ApiModelProperty("数量")
    @JsonProperty(value = "quantity")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    @JsonProperty(value = "total_cost_rate")
    private BigDecimal totalCostRate;

    @ApiModelProperty("出库单号")
    @JsonProperty(value = "outbound_oder_code")
    private String outboundOderCode;

    @ApiModelProperty("入库单号")
    @JsonProperty(value = "inbound_oder_code")
    private String inboundOderCode;

    @ApiModelProperty("状态编码")
    @JsonProperty(value = "allocation_status_code")
    private Byte allocationStatusCode;

    @ApiModelProperty("状态名称")
    @JsonProperty(value = "allocation_status_name")
    private String allocationStatusName;

    @ApiModelProperty("审批流程id")
    @JsonProperty(value = "form_no")
    private String formNo;

    @ApiModelProperty("审批名称")
    @JsonProperty(value = "approval_name")
    private String approvalName;

    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("直属上级编码")
    @JsonProperty(value = "direct_supervisor_code")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    @JsonProperty(value = "direct_supervisor_name")
    private String directSupervisorName;

    @ApiModelProperty("承担单位编码")
    @JsonProperty(value = "undertaking_unit_code")
    private String undertakingUnitCode;

    @ApiModelProperty("承担单位名称")
    @JsonProperty(value = "undertaking_unit_name")
    private String undertakingUnitName;

    @ApiModelProperty("备注")
    @JsonProperty(value = "remark")
    private String remark;

    @ApiModelProperty("调拨类型(1:调拨 2:移库 3:报废)")
    @JsonProperty(value = "allocation_type")
    private Byte allocationType;

    @ApiModelProperty("调拨类型(1:调拨 2:移库 3:报废)")
    @JsonProperty(value = "allocation_type_name")
    private String allocationTypeName;

    @ApiModelProperty("是否是调出仓库")
    @JsonProperty(value = "flag")
    private Boolean flag = Boolean.TRUE;

    @ApiModelProperty("物流费用")
    @NotEmpty(message = "物流费用不能为空")
    @JsonProperty(value = "logistics_outlay")
    private Long logisticsOutlay;

    @ApiModelProperty(value = "调拨移库损益模式（1，我方发起 2，wms方发起）")
    @JsonProperty(value = "pattern_type")
    private Integer patternType;

    @ApiModelProperty(value = "调拨移库损益模式")
    @JsonProperty(value = "pattern_name")
    private String patternName;

    @ApiModelProperty(value = "创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

//    @ApiModelProperty(value = "创建人名称")
//    @JsonProperty("create_by_name")
//    private String createByName;

    @ApiModelProperty(value = "更新人编码")
    @JsonProperty("update_by_id")
    private String updateById;

//    @ApiModelProperty(value = "更新人名称")
//    @JsonProperty("update_by_name")
//    private String updateByName;

    @ApiModelProperty(value="出库时间")
    @JsonProperty("out_stock_time")
    private Date outStockTime;

    @ApiModelProperty(value="入库时间")
    @JsonProperty("in_stock_time")
    private Date inStockTime;

    /** 以下是dl回调需要用的字段*/

    @ApiModelProperty(value = "商品表")
    @JsonProperty("detail_list")
    private List<AllocationProduct> detailList;

    @ApiModelProperty(value = "商品批次表")
    @JsonProperty("detail_batch_list")
    private List<AllocationProductBatch> detailBatchList;
}