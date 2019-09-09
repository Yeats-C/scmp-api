package com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("退货入库申请单")
public class ReturnOrderInfoApplyInbound {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("退货单号")
    private String returnOrderCode;

    @ApiModelProperty("入库申请单号")
    private String code;

    @ApiModelProperty("入库时间")
    private Date inboundTime;

    @ApiModelProperty("状态1新建2完成")
    private Integer status;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("0未同步1已同步2同步失败")
    private Integer beSyn;

    @ApiModelProperty("同步失败原因")
    private String synFailedReason;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("公司编码")
    private String companyCode;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Date getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(Date inboundTime) {
        this.inboundTime = inboundTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getBeSyn() {
        return beSyn;
    }

    public void setBeSyn(Integer beSyn) {
        this.beSyn = beSyn;
    }

    public String getSynFailedReason() {
        return synFailedReason;
    }

    public void setSynFailedReason(String synFailedReason) {
        this.synFailedReason = synFailedReason == null ? null : synFailedReason.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }
}