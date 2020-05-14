package com.aiqin.bms.scmp.api.product.domain.request.movement;

import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationProductReqVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Classname: MovementReqVo
 * 描述:移库单请求保存实体
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
@ApiModel("移库单请求保存实体")
@Data
public class MovementReqVo {

    @ApiModelProperty("所属仓库编码")
    @NotEmpty(message = "所属仓库编码不能为空")
    @JsonProperty("logistics_center_code")
    private String logisticsCenterCode;

    @ApiModelProperty("所属仓库名称")
    @NotEmpty(message = "所属仓库名称不能为空")
    @JsonProperty("logistics_center_name")
    private String logisticsCenterName;

    @ApiModelProperty("调出库房编码")
    @NotEmpty(message = "调出库房编码不能为空")
    @JsonProperty("call_out_warehouse_code")
    private String callOutWarehouseCode;

    @ApiModelProperty("调出库房名称")
    @NotEmpty(message = "调出库房名称不能为空")
    @JsonProperty("call_out_warehouse_name")
    private String callOutWarehouseName;

    @ApiModelProperty("调入库房编码")
    @NotEmpty(message = "调入库房编码不能为空")
    @JsonProperty("call_in_warehouse_code")
    private String callInWarehouseCode;

    @ApiModelProperty("调入库房名称")
    @NotEmpty(message = "调入库房名称不能为空")
    @JsonProperty("call_in_warehouse_name")
    private String callInWarehouseName;

   // @ApiModelProperty("采购组编码")
   // @NotEmpty(message = "采购组编号不能为空")
   // private String purchaseGroupCode;

   // @ApiModelProperty("采购组名称")
   // @NotEmpty(message = "采购组名称不能为空")
   // private String purchaseGroupName;

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

    @ApiModelProperty("备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty("审批名称")
    @JsonProperty("approval_name")
    private String approvalName;

    @ApiModelProperty("职位编码")
    @JsonProperty("position_code")
    private String positionCode;

    @ApiModelProperty("职位名称")
    @JsonProperty("position_name")
    private String positionName;

    @ApiModelProperty("sku列表")
    @Valid
    @JsonProperty("list")
    List<AllocationProductReqVo> list;
}
