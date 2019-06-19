package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("库存request")
@Data
public class StockRequest extends PagesRequest {
    @ApiModelProperty("物流中心CodeOrName")
    @JsonProperty(value = "transport_center_text")
    private String transportCenterText;

    @ApiModelProperty("仓库CodeOrName")
    @JsonProperty(value = "warehouse_text")
    private String warehouseText;

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("状态")
    @JsonProperty(value = "config_status")
    private Integer configStatus;

    @ApiModelProperty("品类编码CodeOrName")
    @JsonProperty(value = "product_category_text")
    private String productCategoryText;

    @ApiModelProperty("品牌编码CodeOrName")
    @JsonProperty(value = "brand_text")
    private String brandText;

    @ApiModelProperty("属性编码CodeOrName")
    @JsonProperty(value = "property_text")
    private String propertyText;

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

    @ApiModelProperty("销售库存数begin")
    @JsonProperty(value = "sale_begin_num")
    private Long saleBeginNum;

    @ApiModelProperty("销售库存数finish")
    @JsonProperty(value = "sale_finish_num")
    private Long saleFinishNum;

    @ApiModelProperty("特卖库存数begin")
    @JsonProperty(value = "special_sale_begin_num")
    private Long specialSaleBeginNum;

    @ApiModelProperty("特卖库存数finish")
    @JsonProperty(value = "special_sale_finish_num")
    private Long specialSaleFinishNum;

    @ApiModelProperty("残品库存数begin")
    @JsonProperty(value = "bad_begin_num")
    private Long badBeginNum;

    @ApiModelProperty("残品库存数finish")
    @JsonProperty(value = "bad_finish_num")
    private Long badFinishNum;

    @ApiModelProperty("公司编码：备用")
    @JsonProperty(value = "company_code")
    private Long companyCode;

    @ApiModelProperty("在途数begin")
    @JsonProperty(value = "total_way_begin_num")
    private Long totalWayBeginNum;

    @ApiModelProperty("在途数finish")
    @JsonProperty(value = "total_way_finish_num")
    private Long totalWayFinishNum;

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

    public Long getSaleBeginNum() {
        return saleBeginNum;
    }

    public void setSaleBeginNum(Long saleBeginNum) {
        this.saleBeginNum = saleBeginNum;
    }

    public Long getSaleFinishNum() {
        return saleFinishNum;
    }

    public void setSaleFinishNum(Long saleFinishNum) {
        this.saleFinishNum = saleFinishNum;
    }

    public Long getSpecialSaleBeginNum() {
        return specialSaleBeginNum;
    }

    public void setSpecialSaleBeginNum(Long specialSaleBeginNum) {
        this.specialSaleBeginNum = specialSaleBeginNum;
    }

    public Long getSpecialSaleFinishNum() {
        return specialSaleFinishNum;
    }

    public void setSpecialSaleFinishNum(Long specialSaleFinishNum) {
        this.specialSaleFinishNum = specialSaleFinishNum;
    }

    public Long getBadBeginNum() {
        return badBeginNum;
    }

    public void setBadBeginNum(Long badBeginNum) {
        this.badBeginNum = badBeginNum;
    }

    public Long getBadFinishNum() {
        return badFinishNum;
    }

    public void setBadFinishNum(Long badFinishNum) {
        this.badFinishNum = badFinishNum;
    }

    public Long getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(Long companyCode) {
        this.companyCode = companyCode;
    }

    public Long getTotalWayBeginNum() {
        return totalWayBeginNum;
    }

    public void setTotalWayBeginNum(Long totalWayBeginNum) {
        this.totalWayBeginNum = totalWayBeginNum;
    }

    public Long getTotalWayFinishNum() {
        return totalWayFinishNum;
    }

    public void setTotalWayFinishNum(Long totalWayFinishNum) {
        this.totalWayFinishNum = totalWayFinishNum;
    }
}