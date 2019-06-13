package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel
public class RejectApplyRecordDetail {
    @ApiModelProperty(value="")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="退货申请单号")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value="申请单类型: 0 手动 1自动")
    @JsonProperty("apply_type")
    private Boolean applyType;

    @ApiModelProperty(value="采购组 code")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value="退供申请单状态: 0  已完成 1 待提交")
    @JsonProperty("apply_record_status")
    private Boolean applyRecordStatus;

    @ApiModelProperty(value="")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value="")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty(value="")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value="品类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value="")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value="品牌id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value="")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value="商品类型 0赠品 1商品 2实物返回")
    @JsonProperty("product_type")
    private Boolean productType;

    @ApiModelProperty(value="规格")
    @JsonProperty("product_spec")
    private String productSpec;

    @ApiModelProperty(value="单位")
    @JsonProperty("product_unit")
    private String productUnit;

    @ApiModelProperty(value="库存数量")
    @JsonProperty("stock_count")
    private Integer stockCount;

    @ApiModelProperty(value="数量")
    @JsonProperty("product_count")
    private Integer productCount;

    @ApiModelProperty(value="税率")
    @JsonProperty("tax_rate")
    private Integer taxRate;

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

    @ApiModelProperty(value="含税单价")
    @JsonProperty("product_amount")
    private Long productAmount;

    @ApiModelProperty(value="含税总价")
    @JsonProperty("product_total_amount")
    private Long productTotalAmount;

    @ApiModelProperty(value="")
    @JsonProperty("product_cost")
    private Long productCost;

    @ApiModelProperty(value="商品批次号")
    @JsonProperty("batch_no")
    private String batchNo;

    @ApiModelProperty(value="批次备注")
    @JsonProperty("batch_remark")
    private String batchRemark;

    @ApiModelProperty(value="批次备注")
    @JsonProperty("batch_create_time")
    private Date batchCreateTime;

    @ApiModelProperty(value="商品 结算方式")
    @JsonProperty("settlement_method")
    private String settlementMethod;

    @ApiModelProperty(value="供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="收货区域 :省")
    @JsonProperty("province_id")
    private String provinceId;

    @ApiModelProperty(value="")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value="市")
    @JsonProperty("city_id")
    private String cityId;

    @ApiModelProperty(value="")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value="县")
    @JsonProperty("district_id")
    private String districtId;

    @ApiModelProperty(value="")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty(value="收货地址")
    @JsonProperty("address")
    private String address;

    @ApiModelProperty(value="")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value="")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="")
    @JsonProperty("update_time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRejectApplyRecordCode() {
        return rejectApplyRecordCode;
    }

    public void setRejectApplyRecordCode(String rejectApplyRecordCode) {
        this.rejectApplyRecordCode = rejectApplyRecordCode;
    }

    public Boolean getApplyType() {
        return applyType;
    }

    public void setApplyType(Boolean applyType) {
        this.applyType = applyType;
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

    public Boolean getApplyRecordStatus() {
        return applyRecordStatus;
    }

    public void setApplyRecordStatus(Boolean applyRecordStatus) {
        this.applyRecordStatus = applyRecordStatus;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Boolean getProductType() {
        return productType;
    }

    public void setProductType(Boolean productType) {
        this.productType = productType;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Integer taxRate) {
        this.taxRate = taxRate;
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

    public Long getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Long productAmount) {
        this.productAmount = productAmount;
    }

    public Long getProductTotalAmount() {
        return productTotalAmount;
    }

    public void setProductTotalAmount(Long productTotalAmount) {
        this.productTotalAmount = productTotalAmount;
    }

    public Long getProductCost() {
        return productCost;
    }

    public void setProductCost(Long productCost) {
        this.productCost = productCost;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBatchRemark() {
        return batchRemark;
    }

    public void setBatchRemark(String batchRemark) {
        this.batchRemark = batchRemark;
    }

    public Date getBatchCreateTime() {
        return batchCreateTime;
    }

    public void setBatchCreateTime(Date batchCreateTime) {
        this.batchCreateTime = batchCreateTime;
    }

    public String getSettlementMethod() {
        return settlementMethod;
    }

    public void setSettlementMethod(String settlementMethod) {
        this.settlementMethod = settlementMethod;
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

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateById() {
        return createById;
    }

    public void setCreateById(String createById) {
        this.createById = createById;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getUpdateById() {
        return updateById;
    }

    public void setUpdateById(String updateById) {
        this.updateById = updateById;
    }

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}