package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("商品变价")
public class VariablePrice extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("变价code")
    private String variablePriceCode;

    @ApiModelProperty("价格类型code")
    private String priceTypeCode;

    @ApiModelProperty("价格类型")
    private String priceTypeName;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("变价名称")
    private String variablePriceName;

    @ApiModelProperty("采购组编码")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    private String procurementSectionName;

    @ApiModelProperty("备注")
    private String remark;
 //0:保存->待提交,1:提交->待审核，2：审核通过3:审核不通过
    @ApiModelProperty("(0:待提交:1:提交->待审核 2:审核通过，3：审核不通过)")
    private Byte status;

    @ApiModelProperty("审核申请单号")
    private String formNo;
    @ApiModelProperty("(0:不撤销,1:撤销)")
    private Byte priceRevoke;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;


    public VariablePrice(String variablePriceCode, String priceTypeCode, String priceTypeName, String variablePriceName, String procurementSectionCode, String procurementSectionName, String remark, Byte status,Byte priceRevoke) {
        this.variablePriceCode = variablePriceCode;
        this.priceTypeCode = priceTypeCode;
        this.priceTypeName = priceTypeName;
        this.variablePriceName = variablePriceName;
        this.procurementSectionCode = procurementSectionCode;
        this.procurementSectionName = procurementSectionName;
        this.remark = remark;
        this.status = status;
        this.priceRevoke=priceRevoke;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariablePriceCode() {
        return variablePriceCode;
    }

    public void setVariablePriceCode(String variablePriceCode) {
        this.variablePriceCode = variablePriceCode == null ? null : variablePriceCode.trim();
    }

    public String getPriceTypeCode() {
        return priceTypeCode;
    }

    public void setPriceTypeCode(String priceTypeCode) {
        this.priceTypeCode = priceTypeCode == null ? null : priceTypeCode.trim();
    }

    public String getPriceTypeName() {
        return priceTypeName;
    }

    public void setPriceTypeName(String priceTypeName) {
        this.priceTypeName = priceTypeName == null ? null : priceTypeName.trim();
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
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

    public String getVariablePriceName() {
        return variablePriceName;
    }

    public void setVariablePriceName(String variablePriceName) {
        this.variablePriceName = variablePriceName == null ? null : variablePriceName.trim();
    }

    public String getProcurementSectionCode() {
        return procurementSectionCode;
    }

    public void setProcurementSectionCode(String procurementSectionCode) {
        this.procurementSectionCode = procurementSectionCode == null ? null : procurementSectionCode.trim();
    }

    public String getProcurementSectionName() {
        return procurementSectionName;
    }

    public void setProcurementSectionName(String procurementSectionName) {
        this.procurementSectionName = procurementSectionName == null ? null : procurementSectionName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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


    public VariablePrice(){

    }
}