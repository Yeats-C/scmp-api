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

    @ApiModelProperty("调出物流中心编号")
    @NotEmpty(message = "调出物流中心编号不能为空")
    private String calloutLogisticsCenterCode;

    @ApiModelProperty("调出物流中心名称")
    @NotEmpty(message = "调出物流中心名称不能为空")
    private String calloutLogisticsCenterName;

    @ApiModelProperty("调出仓库编码")
    @NotEmpty(message = "调出仓库编码不能为空")
    private String calloutWarehouseCode;

    @ApiModelProperty("调出仓库名称")
    @NotEmpty(message = "调出仓库名称不能为空")
    private String calloutWarehouseName;

    @ApiModelProperty("仓库类型编码")
    @NotEmpty(message = "仓库类型编码不能为空")
    private String inventoryAttributesCode;

    @ApiModelProperty("仓库类型名称")
    @NotEmpty(message = "仓库类型名称不能为空")
    private String inventoryAttributesName;

    @ApiModelProperty("采购组编码")
    @NotEmpty(message = "采购组编号不能为空")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    @NotEmpty(message = "采购组名称不能为空")
    private String purchaseGroupName;

    @ApiModelProperty("调入物流中心编码")
    @NotEmpty(message = "入物流中心编号不能为空")
    private String callinLogisticsCenterCode;

    @ApiModelProperty("调入物流中心名称")
    @NotEmpty(message = "调入物流中心名称不能为空")
    private String callinLogisticsCenterName;

    @ApiModelProperty("调入仓库编码")
    @NotEmpty(message = "调入仓库编号不能为空")
    private String callinWarehouseCode;

    @ApiModelProperty("调入仓库名称")
    @NotEmpty(message = "调入仓库名称不能为空")
    private String callinWarehouseName;

    @ApiModelProperty("调拨类型编码")
    @NotNull(message = "调拨类型编码不能为空")
    private Byte allocationTypeCode;

    @ApiModelProperty("调拨类型名称")
    @NotEmpty(message = "调拨类型编码不能为空")
    private String allocationTypeName;

    @ApiModelProperty("负责人")
    @NotEmpty(message = "负责人不能为空")
    private String principal;

    @ApiModelProperty("数量")
    @NotNull(message = "数量不能为空")
    private Long quantity;

    @ApiModelProperty("含税调拨金额")
    @NotNull(message = "数量不能为空")
    private Long taxRefundAmount;

    @ApiModelProperty("出库单号")
//    @NotEmpty(message = "出库单号不能为空")
    private String outboundOderCode;

    @ApiModelProperty("入库单号")
//    @NotEmpty(message = "入库单号不能为空")
    private String inboundOderCode;


    @ApiModelProperty("sku列表")
    @Valid
    List<AllocationProductReqVo> list;

    @ApiModelProperty("审批流程id")
    private String formNo;

    @ApiModelProperty("状态编码")
    private Byte allocationStatusCode;

    @ApiModelProperty("状态名称")
    private String allocationStatusName;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;
}
