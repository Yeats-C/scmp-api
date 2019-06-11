package com.aiqin.bms.scmp.api.product.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("销售区域申请表")
public class ProductSkuSaleArea {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("主表编码")
    private String code;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

    @ApiModelProperty("供货渠道类别编码")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("供货渠道类别名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("直送供应商编码")
    private String directDeliverySupplierCode;

    @ApiModelProperty("直送供应商名称")
    private String directDeliverySupplierName;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("扩展字段1")
    private String extField1;

    @ApiModelProperty("扩展字段2")
    private String extField2;

    @ApiModelProperty("扩展字段3")
    private String extField3;

    @ApiModelProperty("扩展字段4")
    private Date extField4;

    @ApiModelProperty("扩展字段5")
    private Integer extField5;

    @ApiModelProperty("扩展字段6")
    private Long extField6;

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

    public Integer getBeDisable() {
        return beDisable;
    }

    public void setBeDisable(Integer beDisable) {
        this.beDisable = beDisable;
    }

    public String getCategoriesSupplyChannelsCode() {
        return categoriesSupplyChannelsCode;
    }

    public void setCategoriesSupplyChannelsCode(String categoriesSupplyChannelsCode) {
        this.categoriesSupplyChannelsCode = categoriesSupplyChannelsCode == null ? null : categoriesSupplyChannelsCode.trim();
    }

    public String getCategoriesSupplyChannelsName() {
        return categoriesSupplyChannelsName;
    }

    public void setCategoriesSupplyChannelsName(String categoriesSupplyChannelsName) {
        this.categoriesSupplyChannelsName = categoriesSupplyChannelsName == null ? null : categoriesSupplyChannelsName.trim();
    }

    public String getDirectDeliverySupplierCode() {
        return directDeliverySupplierCode;
    }

    public void setDirectDeliverySupplierCode(String directDeliverySupplierCode) {
        this.directDeliverySupplierCode = directDeliverySupplierCode == null ? null : directDeliverySupplierCode.trim();
    }

    public String getDirectDeliverySupplierName() {
        return directDeliverySupplierName;
    }

    public void setDirectDeliverySupplierName(String directDeliverySupplierName) {
        this.directDeliverySupplierName = directDeliverySupplierName == null ? null : directDeliverySupplierName.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public String getExtField1() {
        return extField1;
    }

    public void setExtField1(String extField1) {
        this.extField1 = extField1 == null ? null : extField1.trim();
    }

    public String getExtField2() {
        return extField2;
    }

    public void setExtField2(String extField2) {
        this.extField2 = extField2 == null ? null : extField2.trim();
    }

    public String getExtField3() {
        return extField3;
    }

    public void setExtField3(String extField3) {
        this.extField3 = extField3 == null ? null : extField3.trim();
    }

    public Date getExtField4() {
        return extField4;
    }

    public void setExtField4(Date extField4) {
        this.extField4 = extField4;
    }

    public Integer getExtField5() {
        return extField5;
    }

    public void setExtField5(Integer extField5) {
        this.extField5 = extField5;
    }

    public Long getExtField6() {
        return extField6;
    }

    public void setExtField6(Long extField6) {
        this.extField6 = extField6;
    }
}