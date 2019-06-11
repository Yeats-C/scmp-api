package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("")
public class NewProduct extends CommonBean {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("条形码")
    private String barCode;

    @ApiModelProperty("商品id")
    private String productId;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("价格")
    private Integer price;

    @ApiModelProperty("spu code")
    private String spuCode;

    @ApiModelProperty("零售价格")
    private Integer retailPrice;

    @ApiModelProperty("列表图")
    private String logo;

    @ApiModelProperty("封面图")
    private String images;

    @ApiModelProperty("详情图")
    private String itroImages;

    @ApiModelProperty("商品分类id")
    private String categoryId;

    @ApiModelProperty("商品分类名称")
    private String categoryName;

    @ApiModelProperty("品牌类型id")
    private String brandId;

    @ApiModelProperty("品牌类型名称")
    private String brandName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("申请状态(0:待审 1:审核中 2:审核通过 3:审核未通过 4:已撤销)")
    private Byte applyStatus;

    @ApiModelProperty("申请商品编码")
    private String applyProductCode;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("申请时间起始时间")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("申请结束时间")
    private Date selectionEffectiveEndTime;

    @ApiModelProperty("(0:不撤销,1:撤销)")
    private Byte priceRevoke;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

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
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
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
        this.spuCode = spuCode == null ? null : spuCode.trim();
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId == null ? null : brandId.trim();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
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
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public String getAuditorBy() {
        return auditorBy;
    }

    public void setAuditorBy(String auditorBy) {
        this.auditorBy = auditorBy == null ? null : auditorBy.trim();
    }

    public Date getAuditorTime() {
        return auditorTime;
    }

    public void setAuditorTime(Date auditorTime) {
        this.auditorTime = auditorTime;
    }

    public Byte getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Byte applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyProductCode() {
        return applyProductCode;
    }

    public void setApplyProductCode(String applyProductCode) {
        this.applyProductCode = applyProductCode == null ? null : applyProductCode.trim();
    }

    public Byte getSelectionEffectiveTime() {
        return selectionEffectiveTime;
    }

    public void setSelectionEffectiveTime(Byte selectionEffectiveTime) {
        this.selectionEffectiveTime = selectionEffectiveTime;
    }

    public Date getSelectionEffectiveStartTime() {
        return selectionEffectiveStartTime;
    }

    public void setSelectionEffectiveStartTime(Date selectionEffectiveStartTime) {
        this.selectionEffectiveStartTime = selectionEffectiveStartTime;
    }

    public Date getSelectionEffectiveEndTime() {
        return selectionEffectiveEndTime;
    }

    public void setSelectionEffectiveEndTime(Date selectionEffectiveEndTime) {
        this.selectionEffectiveEndTime = selectionEffectiveEndTime;
    }

    public Byte getPriceRevoke() {
        return priceRevoke;
    }

    public void setPriceRevoke(Byte priceRevoke) {
        this.priceRevoke = priceRevoke;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode == null ? null : applyCode.trim();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}