package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("批量库存request")
@Data
public class StockBatchRequest extends PagesRequest {
    @ApiModelProperty("物流中心CodeOrName")
    @JsonProperty(value = "transport_center_text")
    private String transportCenterText;

    @ApiModelProperty("仓库CodeOrName")
    @JsonProperty(value = "warehouse_text")
    private String warehouseText;

    @ApiModelProperty("库房类型")
    @JsonProperty(value = "warehouse_type")
    private String warehouseType;

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("状态")
    @JsonProperty(value = "config_status")
    private Integer configStatus;

    @ApiModelProperty("属性编码CodeOrName")
    @JsonProperty(value = "property_text")
    private String propertyText;

    @ApiModelProperty("品牌编码CodeOrName")
    @JsonProperty(value = "brand_text")
    private String brandText;

    @ApiModelProperty("品类编码CodeOrName")
    @JsonProperty(value = "product_category_text")
    private String productCategoryText;

    @ApiModelProperty("库存数begin")
    @JsonProperty(value = "inventory_begin_num")
    private Long inventoryBeginNum;

    @ApiModelProperty("库存数finish")
    @JsonProperty(value = "inventory_finish_num")
    private Long inventoryFinishNum;

    @ApiModelProperty("可用库存数begin")
    @JsonProperty(value = "available_begin_num")
    private Long availableBeginNum;

    @ApiModelProperty("可用库存数finish")
    @JsonProperty(value = "available_finish_num")
    private Long availableFinishNum;

    @ApiModelProperty("批次号")
    @JsonProperty(value = "batch_code")
    private String batchCode;

    @ApiModelProperty("生产日期")
    @JsonProperty(value = "production_date")
    private String productionDate;

    public String getTransportCenterText() {
        return transportCenterText;
    }

    public void setTransportCenterText(String transportCenterText) {
        this.transportCenterText = transportCenterText;
    }

    public String getWarehouseText() {
        return warehouseText;
    }

    public void setWarehouseText(String warehouseText) {
        this.warehouseText = warehouseText;
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

    public Integer getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(Integer configStatus) {
        this.configStatus = configStatus;
    }

    public String getProductCategoryText() {
        return productCategoryText;
    }

    public void setProductCategoryText(String productCategoryText) {
        this.productCategoryText = productCategoryText;
    }

    public String getBrandText() {
        return brandText;
    }

    public void setBrandText(String brandText) {
        this.brandText = brandText;
    }

    public String getPropertyText() {
        return propertyText;
    }

    public void setPropertyText(String propertyText) {
        this.propertyText = propertyText;
    }

    public Long getInventoryBeginNum() {
        return inventoryBeginNum;
    }

    public void setInventoryBeginNum(Long inventoryBeginNum) {
        this.inventoryBeginNum = inventoryBeginNum;
    }

    public Long getInventoryFinishNum() {
        return inventoryFinishNum;
    }

    public void setInventoryFinishNum(Long inventoryFinishNum) {
        this.inventoryFinishNum = inventoryFinishNum;
    }

    public Long getAvailableBeginNum() {
        return availableBeginNum;
    }

    public void setAvailableBeginNum(Long availableBeginNum) {
        this.availableBeginNum = availableBeginNum;
    }

    public Long getAvailableFinishNum() {
        return availableFinishNum;
    }

    public void setAvailableFinishNum(Long availableFinishNum) {
        this.availableFinishNum = availableFinishNum;
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
}