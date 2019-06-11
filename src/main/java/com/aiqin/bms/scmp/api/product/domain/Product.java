package com.aiqin.bms.scmp.api.product.domain;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("商品")
public class Product extends PagesRequest{
    @ApiModelProperty(value = "自增主键")
    @JsonProperty("id")
    private Long id;

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

    @ApiModelProperty(value = "价格")
    @JsonProperty("price")
    private Integer price;

    @ApiModelProperty(value = "spu_code")
    @JsonProperty("spu_code")
    private String spuCode;

    @ApiModelProperty(value = "零售价格")
    @JsonProperty("retail_price")
    private Integer retailPrice;

    @ApiModelProperty(value = "列表图")
    @JsonProperty("logo")
    private String logo;

    @ApiModelProperty(value = "封面图")
    @JsonProperty("images")
    private String images;

    @ApiModelProperty(value = "详情图")
    @JsonProperty("itro_images")
    private String itroImages;

    @ApiModelProperty(value = "商品分类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value = "商品分类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty(value = "品牌类型id")
    @JsonProperty("brand_id")
    private String brandId;

    @ApiModelProperty(value = "品牌类型名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty(value = "是否上架的微商城，0为未上架，1为上架")
    @JsonProperty("on_sale")
    private Integer onSale;

    @ApiModelProperty(value = "商品code集合")
    @JsonProperty("product_code_list")
    private List<String> productCodeList;

    @ApiModelProperty(value = "输入值，code或者name")
    @JsonProperty("text")
    private String text;

    @ApiModelProperty(value = "商品id集合")
    @JsonProperty("product_id_list")
    private List<String> productIdList;

    @ApiModelProperty(value = "create_time")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value = "update_time")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "create_by")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value = "update_by")
    @JsonProperty("update_by")
    private String updateBy;

    @ApiModelProperty(value = "是否被删除，1为被删除")
    @JsonProperty("is_delete")
    private Integer isDelete;

    @ApiModelProperty(value = "是否被删除，1为被删除")
    @JsonProperty("del_flag")
    private Integer delFlag;

    @ApiModelProperty(value = "审核人")
    @JsonProperty("auditor_by")
    private String auditorBy;


    @ApiModelProperty(value = "审批时间")
    @JsonProperty("auditor_time")
    private Date auditorTime;

    @ApiModelProperty(value = "审批状态")
    @JsonProperty("apply_status")
    private Integer applyStatus;


    @ApiModelProperty(value = "审批编码")
    @JsonProperty("apply_product_code")
    private String applyProductCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
    }

    public Integer getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Integer retailPrice) {
        this.retailPrice = retailPrice;
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

    public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    public List<String> getProductCodeList() {
        return productCodeList;
    }

    public void setProductCodeList(List<String> productCodeList) {
        this.productCodeList = productCodeList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Product(List<String> productIdList) {
        this.productIdList = productIdList;
    }

    public List<String> getProductIdList() {
        return productIdList;
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Product(String productName, String brandId, String categoryId) {
        this.productName = productName;
        this.brandId = brandId;
        this.categoryId = categoryId;
    }

    public Product(String brandId, String categoryId) {
        this.brandId = brandId;
        this.categoryId = categoryId;
    }

    public Product() {
    }

    public Product(String categoryId, List<String> productCodeList, Integer pageSize, Integer pageNo) {
        this.categoryId = categoryId;
        this.productCodeList = productCodeList;
        this.setPageSize(pageSize);
        this.setPageNo(pageNo);
    }

    public void setProductIdList(List<String> productIdList) {
        this.productIdList = productIdList;
    }


    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyProductCode() {
        return applyProductCode;
    }

    public void setApplyProductCode(String applyProductCode) {
        this.applyProductCode = applyProductCode;
    }
}