package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("商品字典详细信息表")
public class ProductDictionaryInfo extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("商品字典值")
    private String productDictionaryValue;

    @ApiModelProperty("商品字典内容")
    private String productContent;

    @ApiModelProperty("商品字典排序")
    private Integer productWeight;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("商品字典code")
    private String productDictionaryCode;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("商品字典名称")
    private String productDictionaryName;

    @ApiModelProperty("详细code")
    private String productDictionaryInfoCode;
    @ApiModelProperty("是否启用(0,1)")
    private Byte enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductDictionaryValue() {
        return productDictionaryValue;
    }

    public void setProductDictionaryValue(String productDictionaryValue) {
        this.productDictionaryValue = productDictionaryValue == null ? null : productDictionaryValue.trim();
    }

    public String getProductContent() {
        return productContent;
    }

    public void setProductContent(String productContent) {
        this.productContent = productContent == null ? null : productContent.trim();
    }

    public Integer getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(Integer productWeight) {
        this.productWeight = productWeight;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public String getProductDictionaryCode() {
        return productDictionaryCode;
    }

    public void setProductDictionaryCode(String productDictionaryCode) {
        this.productDictionaryCode = productDictionaryCode == null ? null : productDictionaryCode.trim();
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

    public String getProductDictionaryName() {
        return productDictionaryName;
    }

    public void setProductDictionaryName(String productDictionaryName) {
        this.productDictionaryName = productDictionaryName == null ? null : productDictionaryName.trim();
    }

    public String getProductDictionaryInfoCode() {
        return productDictionaryInfoCode;
    }

    public void setProductDictionaryInfoCode(String productDictionaryInfoCode) {
        this.productDictionaryInfoCode = productDictionaryInfoCode == null ? null : productDictionaryInfoCode.trim();
    }
    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }
}