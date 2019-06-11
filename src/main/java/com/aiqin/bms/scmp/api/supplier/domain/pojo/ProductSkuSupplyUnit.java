package com.aiqin.bms.scmp.api.supplier.domain.pojo;


import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("商品sku 供货单位")
public class ProductSkuSupplyUnit extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供货单位code")
    private String supplyUnitCode;

    @ApiModelProperty("供货单位名称")
    private String supplyUnitName;

    @ApiModelProperty("无税进价")
    private Long noTaxPurchasePrice;

    @ApiModelProperty("含税进价")
    private Long taxIncludedPrice;

    @ApiModelProperty("联营扣率")
    private Long jointFranchiseRate;

    @ApiModelProperty("返点")
    private Long point;

    @ApiModelProperty("是否缺省（0:否,1：是）")
    private Byte isDefault;

    @ApiModelProperty("删除标记(0:正常 1:删除）")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("商品sku code")
    private String productSkuCode;

    @ApiModelProperty("0:未用 1:在用")
    private Byte usageStatus;

    @ApiModelProperty("商品sku 名称")
    private String productSkuName;

    @ApiModelProperty("申请编码")
    private String applyCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplyUnitCode() {
        return supplyUnitCode;
    }

    public void setSupplyUnitCode(String supplyUnitCode) {
        this.supplyUnitCode = supplyUnitCode == null ? null : supplyUnitCode.trim();
    }

    public String getSupplyUnitName() {
        return supplyUnitName;
    }

    public void setSupplyUnitName(String supplyUnitName) {
        this.supplyUnitName = supplyUnitName == null ? null : supplyUnitName.trim();
    }

    public Long getNoTaxPurchasePrice() {
        return noTaxPurchasePrice;
    }

    public void setNoTaxPurchasePrice(Long noTaxPurchasePrice) {
        this.noTaxPurchasePrice = noTaxPurchasePrice;
    }

    public Long getTaxIncludedPrice() {
        return taxIncludedPrice;
    }

    public void setTaxIncludedPrice(Long taxIncludedPrice) {
        this.taxIncludedPrice = taxIncludedPrice;
    }

    public Long getJointFranchiseRate() {
        return jointFranchiseRate;
    }

    public void setJointFranchiseRate(Long jointFranchiseRate) {
        this.jointFranchiseRate = jointFranchiseRate;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Byte getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
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

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode == null ? null : productSkuCode.trim();
    }

    public Byte getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(Byte usageStatus) {
        this.usageStatus = usageStatus;
    }

    public String getProductSkuName() {
        return productSkuName;
    }

    public void setProductSkuName(String productSkuName) {
        this.productSkuName = productSkuName == null ? null : productSkuName.trim();
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode == null ? null : applyCode.trim();
    }
}