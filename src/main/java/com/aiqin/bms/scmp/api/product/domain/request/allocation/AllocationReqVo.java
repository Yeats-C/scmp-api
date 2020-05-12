package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 描述:调拨单请求添加实体
 *
 * @Author: Kt.w
 * @Date: 2019/1/9
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("调拨单请求添加实体")
public class AllocationReqVo {

    @ApiModelProperty("调出仓库(物流中心)编号")
    @NotEmpty(message = "调出仓库编号不能为空")
    @JsonProperty("call_out_logistics_center_code")
    private String callOutLogisticsCenterCode;

    @ApiModelProperty("调出仓库(物流中心)名称")
    @NotEmpty(message = "调出仓库名称不能为空")
    @JsonProperty("call_out_logistics_center_name")
    private String callOutLogisticsCenterName;

    @ApiModelProperty("调出库房编码")
    @NotEmpty(message = "调出库房编码不能为空")
    @JsonProperty("call_out_warehouse_code")
    private String callOutWarehouseCode;

    @ApiModelProperty("调出库房名称")
    @NotEmpty(message = "调出库房名称不能为空")
    @JsonProperty("call_out_warehouse_name")
    private String callOutWarehouseName;

  //  @ApiModelProperty("采购组编码")
  //  @NotEmpty(message = "采购组编号不能为空")
  //  private String purchaseGroupCode;

  //  @ApiModelProperty("采购组名称")
  //  @NotEmpty(message = "采购组名称不能为空")
  //  private String purchaseGroupName;

    @ApiModelProperty("调入仓库(物流中心)编码")
    @NotEmpty(message = "调入仓库编号不能为空")
    @JsonProperty("call_in_logistics_center_code")
    private String callInLogisticsCenterCode;

    @ApiModelProperty("调入仓库(物流中心)名称")
    @NotEmpty(message = "调入仓库名称不能为空")
    @JsonProperty("call_in_logistics_center_name")
    private String callInLogisticsCenterName;

    @ApiModelProperty("调入库房编码")
    @NotEmpty(message = "调入库房编号不能为空")
    @JsonProperty("call_in_warehouse_code")
    private String callInWarehouseCode;

    @ApiModelProperty("调入库房名称")
    @NotEmpty(message = "调入库房名称不能为空")
    @JsonProperty("call_in_warehouse_name")
    private String callInWarehouseName;

    @ApiModelProperty("负责人")
    @NotEmpty(message = "负责人不能为空")
    @JsonProperty("principal")
    private String principal;

    @ApiModelProperty("数量")
    @NotNull(message = "数量不能为空")
    @JsonProperty("quantity")
    private Long quantity;

    @ApiModelProperty("含税总成本单位分(传入时需要乘以100)")
    @NotNull(message = "含税总成本不能为空")
    @JsonProperty("total_cost_rate")
    private Long totalCostRate;

    @ApiModelProperty("直属上级编码")
    @NotEmpty(message = "直属上级编码不能为空！")
    @JsonProperty("direct_supervisor_code")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    @NotEmpty(message = "直属上级名称不能为空！")
    @JsonProperty("direct_supervisor_name")
    private String directSupervisorName;

    @ApiModelProperty("承担单位编码")
    @JsonProperty("undertaking_unit_code")
    private String undertakingUnitCode;

    @ApiModelProperty("承担单位名称")
    @JsonProperty("undertaking_unit_name")
    private String undertakingUnitName;

    @ApiModelProperty("审批名称")
    @JsonProperty("approval_name")
    private String approvalName;

    @ApiModelProperty(value = "调拨移库损益模式（1，我方发起 2，wms方发起）")
    @JsonProperty("pattern_type")
    private Integer patternType;

    @ApiModelProperty(value = "调拨移库损益模式")
    @JsonProperty("pattern_name")
    private String patternName;

    @ApiModelProperty("备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value = "调拨类型(1:调拨 2:移库 3:报废)" ,hidden = true)
    @JsonProperty("allocation_type")
    private Byte allocationType;

    @ApiModelProperty(value = "调拨类型(1:调拨 2:移库 3:报废)", hidden = true)
    @JsonProperty("allocation_type_name")
    private String allocationTypeName;

    @ApiModelProperty("物流费用")
    //@NotEmpty(message = "物流费用不能为空")
    @JsonProperty("logistics_outlay")
    private Long logisticsOutlay;

    @ApiModelProperty("sku列表")
    @Valid
    @JsonProperty("list")
    List<AllocationProductReqVo> list;

}
