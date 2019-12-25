package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 描述:调拨sku接受实体
 *
 * @Author: Kt.w
 * @Date: 2019/1/9
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("sku接受实体")
public class AllocationProductReqVo {

    @ApiModelProperty("sku编号")
    @NotEmpty(message = "sku 编号不能为空")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @NotEmpty(message = "sku 名称不能为空")
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

    @ApiModelProperty("单位(销售单位)")
    private String unit;

    @ApiModelProperty("类别")
    private String classes;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty(value = "库存单位",hidden = true)
    private String inventoryUnit;

    @ApiModelProperty(value = "库存",hidden = true)
    private Long inventory;

    @ApiModelProperty("税率")
    @NotNull(message = "税率不能为空")
    private BigDecimal tax;

    @ApiModelProperty("含税成本")
    @NotNull(message = "含税成本不能为空")
    private BigDecimal taxPrice;

    @ApiModelProperty("数量")
    @NotNull(message = "数量不能为空")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    @NotNull(message = "含税总成本不能为空")
    private BigDecimal taxAmount;

    @ApiModelProperty(value = "图片地址",hidden = true)
    private String pictureUrl;

    @ApiModelProperty("行号")
    private Long lineNum;

    @ApiModelProperty("调出批次号")
    private String callOutBatchNumber;

    @ApiModelProperty("调入批次号")
    private String callInBatchNumber;

    @ApiModelProperty("批次备注")
    private String batchNumberRemark;

    @ApiModelProperty("生产日期")
    private String productDate;
}
