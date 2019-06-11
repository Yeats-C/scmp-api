package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("申请合同进货额")
public class ApplyContractPurchaseVolume extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("所属申请合同code")
    private String applyContractCode;

    @ApiModelProperty("0:无税1:有税")
    private Byte tax;

    @ApiModelProperty("金额(以分为单位)")
    private Long amountMoney;

    @ApiModelProperty("按比例")
    private Long proportion;

    @ApiModelProperty("或者金额(以分为单位)")
    private Long orAmountMoney;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyContractCode() {
        return applyContractCode;
    }

    public void setApplyContractCode(String applyContractCode) {
        this.applyContractCode = applyContractCode == null ? null : applyContractCode.trim();
    }

    public Byte getTax() {
        return tax;
    }

    public void setTax(Byte tax) {
        this.tax = tax;
    }

    public Long getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(Long amountMoney) {
        this.amountMoney = amountMoney;
    }

    public Long getProportion() {
        return proportion;
    }

    public void setProportion(Long proportion) {
        this.proportion = proportion;
    }

    public Long getOrAmountMoney() {
        return orAmountMoney;
    }

    public void setOrAmountMoney(Long orAmountMoney) {
        this.orAmountMoney = orAmountMoney;
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
}