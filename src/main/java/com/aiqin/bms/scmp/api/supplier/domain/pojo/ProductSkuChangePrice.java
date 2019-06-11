package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("变价主表")
public class ProductSkuChangePrice {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("变价类型编码")
    private String changePriceType;

    @ApiModelProperty("变价类型名称")
    private String changePriceName;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("备注")
    private String remake;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("运费承担方编码")
    private String costBearerCode;

    @ApiModelProperty("运费承担方名称")
    private String costBearerName;

    @ApiModelProperty("预算")
    private Long budget;

    @ApiModelProperty("公司名称")
    private String companyCode;

    @ApiModelProperty("公司编码")
    private String companyName;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("与之前相同")
    private Integer applyStatus;

    @ApiModelProperty("申请的表单号")
    private String formNo;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("来源(0变价1sku新增)")
    private Integer original;

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

    public String getChangePriceType() {
        return changePriceType;
    }

    public void setChangePriceType(String changePriceType) {
        this.changePriceType = changePriceType == null ? null : changePriceType.trim();
    }

    public String getChangePriceName() {
        return changePriceName;
    }

    public void setChangePriceName(String changePriceName) {
        this.changePriceName = changePriceName == null ? null : changePriceName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPurchaseGroupCode() {
        return purchaseGroupCode;
    }

    public void setPurchaseGroupCode(String purchaseGroupCode) {
        this.purchaseGroupCode = purchaseGroupCode == null ? null : purchaseGroupCode.trim();
    }

    public String getPurchaseGroupName() {
        return purchaseGroupName;
    }

    public void setPurchaseGroupName(String purchaseGroupName) {
        this.purchaseGroupName = purchaseGroupName == null ? null : purchaseGroupName.trim();
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake == null ? null : remake.trim();
    }

    public String getDirectSupervisorCode() {
        return directSupervisorCode;
    }

    public void setDirectSupervisorCode(String directSupervisorCode) {
        this.directSupervisorCode = directSupervisorCode == null ? null : directSupervisorCode.trim();
    }

    public String getDirectSupervisorName() {
        return directSupervisorName;
    }

    public void setDirectSupervisorName(String directSupervisorName) {
        this.directSupervisorName = directSupervisorName == null ? null : directSupervisorName.trim();
    }

    public String getCostBearerCode() {
        return costBearerCode;
    }

    public void setCostBearerCode(String costBearerCode) {
        this.costBearerCode = costBearerCode == null ? null : costBearerCode.trim();
    }

    public String getCostBearerName() {
        return costBearerName;
    }

    public void setCostBearerName(String costBearerName) {
        this.costBearerName = costBearerName == null ? null : costBearerName.trim();
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
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

    public String getAuditorBy() {
        return auditorBy;
    }

    public void setAuditorBy(String auditorBy) {
        this.auditorBy = auditorBy == null ? null : auditorBy.trim();
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo == null ? null : formNo.trim();
    }

    public Date getAuditorTime() {
        return auditorTime;
    }

    public void setAuditorTime(Date auditorTime) {
        this.auditorTime = auditorTime;
    }

    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
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