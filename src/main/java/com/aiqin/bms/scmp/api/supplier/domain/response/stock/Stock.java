package com.aiqin.bms.scmp.api.supplier.domain.response.stock;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("库存实体Model")
public class Stock extends CommonBean {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

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

    @ApiModelProperty("仓库code")
    @JsonProperty(value = "warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty(value = "warehouse_name")
    private String warehouseName;

    @ApiModelProperty("仓库类型")
    @JsonProperty(value = "warehouse_type")
    private String warehouseType;

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("包装code")
    @JsonProperty(value = "sku_package_code")
    private String skuPackageCode;

    @ApiModelProperty("包装")
    @JsonProperty(value = "sku_package_name")
    private String skuPackageName;

    @ApiModelProperty("进货状态")
    @JsonProperty(value = "purchase_status")
    private Integer purchaseStatus;

    @ApiModelProperty("销售状态")
    @JsonProperty(value = "sale_status")
    private Integer saleStatus;

    @ApiModelProperty("正品库存数")
    @JsonProperty(value = "authentic_num")
    private Long authenticNum;

    @ApiModelProperty("可用库存数")
    @JsonProperty(value = "available_num")
    private Long availableNum;

    @ApiModelProperty("锁定库存数")
    @JsonProperty(value = "lock_num")
    private Long lockNum;

    @ApiModelProperty("备品库存数")
    @JsonProperty(value = "spare_num")
    private Long spareNum;

    @ApiModelProperty("库存数")
    @JsonProperty(value = "inventory_num")
    private Long inventoryNum;

    @ApiModelProperty("在途数")
    @JsonProperty(value = "way_num")
    private Long wayNum;

    @ApiModelProperty("采购在途数")
    @JsonProperty(value = "purchase_way_num")
    private Long purchaseWayNum;

    @ApiModelProperty("库存含税金额")
    @JsonProperty(value = "tax_price")
    private Long taxPrice;

    @ApiModelProperty("最新供货单位")
    @JsonProperty(value = "new_delivery")
    private String newDelivery;

    @ApiModelProperty("最新采购价")
    @JsonProperty(value = "new_purchase_price")
    private Long newPurchasePrice;

    @ApiModelProperty("删除标记，0未删除 1已删除")
    @JsonProperty(value = "del_flag")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    @JsonProperty(value = "create_time")
    private Date createTime;

    @ApiModelProperty("创建人")
    @JsonProperty(value = "create_by")
    private String createBy;

    @ApiModelProperty("修改时间")
    @JsonProperty(value = "update_time")
    private Date updateTime;

    @ApiModelProperty("修改人")
    @JsonProperty(value = "update_by")
    private String updateBy;

    @ApiModelProperty("品类编码")
    @JsonProperty(value = "product_category_id")
    private String productCategoryId;

    @ApiModelProperty("品类名称")
    @JsonProperty(value = "product_category_name")
    private String productCategoryName;

    @ApiModelProperty("规格编码")
    @JsonProperty(value = "spec_code")
    private String specCode;

    @ApiModelProperty("规格名称")
    @JsonProperty(value = "spec")
    private String spec;

    @ApiModelProperty("单位编码")
    @JsonProperty(value = "unit_code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @JsonProperty(value = "unit_name")
    private String unitName;

    @ApiModelProperty("品牌编码")
    @JsonProperty(value = "brand_code")
    private String brandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty(value = "brand_name")
    private String brandName;

    @ApiModelProperty("属性编码")
    @JsonProperty(value = "property_code")
    private String propertyCode;

    @ApiModelProperty("属性名称")
    @JsonProperty(value = "property_name")
    private String propertyName;

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

    @ApiModelProperty("sku类型编码")
    @JsonProperty(value = "type_code")
    private String typeCode;

    @ApiModelProperty("sku类型名称")
    @JsonProperty(value = "type_name")
    private String typeName;

    @ApiModelProperty("sku类别编码")
    @JsonProperty(value = "sort_code")
    private String sortCode;

    @ApiModelProperty("sku类别名称")
    @JsonProperty(value = "sort_name")
    private String sortName;

    @ApiModelProperty("sku型号编码")
    @JsonProperty(value = "model_number_code")
    private String modelNumberCode;

    @ApiModelProperty("sku型号名称")
    @JsonProperty(value = "model_number_name")
    private String modelNumberName;

    @ApiModelProperty("sku颜色编码")
    @JsonProperty(value = "color_code")
    private String colorCode;

    @ApiModelProperty("sku颜色名称")
    @JsonProperty(value = "color_name")
    private String colorName;

    @ApiModelProperty("调拨正品在途数")
    @JsonProperty(value = "allocate_product_way_num")
    private Long allocateProductWayNum;

    @ApiModelProperty("调拨备品在途数")
    @JsonProperty(value = "allocate_spare_way_num")
    private Long allocateSpareWayNum;

    @ApiModelProperty("正品含税金额")
    @JsonProperty(value = "product_tax_price")
    private Long productTaxPrice;

    @ApiModelProperty("备品含税金额")
    @JsonProperty(value = "spare_tax_price")
    private Long spareTaxPrice;

    @ApiModelProperty("备品含税金额")
    @JsonProperty(value = "tax_rate")
    private Long taxRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getTransportCenterCode() {
        return transportCenterCode;
    }

    public void setTransportCenterCode(String transportCenterCode) {
        this.transportCenterCode = transportCenterCode == null ? null : transportCenterCode.trim();
    }

    public String getTransportCenterName() {
        return transportCenterName;
    }

    public void setTransportCenterName(String transportCenterName) {
        this.transportCenterName = transportCenterName == null ? null : transportCenterName.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType == null ? null : warehouseType.trim();
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public String getSkuPackageCode() {
        return skuPackageCode;
    }

    public void setSkuPackageCode(String skuPackageCode) {
        this.skuPackageCode = skuPackageCode == null ? null : skuPackageCode.trim();
    }

    public String getSkuPackageName() {
        return skuPackageName;
    }

    public void setSkuPackageName(String skuPackageName) {
        this.skuPackageName = skuPackageName == null ? null : skuPackageName.trim();
    }

    public Integer getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(Integer purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public Integer getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Integer saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Long getAuthenticNum() {
        return authenticNum;
    }

    public void setAuthenticNum(Long authenticNum) {
        this.authenticNum = authenticNum;
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

    public Long getSpareNum() {
        return spareNum;
    }

    public void setSpareNum(Long spareNum) {
        this.spareNum = spareNum;
    }

    public Long getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(Long inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public Long getWayNum() {
        return wayNum;
    }

    public void setWayNum(Long wayNum) {
        this.wayNum = wayNum;
    }

    public Long getPurchaseWayNum() {
        return purchaseWayNum;
    }

    public void setPurchaseWayNum(Long purchaseWayNum) {
        this.purchaseWayNum = purchaseWayNum;
    }

    public Long getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Long taxPrice) {
        this.taxPrice = taxPrice;
    }

    public String getNewDelivery() {
        return newDelivery;
    }

    public void setNewDelivery(String newDelivery) {
        this.newDelivery = newDelivery == null ? null : newDelivery.trim();
    }

    public Long getNewPurchasePrice() {
        return newPurchasePrice;
    }

    public void setNewPurchasePrice(Long newPurchasePrice) {
        this.newPurchasePrice = newPurchasePrice;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId == null ? null : productCategoryId.trim();
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName == null ? null : productCategoryName.trim();
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode == null ? null : specCode.trim();
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode == null ? null : unitCode.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getModelNumberCode() {
        return modelNumberCode;
    }

    public void setModelNumberCode(String modelNumberCode) {
        this.modelNumberCode = modelNumberCode;
    }

    public String getModelNumberName() {
        return modelNumberName;
    }

    public void setModelNumberName(String modelNumberName) {
        this.modelNumberName = modelNumberName;
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

    public Long getAllocateProductWayNum() {
        return allocateProductWayNum;
    }

    public void setAllocateProductWayNum(Long allocateProductWayNum) {
        this.allocateProductWayNum = allocateProductWayNum;
    }

    public Long getAllocateSpareWayNum() {
        return allocateSpareWayNum;
    }

    public void setAllocateSpareWayNum(Long allocateSpareWayNum) {
        this.allocateSpareWayNum = allocateSpareWayNum;
    }

    public Long getProductTaxPrice() {
        return productTaxPrice;
    }

    public void setProductTaxPrice(Long productTaxPrice) {
        this.productTaxPrice = productTaxPrice;
    }

    public Long getSpareTaxPrice() {
        return spareTaxPrice;
    }

    public void setSpareTaxPrice(Long spareTaxPrice) {
        this.spareTaxPrice = spareTaxPrice;
    }

    public Long getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Long taxRate) {
        this.taxRate = taxRate;
    }
}