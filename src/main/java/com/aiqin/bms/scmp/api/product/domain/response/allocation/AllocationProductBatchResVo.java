package com.aiqin.bms.scmp.api.product.domain.response.allocation;

/**
 * 描述:订购单sku返回详情
 *
 * @Author: Kt.w
 * @Date: 2019/1/10
 * @Version 1.0
 * @since 1.0
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("sku批次返回详情")
public class AllocationProductBatchResVo {

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("品类")
    private String category;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("出库数量")
    private Long callOutQuantity;

    @ApiModelProperty("入库数量")
    private Long  callInQuantity;

    @ApiModelProperty("调出批次号")
    private String batchNumber;

    @ApiModelProperty("批次备注")
    private String batchNumberRemark;

    @ApiModelProperty("生产日期")
    private String productDate;

    @ApiModelProperty("调入批次号")
    private String callInBatchNumber;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("行号")
    private Integer lineNum;

}
