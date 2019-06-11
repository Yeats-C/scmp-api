package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("库存request")
public class StockSumRequest extends PagesRequest {

    @ApiModelProperty("sku号")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty(value = "sku_name")
    private String skuName;

    @ApiModelProperty("进货状态")
    @JsonProperty(value = "purchase_status")
    private Integer purchaseStatus;

    @ApiModelProperty("销售状态")
    @JsonProperty(value = "sale_status")
    private Integer saleStatus;

    @ApiModelProperty("品类编码CodeOrName")
    @JsonProperty(value = "product_category_text")
    private String productCategoryText;

    @ApiModelProperty("品牌编码CodeOrName")
    @JsonProperty(value = "brand_text")
    private String brandText;

    @ApiModelProperty("属性编码CodeOrName")
    @JsonProperty(value = "property_text")
    private String propertyText;

    @ApiModelProperty("正品库存数begin")
    @JsonProperty(value = "authentic_begin_num")
    private Long authenticBeginNum;

    @ApiModelProperty("正品库存数finish")
    @JsonProperty(value = "authentic_finish_num")
    private Long authenticFinishNum;

    @ApiModelProperty("可用库存数begin")
    @JsonProperty(value = "available_begin_num")
    private Long availableBeginNum;

    @ApiModelProperty("可用库存数finish")
    @JsonProperty(value = "available_finish_num")
    private Long availableFinishNum;

    @ApiModelProperty("备品库存数begin")
    @JsonProperty(value = "spare_begin_num")
    private Long spareBeginNum;

    @ApiModelProperty("备品库存数finish")
    @JsonProperty(value = "spare_finish_num")
    private Long spareFinishNum;

    public StockSumRequest() {
    }

    public StockSumRequest( String skuCode, String skuName, Integer purchaseStatus, Integer saleStatus, String productCategoryText, String brandText, String propertyText, Long authenticBeginNum, Long authenticFinishNum, Long availableBeginNum, Long availableFinishNum, Long spareBeginNum, Long spareFinishNum, Integer pageSize, Integer pageNo) {
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.purchaseStatus = purchaseStatus;
        this.saleStatus = saleStatus;
        this.productCategoryText = productCategoryText;
        this.brandText = brandText;
        this.propertyText = propertyText;
        this.authenticBeginNum = authenticBeginNum;
        this.authenticFinishNum = authenticFinishNum;
        this.availableBeginNum = availableBeginNum;
        this.availableFinishNum = availableFinishNum;
        this.spareBeginNum = spareBeginNum;
        this.spareFinishNum = spareFinishNum;
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
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

    public Long getAuthenticBeginNum() {
        return authenticBeginNum;
    }

    public void setAuthenticBeginNum(Long authenticBeginNum) {
        this.authenticBeginNum = authenticBeginNum;
    }

    public Long getAuthenticFinishNum() {
        return authenticFinishNum;
    }

    public void setAuthenticFinishNum(Long authenticFinishNum) {
        this.authenticFinishNum = authenticFinishNum;
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

    public Long getSpareBeginNum() {
        return spareBeginNum;
    }

    public void setSpareBeginNum(Long spareBeginNum) {
        this.spareBeginNum = spareBeginNum;
    }

    public Long getSpareFinishNum() {
        return spareFinishNum;
    }

    public void setSpareFinishNum(Long spareFinishNum) {
        this.spareFinishNum = spareFinishNum;
    }
}