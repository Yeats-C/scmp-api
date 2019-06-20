package com.aiqin.bms.scmp.api.product.domain.response.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StockRespVO {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("库存编码")
    @JsonProperty(value = "stock_code")
    private String stockCode;

    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("物流中心编码")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("物流中心名称")
    @JsonProperty(value = "transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房code")
    @JsonProperty(value = "warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty(value = "warehouse_name")
    private String warehouseName;

    @ApiModelProperty("库房类型")
    @JsonProperty(value = "warehouse_type")
    private String warehouseType;

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("库存数")
    @JsonProperty(value = "inventory_num")
    private Long inventoryNum;

    @ApiModelProperty("可用库存数")
    @JsonProperty(value = "available_num")
    private Long availableNum;

    @ApiModelProperty("锁定库存数")
    @JsonProperty(value = "lock_num")
    private Long lockNum;

    @ApiModelProperty("采购在途数")
    @JsonProperty(value = "purchase_way_num")
    private Long purchaseWayNum;

    @ApiModelProperty("调拨在途数")
    @JsonProperty(value = "allocation_way_num")
    private Long allocationWayNum;

    @ApiModelProperty("总在途数")
    @JsonProperty(value = "total_way_num")
    private Long totalWayNum;

    @ApiModelProperty("最新供货单位")
    @JsonProperty(value = "new_delivery")
    private String newDelivery;

    @ApiModelProperty("最新供货单位名称")
    @JsonProperty(value = "new_delivery_name")
    private String newDeliveryName;

    @ApiModelProperty("最新采购价")
    @JsonProperty(value = "new_purchase_price")
    private Long newPurchasePrice;

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

    @ApiModelProperty("库存含税金额")
    @JsonProperty(value = "tax_price")
    private Long taxPrice;

    @ApiModelProperty("备品含税金额")
    @JsonProperty(value = "tax_rate")
    private Long taxRate;

    @ApiModelProperty("状态")
    @JsonProperty(value = "config_status")
    private Integer configStatus;

    @ApiModelProperty("规格")
    @JsonProperty(value = "spec")
    private String spec;

    @ApiModelProperty("单位")
    @JsonProperty(value = "unit_code")
    private String unitCode;

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

    @ApiModelProperty("销售库存数")
    @JsonProperty(value = "sale_num")
    private Long saleNum;

    @ApiModelProperty("销售锁定库存数")
    @JsonProperty(value = "sale_lock_num")
    private Long saleLockNum;

    @ApiModelProperty("销售在途数")
    @JsonProperty(value = "sale_way_num")
    private Long saleWayNum;

    @ApiModelProperty("销售采购在途数")
    @JsonProperty(value = "sale_purchase_way_num")
    private Long salePurchaseWayNum;

    @ApiModelProperty("赠品库存数")
    @JsonProperty(value = "gift_num")
    private Long giftNum;

    @ApiModelProperty("赠品锁定库存数")
    @JsonProperty(value = "gift_lock_num")
    private Long giftLockNum;

    @ApiModelProperty("赠品采购在途数")
    @JsonProperty(value = "gift_way_num")
    private Long giftWayNum;

    @ApiModelProperty("赠品采购在途数")
    @JsonProperty(value = "gift_purchase_way_num")
    private Long giftPurchaseWayNum;

    @ApiModelProperty("特卖库存数")
    @JsonProperty(value = "special_sale_num")
    private Long specialSaleNum;

    @ApiModelProperty("特卖锁定库存数")
    @JsonProperty(value = "special_sale_lock_num")
    private Long specialSaleLockNum;

    @ApiModelProperty("特卖在途数")
    @JsonProperty(value = "special_sale_way_num")
    private Long specialSaleWayNum;

    @ApiModelProperty("残品库存数")
    @JsonProperty(value = "bad_num")
    private Long badNum;

    @ApiModelProperty("残品锁定库存数")
    @JsonProperty(value = "bad_lock_num")
    private Long badLockNum;

    @ApiModelProperty("残品在途数")
    @JsonProperty(value = "bad_way_num")
    private Long badWayNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTransportCenterCode() {
        return transportCenterCode;
    }

    public void setTransportCenterCode(String transportCenterCode) {
        this.transportCenterCode = transportCenterCode;
    }

    public String getTransportCenterName() {
        return transportCenterName;
    }

    public void setTransportCenterName(String transportCenterName) {
        this.transportCenterName = transportCenterName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(Long inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public Long getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(Long availableNum) {
        this.availableNum = availableNum;
    }

    public Long getLockNum() {
        return lockNum;
    }

    public void setLockNum(Long lockNum) {
        this.lockNum = lockNum;
    }

    public Long getPurchaseWayNum() {
        return purchaseWayNum;
    }

    public void setPurchaseWayNum(Long purchaseWayNum) {
        this.purchaseWayNum = purchaseWayNum;
    }

    public Long getAllocationWayNum() {
        return allocationWayNum;
    }

    public void setAllocationWayNum(Long allocationWayNum) {
        this.allocationWayNum = allocationWayNum;
    }

    public Long getTotalWayNum() {
        return totalWayNum;
    }

    public void setTotalWayNum(Long totalWayNum) {
        this.totalWayNum = totalWayNum;
    }

    public String getNewDelivery() {
        return newDelivery;
    }

    public void setNewDelivery(String newDelivery) {
        this.newDelivery = newDelivery;
    }

    public String getNewDeliveryName() {
        return newDeliveryName;
    }

    public void setNewDeliveryName(String newDeliveryName) {
        this.newDeliveryName = newDeliveryName;
    }

    public Long getNewPurchasePrice() {
        return newPurchasePrice;
    }

    public void setNewPurchasePrice(Long newPurchasePrice) {
        this.newPurchasePrice = newPurchasePrice;
    }

    public String getPurchaseGroupCode() {
        return purchaseGroupCode;
    }

    public void setPurchaseGroupCode(String purchaseGroupCode) {
        this.purchaseGroupCode = purchaseGroupCode;
    }

    public String getPurchaseGroupName() {
        return purchaseGroupName;
    }

    public void setPurchaseGroupName(String purchaseGroupName) {
        this.purchaseGroupName = purchaseGroupName;
    }

    public String getStockUnitCode() {
        return stockUnitCode;
    }

    public void setStockUnitCode(String stockUnitCode) {
        this.stockUnitCode = stockUnitCode;
    }

    public String getStockUnitName() {
        return stockUnitName;
    }

    public void setStockUnitName(String stockUnitName) {
        this.stockUnitName = stockUnitName;
    }

    public Long getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Long taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Long getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Long taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(Integer configStatus) {
        this.configStatus = configStatus;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getProductCategoryCode() {
        return productCategoryCode;
    }

    public void setProductCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getLargeUnit() {
        return largeUnit;
    }

    public void setLargeUnit(String largeUnit) {
        this.largeUnit = largeUnit;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
    }

    public Long getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Long saleNum) {
        this.saleNum = saleNum;
    }

    public Long getSaleLockNum() {
        return saleLockNum;
    }

    public void setSaleLockNum(Long saleLockNum) {
        this.saleLockNum = saleLockNum;
    }

    public Long getSaleWayNum() {
        return saleWayNum;
    }

    public void setSaleWayNum(Long saleWayNum) {
        this.saleWayNum = saleWayNum;
    }

    public Long getSalePurchaseWayNum() {
        return salePurchaseWayNum;
    }

    public void setSalePurchaseWayNum(Long salePurchaseWayNum) {
        this.salePurchaseWayNum = salePurchaseWayNum;
    }

    public Long getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Long giftNum) {
        this.giftNum = giftNum;
    }

    public Long getGiftLockNum() {
        return giftLockNum;
    }

    public void setGiftLockNum(Long giftLockNum) {
        this.giftLockNum = giftLockNum;
    }

    public Long getGiftWayNum() {
        return giftWayNum;
    }

    public void setGiftWayNum(Long giftWayNum) {
        this.giftWayNum = giftWayNum;
    }

    public Long getGiftPurchaseWayNum() {
        return giftPurchaseWayNum;
    }

    public void setGiftPurchaseWayNum(Long giftPurchaseWayNum) {
        this.giftPurchaseWayNum = giftPurchaseWayNum;
    }

    public Long getSpecialSaleNum() {
        return specialSaleNum;
    }

    public void setSpecialSaleNum(Long specialSaleNum) {
        this.specialSaleNum = specialSaleNum;
    }

    public Long getSpecialSaleLockNum() {
        return specialSaleLockNum;
    }

    public void setSpecialSaleLockNum(Long specialSaleLockNum) {
        this.specialSaleLockNum = specialSaleLockNum;
    }

    public Long getSpecialSaleWayNum() {
        return specialSaleWayNum;
    }

    public void setSpecialSaleWayNum(Long specialSaleWayNum) {
        this.specialSaleWayNum = specialSaleWayNum;
    }

    public Long getBadNum() {
        return badNum;
    }

    public void setBadNum(Long badNum) {
        this.badNum = badNum;
    }

    public Long getBadLockNum() {
        return badLockNum;
    }

    public void setBadLockNum(Long badLockNum) {
        this.badLockNum = badLockNum;
    }

    public Long getBadWayNum() {
        return badWayNum;
    }

    public void setBadWayNum(Long badWayNum) {
        this.badWayNum = badWayNum;
    }

}
