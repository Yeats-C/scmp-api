package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by 爱亲 on 2018/11/16.
 */
@ApiModel("根据code跟分类来查询商品")
public class ProductRequest {

    @ApiModelProperty(value = "商品id")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value = "商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty(value = "价格")
    @JsonProperty("price")
    private Integer price;

    @ApiModelProperty(value = "列表名称")
    @JsonProperty("show_name")
    private String showName;

    @ApiModelProperty(value = "预警库存")
    @JsonProperty("warning_stock")
    private Integer warningStock;

    @ApiModelProperty(value = "自定义销量")
    @JsonProperty("custom_sales")
    private Integer customSales;

    @ApiModelProperty(value = "门店发货运费")
    @JsonProperty("freight")
    private Integer freight;

    @ApiModelProperty(value = "封面图")
    @JsonProperty("images")
    private String images;

    @ApiModelProperty(value = "关键字，用英文逗号隔开")
    @JsonProperty("keyword")
    private String keyword;

    @ApiModelProperty(value = "update_by")
    @JsonProperty("update_by")
    private String updateBy;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Integer getWarningStock() {
        return warningStock;
    }

    public void setWarningStock(Integer warningStock) {
        this.warningStock = warningStock;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}