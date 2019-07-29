package com.aiqin.bms.scmp.api.product.domain.request.scrap;

import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationProductReqVo;
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
@ApiModel("报废单请求添加实体")
public class ScrapReqVo {

    @ApiModelProperty("仓库(物流中心)编号")
    @NotEmpty(message = "仓库编号不能为空")
    private String logisticsCenterCode;

    @ApiModelProperty("仓库(物流中心)名称")
    @NotEmpty(message = "仓库名称不能为空")
    private String logisticsCenterName;

    @ApiModelProperty("库房编码")
    @NotEmpty(message = "库房编码不能为空")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @NotEmpty(message = "库房名称不能为空")
    private String warehouseName;

    @ApiModelProperty("采购组编码")
    @NotEmpty(message = "采购组编号不能为空")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    @NotEmpty(message = "采购组名称不能为空")
    private String purchaseGroupName;

    @ApiModelProperty("负责人")
    @NotEmpty(message = "负责人不能为空")
    private String principal;

    @ApiModelProperty("数量")
    @NotNull(message = "数量不能为空")
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

    @ApiModelProperty(value = "调拨类型(1:调拨 2:移库 3:报废)" ,hidden = true)
    private Byte allocationType;

    @ApiModelProperty(value = "调拨类型(1:调拨 2:移库 3:报废)", hidden = true)
    private String allocationTypeName;

    @ApiModelProperty("sku列表")
    @Valid
    List<AllocationProductReqVo> list;

}
