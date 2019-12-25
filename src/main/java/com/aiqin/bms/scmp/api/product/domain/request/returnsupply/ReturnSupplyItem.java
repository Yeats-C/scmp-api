package com.aiqin.bms.scmp.api.product.domain.request.returnsupply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel("退供单商品表")
public class ReturnSupplyItem {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("退供主表编码")
    private String returnSupplyCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("商品code")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品类别code")
    private String productCategoryCode;

    @ApiModelProperty("商品类别名称")
    private String productCategoryName;

    @ApiModelProperty("商品类型名称")
    private String productTypeName;

    @ApiModelProperty("商品类型编码")
    private String productTypeCode;

    @ApiModelProperty("商品品牌编码")
    private String productBrandCode;

    @ApiModelProperty("商品品牌名称")
    private String productBrandName;

    @ApiModelProperty("单位编码")
    private String unitCode;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("颜色编码")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("商品类别编码")
    private String productSortCode;

    @ApiModelProperty("商品类别名字")
    private String productSortName;

    @ApiModelProperty("税率")
    private BigDecimal taxRate;

    @ApiModelProperty("含税单价")
    private BigDecimal price;

    @ApiModelProperty("退供数量(库存)")
    private Long num;

    @ApiModelProperty("退供含税总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("实际退供数量")
    private Long actualNum;

    @ApiModelProperty("实际退供含税总价")
    private Long actualTotalPrice;

    @ApiModelProperty("规格编码")
    private String specCode;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("销售单位编码")
    private String saleUnitCode;

    @ApiModelProperty("销售单位名称")
    private String saleUnitName;

    @ApiModelProperty("不含税单价")
    private BigDecimal noTaxPrice;

    @ApiModelProperty("不含税总价")
    private BigDecimal noTaxTotalPrice;

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSaleUnitCode() {
        return saleUnitCode;
    }

    public void setSaleUnitCode(String saleUnitCode) {
        this.saleUnitCode = saleUnitCode;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public BigDecimal getNoTaxPrice() {
        return noTaxPrice;
    }

    public void setNoTaxPrice(BigDecimal noTaxPrice) {
        this.noTaxPrice = noTaxPrice;
    }

    public BigDecimal getNoTaxTotalPrice() {
        return noTaxTotalPrice;
    }

    public void setNoTaxTotalPrice(BigDecimal noTaxTotalPrice) {
        this.noTaxTotalPrice = noTaxTotalPrice;
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReturnSupplyCode() {
        return returnSupplyCode;
    }

    public void setReturnSupplyCode(String returnSupplyCode) {
        this.returnSupplyCode = returnSupplyCode == null ? null : returnSupplyCode.trim();
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductCategoryCode() {
        return productCategoryCode;
    }

    public void setProductCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode == null ? null : productCategoryCode.trim();
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName == null ? null : productCategoryName.trim();
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName == null ? null : productTypeName.trim();
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode == null ? null : productTypeCode.trim();
    }

    public String getProductBrandCode() {
        return productBrandCode;
    }

    public void setProductBrandCode(String productBrandCode) {
        this.productBrandCode = productBrandCode == null ? null : productBrandCode.trim();
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName == null ? null : productBrandName.trim();
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

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode == null ? null : colorCode.trim();
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName == null ? null : colorName.trim();
    }

    public String getProductSortCode() {
        return productSortCode;
    }

    public void setProductSortCode(String productSortCode) {
        this.productSortCode = productSortCode == null ? null : productSortCode.trim();
    }

    public String getProductSortName() {
        return productSortName;
    }

    public void setProductSortName(String productSortName) {
        this.productSortName = productSortName == null ? null : productSortName.trim();
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getActualNum() {
        return actualNum;
    }

    public void setActualNum(Long actualNum) {
        this.actualNum = actualNum;
    }

    public Long getActualTotalPrice() {
        return actualTotalPrice;
    }

    public void setActualTotalPrice(Long actualTotalPrice) {
        this.actualTotalPrice = actualTotalPrice;
    }
}