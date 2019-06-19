package com.aiqin.bms.scmp.api.product.domain.request.allocation;

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
    @NotEmpty(message = "调出仓库(物流中心)编号不能为空")
    private String callOutLogisticsCenterCode;

    @ApiModelProperty("调出仓库(物流中心)名称")
    @NotEmpty(message = "调出仓库(物流中心)名称不能为空")
    private String callOutLogisticsCenterName;

    @ApiModelProperty("调出库房编码")
    @NotEmpty(message = "调出库房编码不能为空")
    private String callOutWarehouseCode;

    @ApiModelProperty("调出库房名称")
    @NotEmpty(message = "调出库房名称不能为空")
    private String callOutWarehouseName;

    @ApiModelProperty("采购组编码")
    @NotEmpty(message = "采购组编号不能为空")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    @NotEmpty(message = "采购组名称不能为空")
    private String purchaseGroupName;

    @ApiModelProperty("调入仓库(物流中心)编码")
    @NotEmpty(message = "调入仓库(物流中心)编号不能为空")
    private String callInLogisticsCenterCode;

    @ApiModelProperty("调入仓库(物流中心)名称")
    @NotEmpty(message = "调入仓库(物流中心)名称不能为空")
    private String callInLogisticsCenterName;

    @ApiModelProperty("调入库房编码")
    @NotEmpty(message = "调入库房编号不能为空")
    private String callInWarehouseCode;

    @ApiModelProperty("调入库房名称")
    @NotEmpty(message = "调入库房名称不能为空")
    private String callInWarehouseName;

    @ApiModelProperty("负责人")
    @NotEmpty(message = "负责人不能为空")
    private String principal;

    @ApiModelProperty("数量")
    @NotNull(message = "数量不能为空")
    private Long quantity;

    @ApiModelProperty("含税总成本")
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
