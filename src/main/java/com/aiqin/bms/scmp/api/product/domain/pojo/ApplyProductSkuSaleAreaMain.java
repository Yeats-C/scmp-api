package com.aiqin.bms.scmp.api.product.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("销售区域主表")
public class ApplyProductSkuSaleAreaMain {
    @ApiModelProperty(" 主键")
    private Long id;

    @ApiModelProperty("限制区域名称")
    private String code;

    @ApiModelProperty("限制区域名称")
    private String name;

    @ApiModelProperty("正式限制区域编码")
    private String officialCode;

    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

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

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("1新增2修改")
    private Integer applyType;

    @ApiModelProperty("申请状态")
    private Integer applyStatus;

    @ApiModelProperty("申请状态")
    private String applyCode;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("是否生效(0未生效1已生效）")
    private Integer beEffective;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效开始时间")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("申请表单号")
    private String formNo;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOfficialCode() {
        return officialCode;
    }

    public void setOfficialCode(String officialCode) {
        this.officialCode = officialCode == null ? null : officialCode.trim();
    }

    public Integer getBeDisable() {
        return beDisable;
    }

    public void setBeDisable(Integer beDisable) {
        this.beDisable = beDisable;
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

    public Date getAuditorTime() {
        return auditorTime;
    }

    public void setAuditorTime(Date auditorTime) {
        this.auditorTime = auditorTime;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
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

    public Integer getBeEffective() {
        return beEffective;
    }

    public void setBeEffective(Integer beEffective) {
        this.beEffective = beEffective;
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

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo == null ? null : formNo.trim();
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

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }
}