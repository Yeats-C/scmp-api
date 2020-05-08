package com.aiqin.bms.scmp.api.product.domain.response.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhao shuai
 * @create: 2020-03-27
 **/
@Data
public class StockBatchDetailResponse {

    @ApiModelProperty(value="批次库存详情")
    @JsonProperty("stock_batch_response")
    private StockBatchResponse stockBatchResponse;

    @ApiModelProperty(value="sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="品牌")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty(value="品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty(value="品类")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty(value="品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty(value="商品属性")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty(value="商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty(value="规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value="颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty(value="型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty(value="单位")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty(value="包装")
    @JsonProperty("box_gauge")
    private String boxGauge;

    @ApiModelProperty(value="状态 0:再用 1:停止进货 2:停止配送 3:停止销售")
    @JsonProperty("config_status")
    private Integer configStatus;

    @ApiModelProperty(value="仓编码(物流中心编码)")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓名称(物流中心名称)")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="库房类型")
    @JsonProperty("warehouse_type")
    private String warehouseType;

    @ApiModelProperty(value="总库存数")
    @JsonProperty("inventory_count")
    private Long inventoryCount;

    @ApiModelProperty(value="可用库存数")
    @JsonProperty("available_count")
    private Long availableCount;

    @ApiModelProperty(value="锁定库存数")
    @JsonProperty("lock_count")
    private Long lockCount;

    @ApiModelProperty(value="调拨在途数")
    @JsonProperty("allocation_way_count")
    private Long allocationWayCount;

    @ApiModelProperty(value="采购在途数")
    @JsonProperty("purchase_way_count")
    private Long purchaseWayCount;

    @ApiModelProperty(value="在途数")
    @JsonProperty("total_way_count")
    private Long totalWayCount;

    @ApiModelProperty(value="含税成本")
    @JsonProperty("tax_cost")
    private BigDecimal taxCost;

    @ApiModelProperty(value="未税成本")
    @JsonProperty("no_tax_cost")
    private BigDecimal noTaxCost;

    @ApiModelProperty(value="税率")
    @JsonProperty("tax_rate")
    private BigDecimal taxRate;

    @ApiModelProperty(value="含税总成本")
    @JsonProperty("total_tax_cost")
    private BigDecimal totalTaxCost;

    @ApiModelProperty(value="最新采购价")
    @JsonProperty("new_purchase_amount")
    private BigDecimal newPurchaseAmount;

    @ApiModelProperty(value="最新供应商")
    @JsonProperty("new_supplier_name")
    private String newSupplierName;
}
