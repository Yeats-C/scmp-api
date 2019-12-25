package com.aiqin.bms.scmp.api.product.domain;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("商品分销机构")
@Data
public class ProductDistributor extends PagesRequest {
    @ApiModelProperty(value = "自增主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "spu_code")
    @JsonProperty("spu_code")
    private String spuCode;

    @ApiModelProperty(value = "sku_code")
    @JsonProperty("sku_code")
    private String skuCode;


    @ApiModelProperty(value = "关联单号")
    @JsonProperty("relation_id")
    private String relationId;


    @ApiModelProperty(value = "默认库存预警值(默认值为3)")
    @JsonProperty("default_warning_stock")
    private Integer defaultWarningStock;

    @ApiModelProperty(value = "分销机构id")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "分销机构编码")
    @JsonProperty("distributor_code")
    private String distributorCode;

    @ApiModelProperty(value = "分销机构名称")
    @JsonProperty("distributor_name")
    private String distributorName;

    @ApiModelProperty(value = "仓库类型，1为门店自有仓，2为爱亲大仓")
    @JsonProperty("storage_type")
    private Integer storageType;

    @ApiModelProperty(value = "陈列仓位库存")
    @JsonProperty("display_stock")
    private Integer displayStock;

    @ApiModelProperty(value = "退货仓位库存")
    @JsonProperty("return_stock")
    private Integer returnStock;

    @ApiModelProperty(value = "存储仓位库存")
    @JsonProperty("storage_stock")
    private Integer storageStock;

    @ApiModelProperty(value = "锁库库存")
    @JsonProperty("lock_stock")
    private Integer lockStock;

    @ApiModelProperty(value = "预警库存")
    @JsonProperty("warning_stock")
    private Integer warningStock;

    @ApiModelProperty(value = "展示库存")
    @JsonProperty("show_stock")
    private Integer showStock;

    @ApiModelProperty(value = "预警状态，0为正常，1为预警")
    @JsonProperty("warning_status")
    private Integer warningStatus;

    @ApiModelProperty(value = "自定义销量")
    @JsonProperty("custom_sales")
    private Integer customSales;

    @ApiModelProperty(value = "门店发货运费")
    @JsonProperty("freight")
    private Integer freight;

    @ApiModelProperty(value = "条形码")
    @JsonProperty("bar_code")
    private String barCode;

    @ApiModelProperty(value = "商品id")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value = "商品编码")
    @JsonProperty("product_code")
    private String productCode;

    @ApiModelProperty(value = "商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty(value = "商品分类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value = "商品分类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value = "规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty("颜色code")
    @JsonProperty("color_code")
    private String colorCode;

    @ApiModelProperty("颜色")
    @JsonProperty("color_name")
    private String colorName;

    @ApiModelProperty("型号")
    @JsonProperty("model_number")
    private String modelNumber;

    @ApiModelProperty("单位code")
    @JsonProperty("unit_code")
    private String unitCode;

    @ApiModelProperty(value = "单位")
    @JsonProperty("unit")
    private String unit;

    @ApiModelProperty(value = "列表名称")
    @JsonProperty("show_name")
    private String showName;

    @ApiModelProperty("进货价")
    @JsonProperty("purchase_price")
    private Long purchasePrice;

    @ApiModelProperty(value = "价格")
    @JsonProperty("price")
    private BigDecimal price;

    @ApiModelProperty(value = "会员价格")
    @JsonProperty("member_price")
    private BigDecimal memberPrice;

    @ApiModelProperty(value = "列表图")
    @JsonProperty("logo")
    private String logo;

    @ApiModelProperty(value = "封面图")
    @JsonProperty("images")
    private String images;

    @ApiModelProperty(value = "封面图")
    @JsonProperty("itro_images")
    private String itroImages;

    @ApiModelProperty(value = "关键字，用英文逗号隔开")
    @JsonProperty("keyword")
    private String keyword;

    @ApiModelProperty(value = "是否上架，0为未上架，1为上架")
    @JsonProperty("on_sale")
    private Integer onSale;

    @ApiModelProperty(value = "销售渠道，0为默认门店，1为上架到网店")
    @JsonProperty("distribution_channel")
    private Integer distributionChannel;

    @ApiModelProperty(value = "品牌类型id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value = "品牌类型名称")
    @JsonProperty("brand_name")
    private String brandName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "下架时间")
    @JsonProperty("sale_time")
    private Date saleTime;

    @ApiModelProperty(value = "create_time")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value = "update_time")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "create_by")
    @JsonProperty("create_by")
    private String createBy;

    @JsonProperty("create_by_name")
    @ApiModelProperty("创建人名称")
    public String createByName;

    @ApiModelProperty(value = "update_by")
    @JsonProperty("update_by")
    private String updateBy;

    @ApiModelProperty(value = "是否被删除，1为被删除")
    @JsonProperty("del_flag")
    private Integer delFlag;

    @ApiModelProperty(value = "不传值")
    @JsonProperty("activity_join_status")
    private Integer activityJoinStatus;

    @ApiModelProperty(value = "不传值 pos销量")
    @JsonProperty("pos_sale_num")
    private Integer posSaleNum;

    @ApiModelProperty(value = "不传值 微商城销量")
    @JsonProperty("online_sale_num")
    private Integer onlineSaleNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public Integer getDisplayStock() {
        return displayStock;
    }

    public void setDisplayStock(Integer displayStock) {
        this.displayStock = displayStock;
    }

    public Integer getReturnStock() {
        return returnStock;
    }

    public void setReturnStock(Integer returnStock) {
        this.returnStock = returnStock;
    }

    public Integer getStorageStock() {
        return storageStock;
    }

    public void setStorageStock(Integer storageStock) {
        this.storageStock = storageStock;
    }

    public Integer getLockStock() {
        return lockStock;
    }

    public void setLockStock(Integer lockStock) {
        this.lockStock = lockStock;
    }

    public Integer getWarningStock() {
        return warningStock;
    }

    public void setWarningStock(Integer warningStock) {
        this.warningStock = warningStock;
    }

    public Integer getShowStock() {
        return showStock;
    }

    public void setShowStock(Integer showStock) {
        this.showStock = showStock;
    }

    public Integer getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(Integer warningStatus) {
        this.warningStatus = warningStatus;
    }

    public Integer getCustomSales() {
        return customSales;
    }

    public void setCustomSales(Integer customSales) {
        this.customSales = customSales;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getItroImages() {
        return itroImages;
    }

    public void setItroImages(String itroImages) {
        this.itroImages = itroImages;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    public Integer getDistributionChannel() {
        return distributionChannel;
    }

    public void setDistributionChannel(Integer distributionChannel) {
        this.distributionChannel = distributionChannel;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getActivityJoinStatus() {
        return activityJoinStatus;
    }

    public void setActivityJoinStatus(Integer activityJoinStatus) {
        this.activityJoinStatus = activityJoinStatus;
    }

    public Integer getPosSaleNum() {
        return posSaleNum;
    }

    public void setPosSaleNum(Integer posSaleNum) {
        this.posSaleNum = posSaleNum;
    }

    public Integer getOnlineSaleNum() {
        return onlineSaleNum;
    }

    public void setOnlineSaleNum(Integer onlineSaleNum) {
        this.onlineSaleNum = onlineSaleNum;
    }

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
    }

    public Integer getDefaultWarningStock() {
        return defaultWarningStock;
    }

    public void setDefaultWarningStock(Integer defaultWarningStock) {
        this.defaultWarningStock = defaultWarningStock;
    }

    public ProductDistributor(String skuCode, String distributorId, String updateBy) {
        this.skuCode = skuCode;
        this.distributorId = distributorId;
        this.updateBy = updateBy;
    }

    public ProductDistributor() {
    }

    public ProductDistributor(String distributorId, String productName, Integer pageSize, Integer pageNo) {
        this.distributorId = distributorId;
        this.productName = productName;
        this.setPageSize(pageSize);
        this.setPageNo(pageNo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDistributor that = (ProductDistributor) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (skuCode != null ? !skuCode.equals(that.skuCode) : that.skuCode != null) return false;
        if (distributorId != null ? !distributorId.equals(that.distributorId) : that.distributorId != null)
            return false;
        if (distributorCode != null ? !distributorCode.equals(that.distributorCode) : that.distributorCode != null)
            return false;
        if (distributorName != null ? !distributorName.equals(that.distributorName) : that.distributorName != null)
            return false;
        if (storageType != null ? !storageType.equals(that.storageType) : that.storageType != null) return false;
        if (displayStock != null ? !displayStock.equals(that.displayStock) : that.displayStock != null) return false;
        if (returnStock != null ? !returnStock.equals(that.returnStock) : that.returnStock != null) return false;
        if (storageStock != null ? !storageStock.equals(that.storageStock) : that.storageStock != null) return false;
        if (lockStock != null ? !lockStock.equals(that.lockStock) : that.lockStock != null) return false;
        if (warningStock != null ? !warningStock.equals(that.warningStock) : that.warningStock != null) return false;
        if (showStock != null ? !showStock.equals(that.showStock) : that.showStock != null) return false;
        if (warningStatus != null ? !warningStatus.equals(that.warningStatus) : that.warningStatus != null)
            return false;
        if (customSales != null ? !customSales.equals(that.customSales) : that.customSales != null) return false;
        if (freight != null ? !freight.equals(that.freight) : that.freight != null) return false;
        if (barCode != null ? !barCode.equals(that.barCode) : that.barCode != null) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (productCode != null ? !productCode.equals(that.productCode) : that.productCode != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (categoryName != null ? !categoryName.equals(that.categoryName) : that.categoryName != null) return false;
        if (spec != null ? !spec.equals(that.spec) : that.spec != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (showName != null ? !showName.equals(that.showName) : that.showName != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (logo != null ? !logo.equals(that.logo) : that.logo != null) return false;
        if (images != null ? !images.equals(that.images) : that.images != null) return false;
        if (keyword != null ? !keyword.equals(that.keyword) : that.keyword != null) return false;
        if (onSale != null ? !onSale.equals(that.onSale) : that.onSale != null) return false;
        if (distributionChannel != null ? !distributionChannel.equals(that.distributionChannel) : that.distributionChannel != null)
            return false;
        if (brandId != null ? !brandId.equals(that.brandId) : that.brandId != null) return false;
        if (brandName != null ? !brandName.equals(that.brandName) : that.brandName != null) return false;
        if (saleTime != null ? !saleTime.equals(that.saleTime) : that.saleTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (createBy != null ? !createBy.equals(that.createBy) : that.createBy != null) return false;
        if (updateBy != null ? !updateBy.equals(that.updateBy) : that.updateBy != null) return false;
        if (delFlag != null ? !delFlag.equals(that.delFlag) : that.delFlag != null) return false;
        return !(activityJoinStatus != null ? !activityJoinStatus.equals(that.activityJoinStatus) : that.activityJoinStatus != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (skuCode != null ? skuCode.hashCode() : 0);
        result = 31 * result + (distributorId != null ? distributorId.hashCode() : 0);
        result = 31 * result + (distributorCode != null ? distributorCode.hashCode() : 0);
        result = 31 * result + (distributorName != null ? distributorName.hashCode() : 0);
        result = 31 * result + (storageType != null ? storageType.hashCode() : 0);
        result = 31 * result + (displayStock != null ? displayStock.hashCode() : 0);
        result = 31 * result + (returnStock != null ? returnStock.hashCode() : 0);
        result = 31 * result + (storageStock != null ? storageStock.hashCode() : 0);
        result = 31 * result + (lockStock != null ? lockStock.hashCode() : 0);
        result = 31 * result + (warningStock != null ? warningStock.hashCode() : 0);
        result = 31 * result + (showStock != null ? showStock.hashCode() : 0);
        result = 31 * result + (warningStatus != null ? warningStatus.hashCode() : 0);
        result = 31 * result + (customSales != null ? customSales.hashCode() : 0);
        result = 31 * result + (freight != null ? freight.hashCode() : 0);
        result = 31 * result + (barCode != null ? barCode.hashCode() : 0);
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (productCode != null ? productCode.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (spec != null ? spec.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (showName != null ? showName.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        result = 31 * result + (onSale != null ? onSale.hashCode() : 0);
        result = 31 * result + (distributionChannel != null ? distributionChannel.hashCode() : 0);
        result = 31 * result + (brandId != null ? brandId.hashCode() : 0);
        result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
        result = 31 * result + (saleTime != null ? saleTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
        result = 31 * result + (updateBy != null ? updateBy.hashCode() : 0);
        result = 31 * result + (delFlag != null ? delFlag.hashCode() : 0);
        result = 31 * result + (activityJoinStatus != null ? activityJoinStatus.hashCode() : 0);
        return result;
    }
}