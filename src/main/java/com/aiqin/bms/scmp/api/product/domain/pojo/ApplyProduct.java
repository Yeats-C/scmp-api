package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("商品申请")
public class ApplyProduct extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("条形码")
    private String barCode;

    @ApiModelProperty("申请状态(0:待审 1:审核中 2:审核通过 3:审核未通过 )")
    private Byte applyStatus;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建时间")
    private Date updateTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建人")
    private String updateBy;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("流水单号")
    private String formNo;

    @ApiModelProperty("(0:不撤销,1:撤销)")
    private Byte priceRevoke;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("申请时间起始时间")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("申请结束时间")
    private Date selectionEffectiveEndTime;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

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

    public Byte getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Byte applyStatus) {
        this.applyStatus = applyStatus;
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

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo == null ? null : formNo.trim();
    }

    public Byte getPriceRevoke() {
        return priceRevoke;
    }

    public void setPriceRevoke(Byte priceRevoke) {
        this.priceRevoke = priceRevoke;
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