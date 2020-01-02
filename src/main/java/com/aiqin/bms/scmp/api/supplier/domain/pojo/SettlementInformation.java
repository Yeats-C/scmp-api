package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("结算信息")
public class SettlementInformation extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("结算周期")
    private String settlementCycle;

    @ApiModelProperty("结算方式")
    private String settlementMethod;

    @ApiModelProperty("结算组")
    private String settlementGroup;

    @ApiModelProperty("最低订货金额")
    private BigDecimal minOrderAmount;

    @ApiModelProperty("最高订货金额")
    private BigDecimal maxOrderAmount;

    @ApiModelProperty("最高付款额")
    private BigDecimal maxPaymentAmount;

    @ApiModelProperty("所属供货单位名称")
    private String supplyCompanyName;

    @ApiModelProperty("所属供货单位编码")
    private String supplyCompanyCode;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("申请结算信息code")
    private String applySettlementInformationCode;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("申请状态(0 ：待审 1，审批中 2 审批通过 ，3 审批失败 4，已撤销 )")
    private Byte applyStatus;

    @ApiModelProperty("结算code")
    private String settlementCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettlementCycle() {
        return settlementCycle;
    }

    public void setSettlementCycle(String settlementCycle) {
        this.settlementCycle = settlementCycle == null ? null : settlementCycle.trim();
    }

    public String getSettlementMethod() {
        return settlementMethod;
    }

    public void setSettlementMethod(String settlementMethod) {
        this.settlementMethod = settlementMethod == null ? null : settlementMethod.trim();
    }

    public String getSettlementGroup() {
        return settlementGroup;
    }

    public void setSettlementGroup(String settlementGroup) {
        this.settlementGroup = settlementGroup == null ? null : settlementGroup.trim();
    }

    public BigDecimal getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(BigDecimal minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public BigDecimal getMaxOrderAmount() {
        return maxOrderAmount;
    }

    public void setMaxOrderAmount(BigDecimal maxOrderAmount) {
        this.maxOrderAmount = maxOrderAmount;
    }

    public BigDecimal getMaxPaymentAmount() {
        return maxPaymentAmount;
    }

    public void setMaxPaymentAmount(BigDecimal maxPaymentAmount) {
        this.maxPaymentAmount = maxPaymentAmount;
    }

    public String getSupplyCompanyName() {
        return supplyCompanyName;
    }

    public void setSupplyCompanyName(String supplyCompanyName) {
        this.supplyCompanyName = supplyCompanyName == null ? null : supplyCompanyName.trim();
    }

    public String getSupplyCompanyCode() {
        return supplyCompanyCode;
    }

    public void setSupplyCompanyCode(String supplyCompanyCode) {
        this.supplyCompanyCode = supplyCompanyCode == null ? null : supplyCompanyCode.trim();
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

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public String getApplySettlementInformationCode() {
        return applySettlementInformationCode;
    }

    public void setApplySettlementInformationCode(String applySettlementInformationCode) {
        this.applySettlementInformationCode = applySettlementInformationCode == null ? null : applySettlementInformationCode.trim();
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

    public String getSettlementCode() {
        return settlementCode;
    }

    public void setSettlementCode(String settlementCode) {
        this.settlementCode = settlementCode == null ? null : settlementCode.trim();
    }
}