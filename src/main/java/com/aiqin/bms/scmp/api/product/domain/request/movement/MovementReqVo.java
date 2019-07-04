package com.aiqin.bms.scmp.api.product.domain.request.movement;

import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationProductReqVo;
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
    private String logisticsCenterCode;

    @ApiModelProperty("所属仓库名称")
    private String logisticsCenterName;

    @ApiModelProperty("调出库房编码")
    private String callOutWarehouseCode;

    @ApiModelProperty("调出库房名称")
    private String callOutWarehouseName;

    @ApiModelProperty("调入库房编码")
    private String callInWarehouseCode;

    @ApiModelProperty("调入库房名称")
    private String callInWarehouseName;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("负责人")
    private String principal;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税总成本单位分(传入时需要乘以100)")
    @NotNull(message = "含税总成本")
    private Long totalCostRate;

    @ApiModelProperty("直属上级编码")
    @NotEmpty(message = "直属上级编码不能为空！")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    @NotEmpty(message = "直属上级名称不能为空！")
    private String directSupervisorName;

    @ApiModelProperty("承担单位编码")
    @NotEmpty(message = "承担单位编码不能为空！")
    private String undertakingUnitCode;

    @ApiModelProperty("承担单位名称")
    @NotEmpty(message = "承担单位名称不能为空！")
    private String undertakingUnitName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("sku列表")
    @Valid
    List<AllocationProductReqVo> list;
}
