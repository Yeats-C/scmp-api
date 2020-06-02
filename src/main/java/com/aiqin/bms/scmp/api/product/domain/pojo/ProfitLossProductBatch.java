package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("损益批次商品管理")
@Data
public class ProfitLossProductBatch {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value = "库位号")
    @JsonProperty("location_code")
    private String locationCode;

    @ApiModelProperty(value = "行号")
    @JsonProperty("line_code")
    private Long lineCode;

    @ApiModelProperty(value = "SKU编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value = "最小单位数量")
    @JsonProperty("total_count")
    private Long totalCount;

    @ApiModelProperty(value = "生产日期")
    @JsonProperty("product_date")
    private String productDate;

    @ApiModelProperty(value = "批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty(value = "批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty("供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("批次编码")
    @JsonProperty("batch_info_code")
    private String batchInfoCode;

    @ApiModelProperty(value = "过期日期")
    @JsonProperty("be_overdue_date")
    private String beOverdueDate;

    @ApiModelProperty(value = "创建人id")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value = "创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value = "修改人id")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value = "修改人名称")
    @JsonProperty("update_by_name")
    private String updateByName;


//    @ApiModelProperty("仓库编号")
//    @JsonProperty("logistics_center_code")
//    private String logisticsCenterCode;
//
//    @ApiModelProperty("仓库名称")
//    @JsonProperty("logistics_center_name")
//    private String logisticsCenterName;
//
//    @ApiModelProperty("库房编号")
//    @JsonProperty("warehouse_code")
//    private String warehouseCode;
//
//    @ApiModelProperty("库房名称")
//    @JsonProperty("warehouse_name")
//    private String warehouseName;
//
//    @ApiModelProperty("调拨单编码")
//    @JsonProperty("order_code")
//    private String orderCode;
//
//    @ApiModelProperty("sku编号")
//    @JsonProperty("sku_code")
//    private String skuCode;
//
//    @ApiModelProperty("sku名称")
//    @JsonProperty("sku_name")
//    private String skuName;
//
//    @ApiModelProperty("品类")
//    @JsonProperty("category")
//    private String category;
//
//    @ApiModelProperty("品牌")
//    @JsonProperty("brand")
//    private String brand;
//
//    @ApiModelProperty("颜色")
//    @JsonProperty("color")
//    private String color;
//
//    @ApiModelProperty("规格")
//    @JsonProperty("specification")
//    private String specification;
//
//    @ApiModelProperty("型号")
//    @JsonProperty("model")
//    private String model;
//
//    @ApiModelProperty("单位(销售单位)")
//    @JsonProperty("unit")
//    private String unit;
//
//    @ApiModelProperty("类别")
//    @JsonProperty("classes")
//    private String classes;
//
//    @ApiModelProperty("类型")
//    @JsonProperty("type")
//    private String type;
//
//    @ApiModelProperty("库存单位")
//    @JsonProperty("inventory_unit")
//    private String inventoryUnit;
//
//    @ApiModelProperty("库存")
//    @JsonProperty("inventory")
//    private Long inventory;
//
//    @ApiModelProperty("税率")
//    @JsonProperty("tax")
//    private BigDecimal tax;
//
//    @ApiModelProperty("含税成本")
//    @JsonProperty("tax_price")
//    private BigDecimal taxPrice;
//
//    @ApiModelProperty("数量")
//    @JsonProperty("quantity")
//    private Long quantity;
//
//    @ApiModelProperty("含税总成本")
//    @JsonProperty("tax_amount")
//    private BigDecimal taxAmount;
//
//    @ApiModelProperty("图片地址")
//    @JsonProperty("picture_url")
//    private String pictureUrl;
//
//    @ApiModelProperty("行号")
//    @JsonProperty("line_num")
//    private Long lineNum;
//
//    @ApiModelProperty("批次号")
//    @JsonProperty("batch_number")
//    private String batchNumber;
//
//    @ApiModelProperty("批次备注")
//    @JsonProperty("batch_remark")
//    private String batchRemark;
//
//    @ApiModelProperty("生产日期")
//    @JsonProperty("product_date")
//    private String productDate;
//
//    @ApiModelProperty("供应商名称")
//    @JsonProperty("supplier_name")
//    private String supplierName;
//
//    @ApiModelProperty("供应商code")
//    @JsonProperty("supplier_code")
//    private String supplierCode;
//
//    @ApiModelProperty(value="创建者")
//    @JsonProperty("create_by_name")
//    private String createByName;

}