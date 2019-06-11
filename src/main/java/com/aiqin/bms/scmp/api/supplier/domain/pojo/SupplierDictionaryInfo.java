package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("供应商字典详细信息表")
public class SupplierDictionaryInfo extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供应商字典值")
    private String supplierDictionaryValue;

    @ApiModelProperty("供应商字典内容")
    private String supplierContent;

    @ApiModelProperty("供应商字典值排序")
    private Integer supplierWeight;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("供应商字典code")
    private String supplierDictionaryCode;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("供应商字典名称")
    private String supplierDictionaryName;

    @ApiModelProperty("供应商字典详细code")
    private String supplierDictionaryInfoCode;

    @ApiModelProperty("是否启用(0,1)")
    private Byte enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierDictionaryValue() {
        return supplierDictionaryValue;
    }

    public void setSupplierDictionaryValue(String supplierDictionaryValue) {
        this.supplierDictionaryValue = supplierDictionaryValue == null ? null : supplierDictionaryValue.trim();
    }

    public String getSupplierContent() {
        return supplierContent;
    }

    public void setSupplierContent(String supplierContent) {
        this.supplierContent = supplierContent == null ? null : supplierContent.trim();
    }

    public Integer getSupplierWeight() {
        return supplierWeight;
    }

    public void setSupplierWeight(Integer supplierWeight) {
        this.supplierWeight = supplierWeight;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public String getSupplierDictionaryCode() {
        return supplierDictionaryCode;
    }

    public void setSupplierDictionaryCode(String supplierDictionaryCode) {
        this.supplierDictionaryCode = supplierDictionaryCode == null ? null : supplierDictionaryCode.trim();
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

    public String getSupplierDictionaryName() {
        return supplierDictionaryName;
    }

    public void setSupplierDictionaryName(String supplierDictionaryName) {
        this.supplierDictionaryName = supplierDictionaryName == null ? null : supplierDictionaryName.trim();
    }

    public String getSupplierDictionaryInfoCode() {
        return supplierDictionaryInfoCode;
    }

    public void setSupplierDictionaryInfoCode(String supplierDictionaryInfoCode) {
        this.supplierDictionaryInfoCode = supplierDictionaryInfoCode == null ? null : supplierDictionaryInfoCode.trim();
    }
    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }
}