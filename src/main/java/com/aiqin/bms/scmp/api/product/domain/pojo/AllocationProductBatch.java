package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("调拨商品批次表")
@Data
public class AllocationProductBatch extends CommonBean {
    @ApiModelProperty("主键")
    @JsonProperty(value = "id")
    private Long id;

    @ApiModelProperty("调拨单编码")
    @JsonProperty(value = "allocation_code")
    private String allocationCode;

    @ApiModelProperty("sku编码")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("品类")
    @JsonProperty(value = "category")
    private String category;

    @ApiModelProperty("品牌")
    @JsonProperty(value = "brand")
    private String brand;

    @ApiModelProperty("颜色")
    @JsonProperty(value = "color")
    private String color;

    @ApiModelProperty("规格")
    @JsonProperty(value = "specification")
    private String specification;

    @ApiModelProperty("型号")
    @JsonProperty(value = "model")
    private String model;

    @ApiModelProperty("单位(销售单位)")
    @JsonProperty(value = "unit")
    private String unit;

    @ApiModelProperty("类别")
    @JsonProperty(value = "classes")
    private String classes;

    @ApiModelProperty("类型")
    @JsonProperty(value = "type")
    private String type;

    @ApiModelProperty("库存单位")
    @JsonProperty(value = "inventory_unit")
    private String inventoryUnit;

    @ApiModelProperty("库存")
    @JsonProperty(value = "inventory")
    private Long inventory;

    @ApiModelProperty("税率")
    @JsonProperty(value = "tax_rate")
    private BigDecimal tax;

    @ApiModelProperty("含税成本")
    @JsonProperty(value = "tax_cost")
    private BigDecimal taxPrice;

    @ApiModelProperty("数量")
    @JsonProperty(value = "quantity")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    @JsonProperty(value = "tax_amount")
    private BigDecimal taxAmount;

    @ApiModelProperty("图片地址")
    @JsonProperty(value = "picture_url")
    private String pictureUrl;

    @ApiModelProperty("行号")
    @JsonProperty(value = "line_num")
    private Long lineNum;

    @ApiModelProperty("调出批次号")
    @JsonProperty(value = "call_out_batch_number")
    private String callOutBatchNumber;

    @ApiModelProperty("调入批次号")
    @JsonProperty(value = "call_in_batch_number")
    private String callInBatchNumber;

    @ApiModelProperty("批次备注")
    @JsonProperty(value = "batch_number_remark")
    private String batchNumberRemark;

    @ApiModelProperty("生产日期")
    @JsonProperty(value = "product_date")
    private String productDate;

    @ApiModelProperty("过期日期")
    @JsonProperty("be_overdue_date")
    private String beOverdueDate;

    @ApiModelProperty("供应商名称")
    @JsonProperty(value = "supplier_name")
    private String supplierName;

    @ApiModelProperty("供应商code")
    @JsonProperty(value = "supplier_code")
    private String supplierCode;

    @ApiModelProperty("调出批次编号")
    @JsonProperty(value = "call_out_batch_info_code")
    private String callOutBatchInfoCode;

    @ApiModelProperty("调入批次编号")
    @JsonProperty(value = "call_in_batch_info_code")
    private String callInBatchInfoCode;
}