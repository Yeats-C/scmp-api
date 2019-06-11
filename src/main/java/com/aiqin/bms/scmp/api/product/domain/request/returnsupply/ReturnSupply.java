package com.aiqin.bms.scmp.api.product.domain.request.returnsupply;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("退供单主表")
public class ReturnSupply {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("退供单编码")
    private String code;

    @ApiModelProperty("供应单位编码")
    private String supplyCode;

    @ApiModelProperty("供应单位名称")
    private String supplyName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应单位账户名称")
    private String accountName;

    @ApiModelProperty("供应单位账户")
    private String account;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("合同编号")
    private String contractCode;

    @ApiModelProperty("负责人名字")
    private String chargeMan;

    @ApiModelProperty("联系人名称")
    private String linkMan;

    @ApiModelProperty("联系人电话")
    private String linkPhone;

    @ApiModelProperty("创建人公司编码")
    private String companyCode;

    @ApiModelProperty("创建人公司名称")
    private String companyName;

    @ApiModelProperty("退供总数量")
    private Long totalNum;

    @ApiModelProperty("含税退供总金额")
    private Long totalAmount;

    @ApiModelProperty("实际退供数量")
    private Long actualNum;

    @ApiModelProperty("实际含税退供总金额")
    private Long actualAmount;

    @ApiModelProperty("退供单状态(0,待提交,1待审核,2,审核中,3,待供应商确认,4,待出库,5,完成,6,取消,7,审核不通过)")
    private Byte returnSupplyStatus;

    @ApiModelProperty("是否删除(撤销,0,否,1,是)")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("创建人名字")
    private String createBy;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty("更新人名字")
    private String updateBy;

    @ApiModelProperty("销售单位退供总数量")
    private Long saleUnitTotalNum;

    @ApiModelProperty("不含税退供总金额")
    private Long noTaxTotalAmount;

    @ApiModelProperty("销售单位实际退供数量")
    private Long saleUnitActualNum;

    @ApiModelProperty("不含税实际退供总数量")
    private Long noTaxActualAmount;
    @ApiModelProperty("审批流id")
    private String formNo;

    @ApiModelProperty("出库单号")
    private String outBoundCode;

    public String getOutBoundCode() {
        return outBoundCode;
    }

    public void setOutBoundCode(String outBoundCode) {
        this.outBoundCode = outBoundCode;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }


    public Long getSaleUnitTotalNum() {
        return saleUnitTotalNum;
    }

    public void setSaleUnitTotalNum(Long saleUnitTotalNum) {
        this.saleUnitTotalNum = saleUnitTotalNum;
    }

    public Long getNoTaxTotalAmount() {
        return noTaxTotalAmount;
    }

    public void setNoTaxTotalAmount(Long noTaxTotalAmount) {
        this.noTaxTotalAmount = noTaxTotalAmount;
    }

    public Long getSaleUnitActualNum() {
        return saleUnitActualNum;
    }

    public void setSaleUnitActualNum(Long saleUnitActualNum) {
        this.saleUnitActualNum = saleUnitActualNum;
    }

    public Long getNoTaxActualAmount() {
        return noTaxActualAmount;
    }

    public void setNoTaxActualAmount(Long noTaxActualAmount) {
        this.noTaxActualAmount = noTaxActualAmount;
    }

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

    public String getSupplyCode() {
        return supplyCode;
    }

    public void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode == null ? null : supplyCode.trim();
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName == null ? null : supplyName.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getTransportCenterCode() {
        return transportCenterCode;
    }

    public void setTransportCenterCode(String transportCenterCode) {
        this.transportCenterCode = transportCenterCode == null ? null : transportCenterCode.trim();
    }

    public String getTransportCenterName() {
        return transportCenterName;
    }

    public void setTransportCenterName(String transportCenterName) {
        this.transportCenterName = transportCenterName == null ? null : transportCenterName.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
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

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getChargeMan() {
        return chargeMan;
    }

    public void setChargeMan(String chargeMan) {
        this.chargeMan = chargeMan == null ? null : chargeMan.trim();
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone == null ? null : linkPhone.trim();
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

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getActualNum() {
        return actualNum;
    }

    public void setActualNum(Long actualNum) {
        this.actualNum = actualNum;
    }

    public Long getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Byte getReturnSupplyStatus() {
        return returnSupplyStatus;
    }

    public void setReturnSupplyStatus(Byte returnSupplyStatus) {
        this.returnSupplyStatus = returnSupplyStatus;
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