package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @NotEmpty(message = "sku 名称不能为空")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("品类(名称)")
    @JsonProperty("product_category_name")
    private String category;

    @ApiModelProperty("品牌(名称)")
    @JsonProperty("product_brand_name")
    private String brand;

    @ApiModelProperty("颜色(名称)")
    @JsonProperty("color_name")
    private String color;

    @ApiModelProperty("规格(名称)")
    @JsonProperty("spec")
    private String specification;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String model;

    @ApiModelProperty("单位(销售单位)")
    @JsonProperty("unit_name")
    private String unit;

    @ApiModelProperty("类别")
    @JsonProperty("classes")
    private String classes;

    @ApiModelProperty("类型")
    @JsonProperty("type")
    private String type;

    @ApiModelProperty(value = "库存单位",hidden = true)
    @JsonProperty("inventory_unit")
    private String inventoryUnit;

    @ApiModelProperty(value = "库存",hidden = true)
    @JsonProperty("inventory")
    private Long inventory;

    @ApiModelProperty("税率")
    @NotNull(message = "税率不能为空")
    @JsonProperty("tax_rate")
    private BigDecimal tax;

    @ApiModelProperty("含税成本")
    @NotNull(message = "含税成本不能为空")
    @JsonProperty("tax_cost")
    private BigDecimal taxPrice;

    @ApiModelProperty("数量")
    @NotNull(message = "数量不能为空")
    @JsonProperty("quantity")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    @NotNull(message = "含税总成本不能为空")
    @JsonProperty("tax_amount")
    private BigDecimal taxAmount;

    @ApiModelProperty(value = "图片地址",hidden = true)
    @JsonProperty("picture_url")
    private String pictureUrl;

    @ApiModelProperty("行号")
    @JsonProperty("line_num")
    private Long lineNum;

    @ApiModelProperty("调出批次号")
    @JsonProperty("call_out_batch_number")
    private String callOutBatchNumber;

    @ApiModelProperty("调入批次号")
    @JsonProperty("call_in_batch_number")
    private String callInBatchNumber;

    @ApiModelProperty("批次备注")
    @JsonProperty("batch_number_remark")
    private String batchNumberRemark;

    @ApiModelProperty("生产日期")
    @JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty("供应商code")
    @JsonProperty(value = "supplier_code")
    private String supplierCode;
}
