package com.aiqin.bms.scmp.api.product.domain.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by 爱亲 on 2018/11/16.
 */
@ApiModel("商品详情信息")
public class ProductInfoRequest {
    @ApiModelProperty(value = "商品id")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value = "分销机构id")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "skuCode")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty(value = "单位")
    @JsonProperty("unit")
    private String unit;

    @ApiModelProperty(value = "品牌id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value = "分类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value = "库存")
    @JsonProperty("show_stock")
    private Integer showStock;

    @ApiModelProperty(value = "预警库存")
    @JsonProperty("warning_stock")
    private Integer warningStock;

    @ApiModelProperty(value = "列表名称")
    @JsonProperty("show_name")
    private String showName;

    @ApiModelProperty(value = "门店零售价")
    @JsonProperty("price")
    private Integer price;

    @ApiModelProperty(value = "自定义销量")
    @JsonProperty("custom_sales")
    private Integer customSales;

    @ApiModelProperty(value = "门店发货运费")
    @JsonProperty("freight")
    private Integer freight;

    @ApiModelProperty(value = "是否上架的微商城，0为未上架，1为上架")
    @JsonProperty("on_sale")
    private Integer onSale;

    @ApiModelProperty(value = "封面图")
    @JsonProperty("images")
    private String images;

    @ApiModelProperty(value = "封面图")
    @JsonProperty("itro_images")
    private String itroImages;

    @ApiModelProperty(value = "create_by")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value = "update_by")
    @JsonProperty("update_by")
    private String updateBy;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
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

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getShowStock() {
        return showStock;
    }

    public void setShowStock(Integer showStock) {
        this.showStock = showStock;
    }

    public Integer getWarningStock() {
        return warningStock;
    }

    public void setWarningStock(Integer warningStock) {
        this.warningStock = warningStock;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
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
}