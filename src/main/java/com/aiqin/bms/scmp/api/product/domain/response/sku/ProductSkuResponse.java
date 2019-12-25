package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 爱亲 on 2019/1/11.
 */
@ApiModel("productSkuInfo返回实体")
public class ProductSkuResponse {
    @ApiModelProperty("主键id")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("所属商品编码")
    @JsonProperty("product_code")
    private String productCode;

    @ApiModelProperty("商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty("商品品类code")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty("单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty("sku状态，0为启用，1为禁用")
    @JsonProperty("sku_status")
    private Byte skuStatus;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    @JsonProperty("del_flag")
    private Byte delFlag;

    @ApiModelProperty("商品属性code")
    @JsonProperty("product_property_code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性名称")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty("商品品牌code")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("商品品牌")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("sku简称")
    @JsonProperty("sku_abbreviation")
    private String skuAbbreviation;

    @ApiModelProperty("颜色code")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty("单位名称")
    @JsonProperty("unit_name")
    private String unitName;

    @ApiModelProperty("保质管理（0:管理 1:不管理）")
    @JsonProperty("quality_assurance_management")
    private Byte qualityAssuranceManagement;

    @ApiModelProperty("商品类别code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("商品类别名称")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("保质数量")
    @JsonProperty("quality_number")
    private String qualityNumber;

    @ApiModelProperty("保质日期")
    @JsonProperty("quality_date")
    private String qualityDate;

    @ApiModelProperty("管理方式code")
    @JsonProperty("management_style_code")
    private String managementStyleCode;

    @ApiModelProperty("管理方式名称")
    @JsonProperty("management_style_name")
    private String managementStyleName;

    @ApiModelProperty("供货渠道类别code")
    @JsonProperty("categories_supply_channels_code")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("是否上架，0为未上架，1为上架")
    @JsonProperty("on_sale")
    private Byte onSale;

    @ApiModelProperty("供货渠道类别名称")
    @JsonProperty("categories_supply_channels_name")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("箱规")
    @JsonProperty("box_gauge")
    private String boxGauge;

    @ApiModelProperty("适用起始月龄")
    @JsonProperty("applicable_month_age")
    private String applicableMonthAge;

    @ApiModelProperty("返点")
    @JsonProperty("point")
    private String point;

    @ApiModelProperty("条码")
    @JsonProperty("bar_code")
    private String barCode;

    @ApiModelProperty("助记码")
    @JsonProperty("mnemonic_code")
    private String mnemonicCode;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    @JsonProperty("selection_effective_time")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效起始时间")
    @JsonProperty("selection_effective_start_time")
    private String selectionEffectiveStartTime;

    @ApiModelProperty("生效结束时间")
    @JsonProperty("selection_effective_end_time")
    private String selectionEffectiveEndTime;

    @ApiModelProperty("申请编码")
    @JsonProperty("apply_code")
    private String applyCode;

    @ApiModelProperty("创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty("创建人")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty("更新时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty("更新人")
    @JsonProperty("update_by")
    private String updateBy;

    @ApiModelProperty("商品/赠品(0:商品，1:赠品)")
    @JsonProperty("goods_gifts")
    private Byte goodsGifts;

    @ApiModelProperty("spu_code")
    @JsonProperty("spu_code")
    private String spuCode;

    @ApiModelProperty("logo")
    @JsonProperty("logo")
    private String logo;

    @ApiModelProperty("图片")
    @JsonProperty("images")
    private String images;

    @ApiModelProperty("封面图")
    @JsonProperty("itro_images")
    private String itroImages;

    @ApiModelProperty("生产日期")
    @JsonProperty("production_date")
    private Date productionDate;

    @ApiModelProperty("质检报告文件路径")
    @JsonProperty("Inspection_report_path")
    private String inspectionReportPath;

    @ApiModelProperty("sku_name")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("判断是否失效，0为生效，1为失效")
    @JsonProperty("use_status")
    private Integer useStatus;

    @ApiModelProperty("价格")
    @JsonProperty("price")
    private BigDecimal price;

    @ApiModelProperty("图片路径")
    @JsonProperty("product_picture_path")
    private String productPicturePath;

    @ApiModelProperty("图片名称")
    @JsonProperty("product_picture_name")
    private String productPictureName;

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

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getInspectionReportPath() {
        return inspectionReportPath;
    }

    public void setInspectionReportPath(String inspectionReportPath) {
        this.inspectionReportPath = inspectionReportPath == null ? null : inspectionReportPath.trim();
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductPicturePath() {
        return productPicturePath;
    }

    public void setProductPicturePath(String productPicturePath) {
        this.productPicturePath = productPicturePath;
    }

    public String getProductPictureName() {
        return productPictureName;
    }

    public void setProductPictureName(String productPictureName) {
        this.productPictureName = productPictureName;
    }
}