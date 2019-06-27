package com.aiqin.bms.scmp.api.product.domain.response.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
public class StockBatchRespVO {


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
    @JsonProperty(value = "company_code")
    private String companyName;

    @ApiModelProperty("仓编码(物流中心编码)")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓名称(物流中心名称)")
    @JsonProperty(value = "transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
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

    @ApiModelProperty("单位code")
    @JsonProperty(value = "unit_code")
    private String unitCode;

    @ApiModelProperty("单位code")
    @JsonProperty(value = "unit_name")
    private String unitName;

    @ApiModelProperty("包装")
    @JsonProperty(value = "pack")
    private String pack;

    @ApiModelProperty("状态(进货销售的)")
    @JsonProperty(value = "config_status")
    private Integer configStatus;

    @ApiModelProperty("库存数")
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

    @ApiModelProperty("最新供货单位")
    @JsonProperty(value = "new_delivery")
    private String newDelivery;

    @ApiModelProperty("最新供货单位名称")
    @JsonProperty(value = "new_delivery_name")
    private String newDeliveryName;

    @ApiModelProperty("采购价")
    @JsonProperty(value = "purchase_price")
    private Long purchasePrice;

    // 详情表数据
    @ApiModelProperty("流水编码")
    @JsonProperty(value = "flow_batch_code")
    private String flowBatchCode;

    @ApiModelProperty("订单编码")
    @JsonProperty(value = "order_code")
    private String orderCode;

    @ApiModelProperty("订单类型")
    @JsonProperty(value = "order_type")
    private Integer orderType;

    @ApiModelProperty("订单来源")
    @JsonProperty(value = "order_source")
    private String orderSource;

    @ApiModelProperty("状态(锁状态-后补)")
    @JsonProperty(value = "lock_status")
    private Long lockStatus;

    @ApiModelProperty("变动数(修改数)")
    @JsonProperty(value = "change_num")
    private Long changeNum;

    @ApiModelProperty("单据类型")
    @JsonProperty(value = "document_type")
    private String documentType;

    @ApiModelProperty("单据号")
    @JsonProperty(value = "document_num")
    private Long documentNum;

    @ApiModelProperty("来源单据类型")
    @JsonProperty(value = "source_document_type")
    private String sourceDocumentType;

    @ApiModelProperty("来源单据号")
    @JsonProperty(value = "source_document_num")
    private Long sourceDocumentNum;

    @ApiModelProperty("操作时间")
    @JsonProperty(value = "operating_time")
    private String operatingTime;

    @ApiModelProperty("操作人")
    @JsonProperty(value = "operating_by")
    private String operatingBy;

    @ApiModelProperty("批次单商品备注")
    @JsonProperty(value = "remark")
    private String remark;

    @ApiModelProperty("修改前总库存数")
    @JsonProperty(value = "before_inventory_num")
    private Long beforeInventoryNum;

    @ApiModelProperty("修改后总库存数")
    @JsonProperty(value = "after_inventory_num")
    private Long afterInventoryNum;

    @ApiModelProperty("修改前可用库存数")
    @JsonProperty(value = "before_available_num")
    private Long beforeAvailableNum;

    @ApiModelProperty("修改后可用库存数")
    @JsonProperty(value = "after_available_num")
    private Long afterAvailableNum;

    @ApiModelProperty("修改前锁定库存数")
    @JsonProperty(value = "before_lock_num")
    private Long beforeLockNum;

    @ApiModelProperty("修改后锁定库存数")
    @JsonProperty(value = "after_lock_num")
    private Long afterLockNum;

    @ApiModelProperty("操作类型")
    @JsonProperty(value = "operation_type")
    private Integer operationType;

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

    public Integer getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(Integer configStatus) {
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

    public String getFlowBatchCode() {
        return flowBatchCode;
    }

    public void setFlowBatchCode(String flowBatchCode) {
        this.flowBatchCode = flowBatchCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public Long getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Long lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Long getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(Long changeNum) {
        this.changeNum = changeNum;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Long getDocumentNum() {
        return documentNum;
    }

    public void setDocumentNum(Long documentNum) {
        this.documentNum = documentNum;
    }

    public String getSourceDocumentType() {
        return sourceDocumentType;
    }

    public void setSourceDocumentType(String sourceDocumentType) {
        this.sourceDocumentType = sourceDocumentType;
    }

    public Long getSourceDocumentNum() {
        return sourceDocumentNum;
    }

    public void setSourceDocumentNum(Long sourceDocumentNum) {
        this.sourceDocumentNum = sourceDocumentNum;
    }

    public String getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(String operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getOperatingBy() {
        return operatingBy;
    }

    public void setOperatingBy(String operatingBy) {
        this.operatingBy = operatingBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getBeforeInventoryNum() {
        return beforeInventoryNum;
    }

    public void setBeforeInventoryNum(Long beforeInventoryNum) {
        this.beforeInventoryNum = beforeInventoryNum;
    }

    public Long getAfterInventoryNum() {
        return afterInventoryNum;
    }

    public void setAfterInventoryNum(Long afterInventoryNum) {
        this.afterInventoryNum = afterInventoryNum;
    }

    public Long getBeforeAvailableNum() {
        return beforeAvailableNum;
    }

    public void setBeforeAvailableNum(Long beforeAvailableNum) {
        this.beforeAvailableNum = beforeAvailableNum;
    }

    public Long getAfterAvailableNum() {
        return afterAvailableNum;
    }

    public void setAfterAvailableNum(Long afterAvailableNum) {
        this.afterAvailableNum = afterAvailableNum;
    }

    public Long getBeforeLockNum() {
        return beforeLockNum;
    }

    public void setBeforeLockNum(Long beforeLockNum) {
        this.beforeLockNum = beforeLockNum;
    }

    public Long getAfterLockNum() {
        return afterLockNum;
    }

    public void setAfterLockNum(Long afterLockNum) {
        this.afterLockNum = afterLockNum;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }
}
