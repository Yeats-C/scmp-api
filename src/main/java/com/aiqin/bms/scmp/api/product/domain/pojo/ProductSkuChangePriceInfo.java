package com.aiqin.bms.scmp.api.product.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("变价附表")
public class ProductSkuChangePriceInfo {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("主表编码")
    private String code;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("最新采购价")
    private Long purchasePriceNewest;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("是否默认(0否1是)")
    private Integer beDefault;

    @ApiModelProperty("原含税采购价")
    private Long purchasePriceOld;

    @ApiModelProperty("开始生效时间")
    private Date effectiveTimeStart;

    @ApiModelProperty("结束生效时间")
    private Date effectiveTimeEnd;

    @ApiModelProperty("新的含税采购价")
    private Long purchasePriceNew;

    @ApiModelProperty("原含税价")
    private Long oldPrice;

    @ApiModelProperty("新含税价")
    private Long newPrice;

    @ApiModelProperty("仓库批次号编码")
    private String warehouseBatchNumber;

    @ApiModelProperty("仓库批次号名称")
    private String warehouseBatchName;

    @ApiModelProperty("调价原因编码")
    private String changePriceReasonCode;

    @ApiModelProperty("调价原因描述")
    private String changePriceReasonName;

    @ApiModelProperty("临时含税价")
    private Long temporaryPrice;

    @ApiModelProperty("价格项目编码")
    private String priceItemCode;

    @ApiModelProperty("价格项目名称")
    private String priceItemName;

    @ApiModelProperty("价格类型")
    private String priceTypeCode;

    @ApiModelProperty("价格类型名称")
    private String priceTypeName;

    @ApiModelProperty("价格属性编码")
    private String priceAttributeCode;

    @ApiModelProperty("价格数据名称")
    private String priceAttributeName;

    @ApiModelProperty("公司名称")
    private String companyCode;

    @ApiModelProperty("公司编码")
    private String companyName;

    @ApiModelProperty("仓库编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("批次备注")
    private String batchRemark;

    @ApiModelProperty("正式编码")
    private String officialCode;

    @ApiModelProperty("生产日期")
    private Date productTime;

    @ApiModelProperty("是否同步(0否1是)")
    private Integer beSynchronize;

    @ApiModelProperty("原毛利率")
    private Long oldGrossProfitMargin;

    @ApiModelProperty("现毛利率")
    private Long newGrossProfitMargin;

    @ApiModelProperty("进项税率")
    private Long inTax;

    @ApiModelProperty("销项税率")
    private Long outTax;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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

    public Long getPurchasePriceNewest() {
        return purchasePriceNewest;
    }

    public void setPurchasePriceNewest(Long purchasePriceNewest) {
        this.purchasePriceNewest = purchasePriceNewest;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public Integer getBeDefault() {
        return beDefault;
    }

    public void setBeDefault(Integer beDefault) {
        this.beDefault = beDefault;
    }

    public Long getPurchasePriceOld() {
        return purchasePriceOld;
    }

    public void setPurchasePriceOld(Long purchasePriceOld) {
        this.purchasePriceOld = purchasePriceOld;
    }

    public Date getEffectiveTimeStart() {
        return effectiveTimeStart;
    }

    public void setEffectiveTimeStart(Date effectiveTimeStart) {
        this.effectiveTimeStart = effectiveTimeStart;
    }

    public Date getEffectiveTimeEnd() {
        return effectiveTimeEnd;
    }

    public void setEffectiveTimeEnd(Date effectiveTimeEnd) {
        this.effectiveTimeEnd = effectiveTimeEnd;
    }

    public Long getPurchasePriceNew() {
        return purchasePriceNew;
    }

    public void setPurchasePriceNew(Long purchasePriceNew) {
        this.purchasePriceNew = purchasePriceNew;
    }

    public Long getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Long oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Long getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Long newPrice) {
        this.newPrice = newPrice;
    }

    public String getWarehouseBatchNumber() {
        return warehouseBatchNumber;
    }

    public void setWarehouseBatchNumber(String warehouseBatchNumber) {
        this.warehouseBatchNumber = warehouseBatchNumber == null ? null : warehouseBatchNumber.trim();
    }

    public String getWarehouseBatchName() {
        return warehouseBatchName;
    }

    public void setWarehouseBatchName(String warehouseBatchName) {
        this.warehouseBatchName = warehouseBatchName == null ? null : warehouseBatchName.trim();
    }

    public String getChangePriceReasonCode() {
        return changePriceReasonCode;
    }

    public void setChangePriceReasonCode(String changePriceReasonCode) {
        this.changePriceReasonCode = changePriceReasonCode == null ? null : changePriceReasonCode.trim();
    }

    public String getChangePriceReasonName() {
        return changePriceReasonName;
    }

    public void setChangePriceReasonName(String changePriceReasonName) {
        this.changePriceReasonName = changePriceReasonName == null ? null : changePriceReasonName.trim();
    }

    public Long getTemporaryPrice() {
        return temporaryPrice;
    }

    public void setTemporaryPrice(Long temporaryPrice) {
        this.temporaryPrice = temporaryPrice;
    }

    public String getPriceItemCode() {
        return priceItemCode;
    }

    public void setPriceItemCode(String priceItemCode) {
        this.priceItemCode = priceItemCode == null ? null : priceItemCode.trim();
    }

    public String getPriceItemName() {
        return priceItemName;
    }

    public void setPriceItemName(String priceItemName) {
        this.priceItemName = priceItemName == null ? null : priceItemName.trim();
    }

    public String getPriceTypeCode() {
        return priceTypeCode;
    }

    public void setPriceTypeCode(String priceTypeCode) {
        this.priceTypeCode = priceTypeCode == null ? null : priceTypeCode.trim();
    }

    public String getPriceTypeName() {
        return priceTypeName;
    }

    public void setPriceTypeName(String priceTypeName) {
        this.priceTypeName = priceTypeName == null ? null : priceTypeName.trim();
    }

    public String getPriceAttributeCode() {
        return priceAttributeCode;
    }

    public void setPriceAttributeCode(String priceAttributeCode) {
        this.priceAttributeCode = priceAttributeCode == null ? null : priceAttributeCode.trim();
    }

    public String getPriceAttributeName() {
        return priceAttributeName;
    }

    public void setPriceAttributeName(String priceAttributeName) {
        this.priceAttributeName = priceAttributeName == null ? null : priceAttributeName.trim();
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

    public String getBatchRemark() {
        return batchRemark;
    }

    public void setBatchRemark(String batchRemark) {
        this.batchRemark = batchRemark == null ? null : batchRemark.trim();
    }

    public String getOfficialCode() {
        return officialCode;
    }

    public void setOfficialCode(String officialCode) {
        this.officialCode = officialCode == null ? null : officialCode.trim();
    }
    public Date getProductTime() {
        return productTime;
    }

    public void setProductTime(Date productTime) {
        this.productTime = productTime;
    }

    public Integer getBeSynchronize() {
        return beSynchronize;
    }

    public void setBeSynchronize(Integer beSynchronize) {
        this.beSynchronize = beSynchronize;
    }

    public Long getOldGrossProfitMargin() {
        return oldGrossProfitMargin;
    }

    public void setOldGrossProfitMargin(Long oldGrossProfitMargin) {
        this.oldGrossProfitMargin = oldGrossProfitMargin;
    }

    public Long getNewGrossProfitMargin() {
        return newGrossProfitMargin;
    }

    public void setNewGrossProfitMargin(Long newGrossProfitMargin) {
        this.newGrossProfitMargin = newGrossProfitMargin;
    }

    public Long getInTax() {
        return inTax;
    }

    public void setInTax(Long inTax) {
        this.inTax = inTax;
    }

    public Long getOutTax() {
        return outTax;
    }

    public void setOutTax(Long outTax) {
        this.outTax = outTax;
    }
}