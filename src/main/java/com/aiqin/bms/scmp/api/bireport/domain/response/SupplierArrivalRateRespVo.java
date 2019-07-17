package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("供应商到货率respVo")
@Data
public class SupplierArrivalRateRespVo implements Serializable {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_run_time")
    private String beginRunTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_run_time")
    private String finishRunTime;

    @ApiModelProperty("供应商code")
    @JsonProperty(value = "supplier_code")
    private String supplierCode;

    @ApiModelProperty("供应商name")
    @JsonProperty(value = "supplier_name")
    private String supplierName;

    @ApiModelProperty("一级品类编号")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("一级品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("仓库编码")
    @JsonProperty(value = "transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty(value = "transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("订货数量")
    @JsonProperty("pre_inbound_num")
    private String preInboundNum;

    @ApiModelProperty("订货金额")
    @JsonProperty("pre_tax_amount")
    private String preTaxAmount;

    @ApiModelProperty("入库数量")
    @JsonProperty("pra_inbound_num")
    private String praInboundNum;

    @ApiModelProperty("入库金额")
    @JsonProperty("pra_tax_amount")
    private String praTaxAmount;

    @ApiModelProperty("入库金额满足率")
    @JsonProperty("pra_tax_amount_rate")
    private String praTaxAmountRate;

    @ApiModelProperty("采购组负责人")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeginRunTime() {
        return beginRunTime;
    }

    public void setBeginRunTime(String beginRunTime) {
        this.beginRunTime = beginRunTime;
    }

    public String getFinishRunTime() {
        return finishRunTime;
    }

    public void setFinishRunTime(String finishRunTime) {
        this.finishRunTime = finishRunTime;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTransportCenterCode() {
        return transportCenterCode;
    }

    public void setTransportCenterCode(String transportCenterCode) {
        this.transportCenterCode = transportCenterCode;
    }

    public String getPreInboundNum() {
        return preInboundNum;
    }

    public void setPreInboundNum(String preInboundNum) {
        this.preInboundNum = preInboundNum;
    }

    public String getPreTaxAmount() {
        return preTaxAmount;
    }

    public void setPreTaxAmount(String preTaxAmount) {
        this.preTaxAmount = preTaxAmount;
    }

    public String getPraInboundNum() {
        return praInboundNum;
    }

    public void setPraInboundNum(String praInboundNum) {
        this.praInboundNum = praInboundNum;
    }

    public String getPraTaxAmount() {
        return praTaxAmount;
    }

    public void setPraTaxAmount(String praTaxAmount) {
        this.praTaxAmount = praTaxAmount;
    }

    public String getPraTaxAmountRate() {
        return praTaxAmountRate;
    }

    public void setPraTaxAmountRate(String praTaxAmountRate) {
        this.praTaxAmountRate = praTaxAmountRate;
    }

    public String getResponsiblePersonName() {
        return responsiblePersonName;
    }

    public void setResponsiblePersonName(String responsiblePersonName) {
        this.responsiblePersonName = responsiblePersonName;
    }
}
