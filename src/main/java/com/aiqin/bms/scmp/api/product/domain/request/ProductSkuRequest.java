package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by 爱亲 on 2018/11/22.
 */
@ApiModel("商品skucode传参")
public class ProductSkuRequest {
    @ApiModelProperty(value = "sku_code_list")
    @JsonProperty("sku_code_list")
    private List<String> skuCodeList;

    @ApiModelProperty(value = "门店id")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "brand_id_list")
    @JsonProperty("brand_id_list")
    private List<String> brandIdList;

    @ApiModelProperty(value = "category_id_list")
    @JsonProperty("category_id_list")
    private List<String> categoryIdList;

    @ApiModelProperty(value = "商品名称")
    @JsonProperty("product_name")
    private String productName;

    public List<String> getSkuCodeList() {
        return skuCodeList;
    }

    public void setSkuCodeList(List<String> skuCodeList) {
        this.skuCodeList = skuCodeList;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public ProductSkuRequest(List<String> skuCodeList, String distributorId, List<String> brandIdList, List<String> categoryIdList, String productName) {
        this.skuCodeList = skuCodeList;
        this.distributorId = distributorId;
        this.brandIdList = brandIdList;
        this.categoryIdList = categoryIdList;
        this.productName = productName;
    }

    public List<String> getBrandIdList() {
        return brandIdList;
    }

    public void setBrandIdList(List<String> brandIdList) {
        this.brandIdList = brandIdList;
    }

    public List<String> getCategoryIdList() {
        return categoryIdList;
    }

    public void setCategoryIdList(List<String> categoryIdList) {
        this.categoryIdList = categoryIdList;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductSkuRequest() {
    }
}