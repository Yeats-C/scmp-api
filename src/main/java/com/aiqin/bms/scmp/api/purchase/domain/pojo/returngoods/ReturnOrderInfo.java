package com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel("退货订单主表")
public class ReturnOrderInfo {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("退货订单编码")
    private String returnOrderCode;

    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("类型：直送、配送、首单、首单赠送")
    private String orderType;

    @ApiModelProperty("订单类型编码")
    private Integer orderTypeCode;

    @ApiModelProperty("退货类型：客户退货、缺货退货、售后退货")
    private String returnOrderType;

    @ApiModelProperty("退货类型编码")
    private Integer returnOrderTypeCode;

    @ApiModelProperty("支付状态0未支付1已支付")
    private Integer paymentStatus;

    @ApiModelProperty("是否锁定(0否1是）")
    private Integer beLock;

    @ApiModelProperty("锁定原因")
    private String lockReason;

    @ApiModelProperty("是否是异常订单(0否1是)")
    private Integer beException;

    @ApiModelProperty("异常原因")
    private String exceptionReason;

    @ApiModelProperty("是否删除(0否1是)")
    private Integer beDelete;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("客户编码")
    private String customerCode;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("收货人手机号")
    private String consigneePhone;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("省编码")
    private String provinceCode;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市编码")
    private String cityCode;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区编码")
    private String districtCode;

    @ApiModelProperty("区名称")
    private String districtName;

    @ApiModelProperty("详细地址")
    private String detailAddress;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("配送方式")
    private String distributionMode;

    @ApiModelProperty("配送方式编码")
    private String distributionModeCode;

    @ApiModelProperty("订单状态(状态有点多，后面补)")
    private Integer orderStatus;

    @ApiModelProperty("支付方式")
    private String paymentType;

    @ApiModelProperty("支付方式编码")
    private String paymentTypeCode;

    @ApiModelProperty("运费")
    private Long deliverAmount;

    @ApiModelProperty("商品数量")
    private Long productNum;

    @ApiModelProperty("商品总金额")
    private Long productTotalAmount;

    @ApiModelProperty("退货金额")
    private Long returnOrderAmount;

    @ApiModelProperty("重量")
    private Long weight;

    @ApiModelProperty("发货时间")
    private Date deliveryTime;

    @ApiModelProperty("收货时间")
    private Date receivingTime;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("操作人编码")
    private String operatorCode;

    @ApiModelProperty("操作时间")
    private Date operatorTime;

    @ApiModelProperty("验货备注")
    private String inspectionRemark;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("运输公司编码")
    private String transportCompanyCode;

    @ApiModelProperty("运输公司")
    private String transportCompany;

    @ApiModelProperty("运输单号")
    private String transportNumber;

    @ApiModelProperty("退货原因编码")
    private String returnReasonCode;

    @ApiModelProperty("退货原因描述")
    private String returnReasonContent;

    @ApiModelProperty("备注")
    private String remake;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReturnOrderCode() {
        return returnOrderCode;
    }

    public void setReturnOrderCode(String returnOrderCode) {
        this.returnOrderCode = returnOrderCode == null ? null : returnOrderCode.trim();
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Integer getOrderTypeCode() {
        return orderTypeCode;
    }

    public void setOrderTypeCode(Integer orderTypeCode) {
        this.orderTypeCode = orderTypeCode;
    }

    public String getReturnOrderType() {
        return returnOrderType;
    }

    public void setReturnOrderType(String returnOrderType) {
        this.returnOrderType = returnOrderType == null ? null : returnOrderType.trim();
    }

    public Integer getReturnOrderTypeCode() {
        return returnOrderTypeCode;
    }

    public void setReturnOrderTypeCode(Integer returnOrderTypeCode) {
        this.returnOrderTypeCode = returnOrderTypeCode;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getBeLock() {
        return beLock;
    }

    public void setBeLock(Integer beLock) {
        this.beLock = beLock;
    }

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason == null ? null : lockReason.trim();
    }

    public Integer getBeException() {
        return beException;
    }

    public void setBeException(Integer beException) {
        this.beException = beException;
    }

    public String getExceptionReason() {
        return exceptionReason;
    }

    public void setExceptionReason(String exceptionReason) {
        this.exceptionReason = exceptionReason == null ? null : exceptionReason.trim();
    }

    public Integer getBeDelete() {
        return beDelete;
    }

    public void setBeDelete(Integer beDelete) {
        this.beDelete = beDelete;
    }

    public String getTransportCenterName() {
        return transportCenterName;
    }

    public void setTransportCenterName(String transportCenterName) {
        this.transportCenterName = transportCenterName == null ? null : transportCenterName.trim();
    }

    public String getTransportCenterCode() {
        return transportCenterCode;
    }

    public void setTransportCenterCode(String transportCenterCode) {
        this.transportCenterCode = transportCenterCode == null ? null : transportCenterCode.trim();
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone == null ? null : consigneePhone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode == null ? null : districtCode.trim();
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName == null ? null : districtName.trim();
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getDistributionMode() {
        return distributionMode;
    }

    public void setDistributionMode(String distributionMode) {
        this.distributionMode = distributionMode == null ? null : distributionMode.trim();
    }

    public String getDistributionModeCode() {
        return distributionModeCode;
    }

    public void setDistributionModeCode(String distributionModeCode) {
        this.distributionModeCode = distributionModeCode == null ? null : distributionModeCode.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType == null ? null : paymentType.trim();
    }

    public String getPaymentTypeCode() {
        return paymentTypeCode;
    }

    public void setPaymentTypeCode(String paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode == null ? null : paymentTypeCode.trim();
    }

    public Long getDeliverAmount() {
        return deliverAmount;
    }

    public void setDeliverAmount(Long deliverAmount) {
        this.deliverAmount = deliverAmount;
    }

    public Long getProductNum() {
        return productNum;
    }

    public void setProductNum(Long productNum) {
        this.productNum = productNum;
    }

    public Long getProductTotalAmount() {
        return productTotalAmount;
    }

    public void setProductTotalAmount(Long productTotalAmount) {
        this.productTotalAmount = productTotalAmount;
    }

    public Long getReturnOrderAmount() {
        return returnOrderAmount;
    }

    public void setReturnOrderAmount(Long returnOrderAmount) {
        this.returnOrderAmount = returnOrderAmount;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getReceivingTime() {
        return receivingTime;
    }

    public void setReceivingTime(Date receivingTime) {
        this.receivingTime = receivingTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode == null ? null : operatorCode.trim();
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    public String getInspectionRemark() {
        return inspectionRemark;
    }

    public void setInspectionRemark(String inspectionRemark) {
        this.inspectionRemark = inspectionRemark == null ? null : inspectionRemark.trim();
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

    public String getTransportCompanyCode() {
        return transportCompanyCode;
    }

    public void setTransportCompanyCode(String transportCompanyCode) {
        this.transportCompanyCode = transportCompanyCode == null ? null : transportCompanyCode.trim();
    }

    public String getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(String transportCompany) {
        this.transportCompany = transportCompany == null ? null : transportCompany.trim();
    }

    public String getTransportNumber() {
        return transportNumber;
    }

    public void setTransportNumber(String transportNumber) {
        this.transportNumber = transportNumber == null ? null : transportNumber.trim();
    }

    public String getReturnReasonCode() {
        return returnReasonCode;
    }

    public void setReturnReasonCode(String returnReasonCode) {
        this.returnReasonCode = returnReasonCode == null ? null : returnReasonCode.trim();
    }

    public String getReturnReasonContent() {
        return returnReasonContent;
    }

    public void setReturnReasonContent(String returnReasonContent) {
        this.returnReasonContent = returnReasonContent == null ? null : returnReasonContent.trim();
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake == null ? null : remake.trim();
    }
}