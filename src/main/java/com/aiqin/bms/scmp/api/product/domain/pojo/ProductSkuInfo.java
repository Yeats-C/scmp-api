package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("")
public class ProductSkuInfo extends CommonBean {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("所属商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品品类code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("单位code")
    private String unitCode;

    @ApiModelProperty("sku状态，0为启用，1为禁用")
    private Byte skuStatus;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("商品属性code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性名称")
    private String productPropertyName;

    @ApiModelProperty("商品品牌code")
    private String productBrandCode;

    @ApiModelProperty("商品品牌")
    private String productBrandName;

    @ApiModelProperty("sku简称")
    private String skuAbbreviation;

    @ApiModelProperty("颜色code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("保质管理（0:管理 1:不管理）")
    private Byte qualityAssuranceManagement;

    @ApiModelProperty("商品类别code")
    private String productSortCode;

    @ApiModelProperty("商品类别名称")
    private String productSortName;

    @ApiModelProperty("保质数量")
    private String qualityNumber;

    @ApiModelProperty("保质日期")
    private String qualityDate;

    @ApiModelProperty("管理方式code")
    private String managementStyleCode;

    @ApiModelProperty("管理方式名称")
    private String managementStyleName;

    @ApiModelProperty("供货渠道类别code")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("是否上架，0为未上架，1为上架")
    private Byte onSale;

    @ApiModelProperty("供货渠道类别名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("箱规")
    private String boxGauge;

    @ApiModelProperty("适用起始月龄")
    private String applicableMonthAge;

    @ApiModelProperty("返点")
    private String point;

    @ApiModelProperty("条码")
    private String barCode;

    @ApiModelProperty("助记码")
    private String mnemonicCode;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效起始时间")
    private String selectionEffectiveStartTime;

    @ApiModelProperty("生效结束时间")
    private String selectionEffectiveEndTime;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("商品/赠品(0:商品，1:赠品)")
    private Byte goodsGifts;

    @ApiModelProperty("")
    private String spuCode;

    @ApiModelProperty("logo")
    private String logo;

    @ApiModelProperty("图片")
    private String images;

    @ApiModelProperty("封面图")
    private String itroImages;

    @ApiModelProperty("是否爱亲主推(0:否，1:是)")
    private Byte isMainPush;

    @ApiModelProperty("是否新品(0:否，1:是)")
    private Byte newProduct;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("厂家指导价")
    private Long manufacturerGuidePrice;

    @ApiModelProperty("拆零系数")
    private Long zeroRemovalCoefficient;

    @ApiModelProperty("采购组编码")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    private String procurementSectionName;

    @ApiModelProperty("申请状态(0:待审 1:审核中 2:审核通过 3:审核未通过 4:已撤销)")
    private Byte applyStatus;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("审批流程编码")
    private String formNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode == null ? null : unitCode.trim();
    }

    public Byte getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(Byte skuStatus) {
        this.skuStatus = skuStatus;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public String getProductPropertyCode() {
        return productPropertyCode;
    }

    public void setProductPropertyCode(String productPropertyCode) {
        this.productPropertyCode = productPropertyCode == null ? null : productPropertyCode.trim();
    }

    public String getProductPropertyName() {
        return productPropertyName;
    }

    public void setProductPropertyName(String productPropertyName) {
        this.productPropertyName = productPropertyName == null ? null : productPropertyName.trim();
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

    public String getSkuAbbreviation() {
        return skuAbbreviation;
    }

    public void setSkuAbbreviation(String skuAbbreviation) {
        this.skuAbbreviation = skuAbbreviation == null ? null : skuAbbreviation.trim();
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

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber == null ? null : modelNumber.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public Byte getQualityAssuranceManagement() {
        return qualityAssuranceManagement;
    }

    public void setQualityAssuranceManagement(Byte qualityAssuranceManagement) {
        this.qualityAssuranceManagement = qualityAssuranceManagement;
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

    public String getQualityNumber() {
        return qualityNumber;
    }

    public void setQualityNumber(String qualityNumber) {
        this.qualityNumber = qualityNumber == null ? null : qualityNumber.trim();
    }

    public String getQualityDate() {
        return qualityDate;
    }

    public void setQualityDate(String qualityDate) {
        this.qualityDate = qualityDate == null ? null : qualityDate.trim();
    }

    public String getManagementStyleCode() {
        return managementStyleCode;
    }

    public void setManagementStyleCode(String managementStyleCode) {
        this.managementStyleCode = managementStyleCode == null ? null : managementStyleCode.trim();
    }

    public String getManagementStyleName() {
        return managementStyleName;
    }

    public void setManagementStyleName(String managementStyleName) {
        this.managementStyleName = managementStyleName == null ? null : managementStyleName.trim();
    }

    public String getCategoriesSupplyChannelsCode() {
        return categoriesSupplyChannelsCode;
    }

    public void setCategoriesSupplyChannelsCode(String categoriesSupplyChannelsCode) {
        this.categoriesSupplyChannelsCode = categoriesSupplyChannelsCode == null ? null : categoriesSupplyChannelsCode.trim();
    }

    public Byte getOnSale() {
        return onSale;
    }

    public void setOnSale(Byte onSale) {
        this.onSale = onSale;
    }

    public String getCategoriesSupplyChannelsName() {
        return categoriesSupplyChannelsName;
    }

    public void setCategoriesSupplyChannelsName(String categoriesSupplyChannelsName) {
        this.categoriesSupplyChannelsName = categoriesSupplyChannelsName == null ? null : categoriesSupplyChannelsName.trim();
    }

    public String getBoxGauge() {
        return boxGauge;
    }

    public void setBoxGauge(String boxGauge) {
        this.boxGauge = boxGauge == null ? null : boxGauge.trim();
    }

    public String getApplicableMonthAge() {
        return applicableMonthAge;
    }

    public void setApplicableMonthAge(String applicableMonthAge) {
        this.applicableMonthAge = applicableMonthAge == null ? null : applicableMonthAge.trim();
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point == null ? null : point.trim();
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public String getMnemonicCode() {
        return mnemonicCode;
    }

    public void setMnemonicCode(String mnemonicCode) {
        this.mnemonicCode = mnemonicCode == null ? null : mnemonicCode.trim();
    }

    public Byte getSelectionEffectiveTime() {
        return selectionEffectiveTime;
    }

    public void setSelectionEffectiveTime(Byte selectionEffectiveTime) {
        this.selectionEffectiveTime = selectionEffectiveTime;
    }

    public String getSelectionEffectiveStartTime() {
        return selectionEffectiveStartTime;
    }

    public void setSelectionEffectiveStartTime(String selectionEffectiveStartTime) {
        this.selectionEffectiveStartTime = selectionEffectiveStartTime == null ? null : selectionEffectiveStartTime.trim();
    }

    public String getSelectionEffectiveEndTime() {
        return selectionEffectiveEndTime;
    }

    public void setSelectionEffectiveEndTime(String selectionEffectiveEndTime) {
        this.selectionEffectiveEndTime = selectionEffectiveEndTime == null ? null : selectionEffectiveEndTime.trim();
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode == null ? null : applyCode.trim();
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

    public Byte getGoodsGifts() {
        return goodsGifts;
    }

    public void setGoodsGifts(Byte goodsGifts) {
        this.goodsGifts = goodsGifts;
    }

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode == null ? null : spuCode.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public String getItroImages() {
        return itroImages;
    }

    public void setItroImages(String itroImages) {
        this.itroImages = itroImages == null ? null : itroImages.trim();
    }

    public Byte getIsMainPush() {
        return isMainPush;
    }

    public void setIsMainPush(Byte isMainPush) {
        this.isMainPush = isMainPush;
    }

    public Byte getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Byte newProduct) {
        this.newProduct = newProduct;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getManufacturerGuidePrice() {
        return manufacturerGuidePrice;
    }

    public void setManufacturerGuidePrice(Long manufacturerGuidePrice) {
        this.manufacturerGuidePrice = manufacturerGuidePrice;
    }

    public Long getZeroRemovalCoefficient() {
        return zeroRemovalCoefficient;
    }

    public void setZeroRemovalCoefficient(Long zeroRemovalCoefficient) {
        this.zeroRemovalCoefficient = zeroRemovalCoefficient;
    }

    public String getProcurementSectionCode() {
        return procurementSectionCode;
    }

    public void setProcurementSectionCode(String procurementSectionCode) {
        this.procurementSectionCode = procurementSectionCode == null ? null : procurementSectionCode.trim();
    }

    public String getProcurementSectionName() {
        return procurementSectionName;
    }

    public void setProcurementSectionName(String procurementSectionName) {
        this.procurementSectionName = procurementSectionName == null ? null : procurementSectionName.trim();
    }

    public Byte getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Byte applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getAuditorBy() {
        return auditorBy;
    }

    public void setAuditorBy(String auditorBy) {
        this.auditorBy = auditorBy;
    }

    public Date getAuditorTime() {
        return auditorTime;
    }

    public void setAuditorTime(Date auditorTime) {
        this.auditorTime = auditorTime;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }
}