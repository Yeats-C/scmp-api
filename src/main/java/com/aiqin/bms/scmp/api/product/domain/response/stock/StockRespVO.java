package com.aiqin.bms.scmp.api.product.domain.response.stock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StockRespVO {

    @ApiModelProperty("库存编码")
    @JsonProperty(value = "stock_code")
    private String stockCode;

    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("仓库编码")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty(value = "transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty(value = "warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty(value = "warehouse_name")
    private String warehouseName;

    @ApiModelProperty("库房类型 1.销售库 2.特卖库 3.残品库 4.监管库")
    @JsonProperty(value = "warehouse_type")
    private Integer warehouseType;

    @ApiModelProperty("sku编码")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("库存数")
    @JsonProperty(value = "inventory_count")
    private Long inventoryCount;

    @ApiModelProperty("可用库存数")
    @JsonProperty(value = "available_count")
    private Long availableCount;

    @ApiModelProperty("锁定库存数")
    @JsonProperty(value = "lock_count")
    private Long lockCount;

    @ApiModelProperty("采购在途数")
    @JsonProperty(value = "purchase_way_count")
    private Long purchaseWayCount;

    @ApiModelProperty("调拨在途数")
    @JsonProperty(value = "allocation_way_count")
    private Long allocationWayCount;

    @ApiModelProperty("总在途数")
    @JsonProperty(value = "total_way_count")
    private Long totalWayCount;

    @ApiModelProperty("最新供货单位")
    @JsonProperty(value = "new_delivery")
    private String newDelivery;

    @ApiModelProperty("最新供货单位名称")
    @JsonProperty(value = "new_delivery_name")
    private String newDeliveryName;

    @ApiModelProperty("最新采购价")
    @JsonProperty(value = "new_purchase_price")
    private BigDecimal newPurchasePrice;

    @ApiModelProperty("采购组编码")
    @JsonProperty(value = "purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    @JsonProperty(value = "purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty("库存单位编码")
    @JsonProperty(value = "stock_unit_code")
    private String stockUnitCode;

    @ApiModelProperty("库存单位名称")
    @JsonProperty(value = "stock_unit_name")
    private String stockUnitName;

    @ApiModelProperty("库存含税成本")
    @JsonProperty(value = "tax_price")
    private BigDecimal taxPrice;

    @ApiModelProperty("状态")
    @JsonProperty(value = "config_status")
    private Integer configStatus;

    @ApiModelProperty("规格")
    @JsonProperty(value = "spec")
    private String spec;

    @ApiModelProperty("颜色code")
    @JsonProperty(value = "color_code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    @JsonProperty(value = "color_name")
    private String colorName;

    @ApiModelProperty("型号")
    @JsonProperty(value = "model_number")
    private String modelNumber;

    @ApiModelProperty("单位")
    @JsonProperty(value = "unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty(value = "unit_name")
    private String unitName;

    @ApiModelProperty("基商品含量")
    @JsonProperty(value = "base_product_content")
    private String baseProductContent;

    @ApiModelProperty("品类编码")
    @JsonProperty(value = "product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty(value = "product_category_name")
    private String productCategoryName;

    @ApiModelProperty("包装")
    @JsonProperty(value = "large_unit")
    private String largeUnit;

    @ApiModelProperty("品牌")
    @JsonProperty(value = "product_brand_name")
    private String productBrandName;

    @ApiModelProperty("品牌编码")
    @JsonProperty(value = "product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("销售库存数")
    @JsonProperty(value = "sale_count")
    private Long saleCount;

    @ApiModelProperty("销售锁定库存数")
    @JsonProperty(value = "sale_lock_count")
    private Long saleLockCount;

    @ApiModelProperty("销售在途数")
    @JsonProperty(value = "sale_way_count")
    private Long saleWayCount;

    @ApiModelProperty("销售采购在途数")
    @JsonProperty(value = "sale_purchase_way_count")
    private Long salePurchaseWayCount;

    @ApiModelProperty("销售含税总成本")
    @JsonProperty(value = "sale_total_amount")
    private BigDecimal saleTotalAmount;

    @ApiModelProperty("特卖库存数")
    @JsonProperty(value = "special_sale_count")
    private Long specialSaleCount;

    @ApiModelProperty("特卖锁定库存数")
    @JsonProperty(value = "special_sale_lock_count")
    private Long specialSaleLockCount;

    @ApiModelProperty("特卖在途数")
    @JsonProperty(value = "special_sale_way_count")
    private Long specialSaleWayCount;

    @ApiModelProperty("特卖含税总成本")
    @JsonProperty(value = "special_sale_total_amount")
    private BigDecimal specialSaleTotalAmount;

    @ApiModelProperty("残品含税总成本")
    @JsonProperty(value = "bad_total_amount")
    private BigDecimal badTotalAmount;

    @ApiModelProperty("残品库存数")
    @JsonProperty(value = "bad_count")
    private Long badCount;

    @ApiModelProperty("残品锁定库存数")
    @JsonProperty(value = "bad_lock_count")
    private Long badLockCount;

    @ApiModelProperty("残品在途数")
    @JsonProperty(value = "bad_way_count")
    private Long badWayCount;

    @ApiModelProperty("昨天含税成本")
    @JsonProperty(value = "tax_cost")
    private BigDecimal taxCost;

    @ApiModelProperty("含税总成本")
    @JsonProperty(value = "total_tax_cost")
    private BigDecimal totalTaxCost;

    @ApiModelProperty("未税成本")
    @JsonProperty(value = "no_tax_cost")
    private BigDecimal noTaxCost;

    @ApiModelProperty("税率")
    @JsonProperty(value = "tax_rate")
    private BigDecimal taxRate;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    @JsonProperty(value = "create_time")
    private Date createTime;

    @ApiModelProperty("创建人")
    @JsonProperty(value = "create_by_name")
    private String createByName;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    @JsonProperty(value = "update_time")
    private Date updateTime;

    @ApiModelProperty("更新人")
    @JsonProperty(value = "update_by_name")
    private String updateByName;

    @ApiModelProperty("仓库库存总计")
    @JsonProperty(value = "sum_list")
    private StockSumResponse sumList;

    @ApiModelProperty("品类级别1")
    @JsonProperty(value = "category_one")
    private String categoryOne;

    @ApiModelProperty("品类级别2")
    @JsonProperty(value = "category_two")
    private String categoryTwo;

    @ApiModelProperty("品类级别3")
    @JsonProperty(value = "category_three")
    private String categoryThree;

    @ApiModelProperty("品类级别4")
    @JsonProperty(value = "category_four")
    private String categoryFour;

}
