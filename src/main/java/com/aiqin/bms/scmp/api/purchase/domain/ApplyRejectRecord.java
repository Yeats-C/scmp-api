package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel
public class ApplyRejectRecord {
    @ApiModelProperty(value="")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="")
    @JsonProperty("reject_record_id")
    private String rejectRecordId;

    @ApiModelProperty(value="")
    @JsonProperty("reject_record_code")
    private String rejectRecordCode;

    @ApiModelProperty(value="关联审批号")
    @JsonProperty("approval_code")
    private String approvalCode;

    @ApiModelProperty(value="负责人")
    @JsonProperty("duty_person")
    private String dutyPerson;

    @ApiModelProperty(value="联系人")
    @JsonProperty("contacts_person")
    private String contactsPerson;

    @ApiModelProperty(value="联系人电话")
    @JsonProperty("contacts_person_phone")
    private String contactsPersonPhone;

    @ApiModelProperty(value="公司")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty(value="公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty(value="收货区域 :省")
    @JsonProperty("province_id")
    private String provinceId;

    @ApiModelProperty(value="")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value="市")
    @JsonProperty("city_id")
    private String cityId;

    @ApiModelProperty(value="")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value="县")
    @JsonProperty("district_id")
    private String districtId;

    @ApiModelProperty(value="")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty(value="收货地址")
    @JsonProperty("address")
    private String address;

    @ApiModelProperty(value="预计配送时间")
    @JsonProperty("expect_time")
    private Date expectTime;

    @ApiModelProperty(value="有效期")
    @JsonProperty("valid_day")
    private Date validDay;

    @ApiModelProperty(value="直属上级id (取字典表数据)")
    @JsonProperty("dictionary_id")
    private String dictionaryId;

    @ApiModelProperty(value="")
    @JsonProperty("dictionary_name")
    private String dictionaryName;

    @ApiModelProperty(value="供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="商品 结算方式")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value="商品 结算方式")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

    @ApiModelProperty(value="")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="采购组 code")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value="仓编码(物流中心编码)")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓名称(物流中心名称)")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value="退供单状态: 0 待审核 1 审核中  2 待供应商确认 3 待出库  4 出库开始 5 已出库 6 已发运 7 完成 8 取消 9 审核不通过")
    @JsonProperty("reject_status")
    private Integer rejectStatus;

    @ApiModelProperty(value="供应商评分编号")
    @JsonProperty("score_code")
    private String scoreCode;

    @ApiModelProperty(value="")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="")
    @JsonProperty("transport_url")
    private String transportUrl;

    @ApiModelProperty(value="运输信息说明")
    @JsonProperty("transport_remark")
    private String transportRemark;

    @ApiModelProperty(value="")
    @JsonProperty("out_stock_time")
    private Date outStockTime;

    @ApiModelProperty(value="普通商品数量")
    @JsonProperty("product_count")
    private Integer productCount;

    @ApiModelProperty(value="普通商品含税金额")
    @JsonProperty("product_amount")
    private Long productAmount;

    @ApiModelProperty(value="单品数量")
    @JsonProperty("single_count")
    private Integer singleCount;

    @ApiModelProperty(value="赠品数量")
    @JsonProperty("gift_count")
    private Integer giftCount;

    @ApiModelProperty(value="赠品含税金额")
    @JsonProperty("gift_amount")
    private Long giftAmount;

    @ApiModelProperty(value="实物返回数量")
    @JsonProperty("return_count")
    private Integer returnCount;

    @ApiModelProperty(value="实物返金额")
    @JsonProperty("return_amount")
    private Long returnAmount;

    @ApiModelProperty(value="实际普通商品数量")
    @JsonProperty("actual_product_count")
    private Integer actualProductCount;

    @ApiModelProperty(value="实际普通商品含税金额")
    @JsonProperty("actual_product_amount")
    private Long actualProductAmount;

    @ApiModelProperty(value="实际单品数量")
    @JsonProperty("actual_single_count")
    private Integer actualSingleCount;

    @ApiModelProperty(value="实际赠品数量")
    @JsonProperty("actual_gift_count")
    private Integer actualGiftCount;

    @ApiModelProperty(value="实际赠品含税金额")
    @JsonProperty("actual_gift_amount")
    private Long actualGiftAmount;

    @ApiModelProperty(value="实际实物返回数量")
    @JsonProperty("actual_return_count")
    private Integer actualReturnCount;

    @ApiModelProperty(value="实际实物返金额")
    @JsonProperty("actual_return_amount")
    private Long actualReturnAmount;

    @ApiModelProperty(value="未税金额")
    @JsonProperty("untaxed_amount")
    private Long untaxedAmount;

    @ApiModelProperty(value="")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value="")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="")
    @JsonProperty("update_time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRejectRecordId() {
        return rejectRecordId;
    }

    public void setRejectRecordId(String rejectRecordId) {
        this.rejectRecordId = rejectRecordId;
    }

    public String getRejectRecordCode() {
        return rejectRecordCode;
    }

    public void setRejectRecordCode(String rejectRecordCode) {
        this.rejectRecordCode = rejectRecordCode;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getDutyPerson() {
        return dutyPerson;
    }

    public void setDutyPerson(String dutyPerson) {
        this.dutyPerson = dutyPerson;
    }

    public String getContactsPerson() {
        return contactsPerson;
    }

    public void setContactsPerson(String contactsPerson) {
        this.contactsPerson = contactsPerson;
    }

    public String getContactsPersonPhone() {
        return contactsPersonPhone;
    }

    public void setContactsPersonPhone(String contactsPersonPhone) {
        this.contactsPersonPhone = contactsPersonPhone;
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

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(Date expectTime) {
        this.expectTime = expectTime;
    }

    public Date getValidDay() {
        return validDay;
    }

    public void setValidDay(Date validDay) {
        this.validDay = validDay;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSettlementMethodCode() {
        return settlementMethodCode;
    }

    public void setSettlementMethodCode(String settlementMethodCode) {
        this.settlementMethodCode = settlementMethodCode;
    }

    public String getSettlementMethodName() {
        return settlementMethodName;
    }

    public void setSettlementMethodName(String settlementMethodName) {
        this.settlementMethodName = settlementMethodName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPurchaseGroupCode() {
        return purchaseGroupCode;
    }

    public void setPurchaseGroupCode(String purchaseGroupCode) {
        this.purchaseGroupCode = purchaseGroupCode;
    }

    public String getPurchaseGroupName() {
        return purchaseGroupName;
    }

    public void setPurchaseGroupName(String purchaseGroupName) {
        this.purchaseGroupName = purchaseGroupName;
    }

    public String getTransportCenterCode() {
        return transportCenterCode;
    }

    public void setTransportCenterCode(String transportCenterCode) {
        this.transportCenterCode = transportCenterCode;
    }

    public String getTransportCenterName() {
        return transportCenterName;
    }

    public void setTransportCenterName(String transportCenterName) {
        this.transportCenterName = transportCenterName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getRejectStatus() {
        return rejectStatus;
    }

    public void setRejectStatus(Integer rejectStatus) {
        this.rejectStatus = rejectStatus;
    }

    public String getScoreCode() {
        return scoreCode;
    }

    public void setScoreCode(String scoreCode) {
        this.scoreCode = scoreCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransportUrl() {
        return transportUrl;
    }

    public void setTransportUrl(String transportUrl) {
        this.transportUrl = transportUrl;
    }

    public String getTransportRemark() {
        return transportRemark;
    }

    public void setTransportRemark(String transportRemark) {
        this.transportRemark = transportRemark;
    }

    public Date getOutStockTime() {
        return outStockTime;
    }

    public void setOutStockTime(Date outStockTime) {
        this.outStockTime = outStockTime;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Long getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Long productAmount) {
        this.productAmount = productAmount;
    }

    public Integer getSingleCount() {
        return singleCount;
    }

    public void setSingleCount(Integer singleCount) {
        this.singleCount = singleCount;
    }

    public Integer getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(Integer giftCount) {
        this.giftCount = giftCount;
    }

    public Long getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(Long giftAmount) {
        this.giftAmount = giftAmount;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Long getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Long returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Integer getActualProductCount() {
        return actualProductCount;
    }

    public void setActualProductCount(Integer actualProductCount) {
        this.actualProductCount = actualProductCount;
    }

    public Long getActualProductAmount() {
        return actualProductAmount;
    }

    public void setActualProductAmount(Long actualProductAmount) {
        this.actualProductAmount = actualProductAmount;
    }

    public Integer getActualSingleCount() {
        return actualSingleCount;
    }

    public void setActualSingleCount(Integer actualSingleCount) {
        this.actualSingleCount = actualSingleCount;
    }

    public Integer getActualGiftCount() {
        return actualGiftCount;
    }

    public void setActualGiftCount(Integer actualGiftCount) {
        this.actualGiftCount = actualGiftCount;
    }

    public Long getActualGiftAmount() {
        return actualGiftAmount;
    }

    public void setActualGiftAmount(Long actualGiftAmount) {
        this.actualGiftAmount = actualGiftAmount;
    }

    public Integer getActualReturnCount() {
        return actualReturnCount;
    }

    public void setActualReturnCount(Integer actualReturnCount) {
        this.actualReturnCount = actualReturnCount;
    }

    public Long getActualReturnAmount() {
        return actualReturnAmount;
    }

    public void setActualReturnAmount(Long actualReturnAmount) {
        this.actualReturnAmount = actualReturnAmount;
    }

    public Long getUntaxedAmount() {
        return untaxedAmount;
    }

    public void setUntaxedAmount(Long untaxedAmount) {
        this.untaxedAmount = untaxedAmount;
    }

    public String getCreateById() {
        return createById;
    }

    public void setCreateById(String createById) {
        this.createById = createById;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getUpdateById() {
        return updateById;
    }

    public void setUpdateById(String updateById) {
        this.updateById = updateById;
    }

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
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
}