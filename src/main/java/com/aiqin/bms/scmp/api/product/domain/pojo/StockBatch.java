package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("批次库存实体Model")
@Data
public class StockBatch extends PagesRequest {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("批次库存编码")
    @JsonProperty(value = "stock_batch_code")
    private String stockBatchCode;

    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;

    @ApiModelProperty("仓编码(物流中心编码)")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓名称(物流中心名称)")
    @JsonProperty(value = "transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty(value = "warehouse_name")
    private String warehouseName;

    @ApiModelProperty("库房名称")
    @JsonProperty(value = "warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房类型")
    @JsonProperty(value = "warehouse_type")
    private String warehouseType;

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("批次号")
    @JsonProperty(value = "batch_code")
    private String batchCode;

    @ApiModelProperty("生产日期")
    @JsonProperty(value = "production_date")
    private String productionDate;

    @ApiModelProperty("批次备注")
    @JsonProperty(value = "batch_remark")
    private String batchRemark;

    @ApiModelProperty("品类编码")
    @JsonProperty(value = "category_type_code")
    private String categoryTypeCode;

    @ApiModelProperty("品类名称")
    @JsonProperty(value = "category_type_name")
    private String categoryTypeName;

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

    @ApiModelProperty("单位编码")
    @JsonProperty(value = "unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty(value = "unit_name")
    private String unitName;

    @ApiModelProperty("包装")
    @JsonProperty(value = "pack")
    private String pack;

    @ApiModelProperty("状态(进货销售的)")
    @JsonProperty(value = "config_status")
    private Long configStatus;

    @ApiModelProperty("总库存数")
    @JsonProperty(value = "inventory_num")
    private Long inventoryNum;

    @ApiModelProperty("可用库存数")
    @JsonProperty(value = "available_num")
    private Long availableNum;

    @ApiModelProperty("锁定库存数")
    @JsonProperty(value = "lock_num")
    private Long lockNum;

    @ApiModelProperty("供应商code")
    @JsonProperty(value = "supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @JsonProperty(value = "supplier_name")
    private String supplierName;

    @ApiModelProperty("最新供货单位编码")
    @JsonProperty(value = "new_delivery")
    private String newDelivery;

    @ApiModelProperty("最新供货单位名称")
    @JsonProperty(value = "new_delivery_name")
    private String newDeliveryName;

    @ApiModelProperty("采购价")
    @JsonProperty(value = "purchase_price")
    private Long purchasePrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockBatchCode() {
        return stockBatchCode;
    }

    public void setStockBatchCode(String stockBatchCode) {
        this.stockBatchCode = stockBatchCode;
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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
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

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getBatchRemark() {
        return batchRemark;
    }

    public void setBatchRemark(String batchRemark) {
        this.batchRemark = batchRemark;
    }

    public String getCategoryTypeCode() {
        return categoryTypeCode;
    }

    public void setCategoryTypeCode(String categoryTypeCode) {
        this.categoryTypeCode = categoryTypeCode;
    }

    public String getCategoryTypeName() {
        return categoryTypeName;
    }

    public void setCategoryTypeName(String categoryTypeName) {
        this.categoryTypeName = categoryTypeName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public Long getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(Long configStatus) {
        this.configStatus = configStatus;
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

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public Long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}