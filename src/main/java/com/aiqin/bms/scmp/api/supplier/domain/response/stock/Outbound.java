package com.aiqin.bms.scmp.api.supplier.domain.response.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("库房出库主表")
public class Outbound {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty("出库状态编码")
    @JsonProperty("outbound_status_code")
    private Byte outboundStatusCode;

    @ApiModelProperty("出库状态名称")
    @JsonProperty("outbound_status_name")
    private String outboundStatusName;

    @ApiModelProperty("出库单号")
    @JsonProperty("outbound_oder_code")
    private String outboundOderCode;

    @ApiModelProperty("出库类型编码")
    @JsonProperty("outbound_type_code")
    private Byte outboundTypeCode;

    @ApiModelProperty("出库类型名称")
    @JsonProperty("outbound_type_name")
    private String outboundTypeName;

    @ApiModelProperty("来源单号")
    @JsonProperty("source_oder_code")
    private String sourceOderCode;

    @ApiModelProperty("出库时间")
    @JsonProperty("outbound_time")
    private Date outboundTime;

    @ApiModelProperty("物流中心编码")
    @JsonProperty("logistics_center_code")
    private String logisticsCenterCode;

    @ApiModelProperty("物流中心名称")
    @JsonProperty("logistics_center_name")
    private String logisticsCenterName;

    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("供货单位编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty("供货单位名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty("预计到货时间")
    @JsonProperty("pre_arrival_time")
    private Date preArrivalTime;

    @ApiModelProperty("WMS单据号")
    @JsonProperty("wms_document_code")
    private String wmsDocumentCode;

    @ApiModelProperty("预计出库数量")
    @JsonProperty("pre_outbound_num")
    private Long preOutboundNum;

    @ApiModelProperty("预计出库主单位数量")
    @JsonProperty("pre_main_unit_num")
    private Long preMainUnitNum;

    @ApiModelProperty("预计含税总金额")
    @JsonProperty("pre_tax_amount")
    private Long preTaxAmount;

    @ApiModelProperty("预计无税总金额")
    @JsonProperty("pre_amount")
    private Long preAmount;

    @ApiModelProperty("预计税额")
    @JsonProperty("pre_tax")
    private Long preTax;

    @ApiModelProperty("实际出库数量")
    @JsonProperty("pra_outbound_num")
    private Long praOutboundNum;

    @ApiModelProperty("实际出库主单位数量")
    @JsonProperty("pra_main_unit_num")
    private Long praMainUnitNum;

    @ApiModelProperty("实际含税总金额")
    @JsonProperty("pra_tax_amount")
    private Long praTaxAmount;

    @ApiModelProperty("实际无税总金额")
    @JsonProperty("pra_amount")
    private Long praAmount;

    @ApiModelProperty("实际税额")
    @JsonProperty("pra_tax")
    private Long praTax;

    @ApiModelProperty("创建人")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty("更新人")
    @JsonProperty("update_by")
    private String updateBy;

    @ApiModelProperty("更新时间")
    @JsonProperty("update_time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Byte getOutboundStatusCode() {
        return outboundStatusCode;
    }

    public void setOutboundStatusCode(Byte outboundStatusCode) {
        this.outboundStatusCode = outboundStatusCode;
    }

    public String getOutboundStatusName() {
        return outboundStatusName;
    }

    public void setOutboundStatusName(String outboundStatusName) {
        this.outboundStatusName = outboundStatusName == null ? null : outboundStatusName.trim();
    }

    public String getOutboundOderCode() {
        return outboundOderCode;
    }

    public void setOutboundOderCode(String outboundOderCode) {
        this.outboundOderCode = outboundOderCode == null ? null : outboundOderCode.trim();
    }

    public Byte getOutboundTypeCode() {
        return outboundTypeCode;
    }

    public void setOutboundTypeCode(Byte outboundTypeCode) {
        this.outboundTypeCode = outboundTypeCode;
    }

    public String getOutboundTypeName() {
        return outboundTypeName;
    }

    public void setOutboundTypeName(String outboundTypeName) {
        this.outboundTypeName = outboundTypeName == null ? null : outboundTypeName.trim();
    }

    public String getSourceOderCode() {
        return sourceOderCode;
    }

    public void setSourceOderCode(String sourceOderCode) {
        this.sourceOderCode = sourceOderCode == null ? null : sourceOderCode.trim();
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getLogisticsCenterCode() {
        return logisticsCenterCode;
    }

    public void setLogisticsCenterCode(String logisticsCenterCode) {
        this.logisticsCenterCode = logisticsCenterCode == null ? null : logisticsCenterCode.trim();
    }

    public String getLogisticsCenterName() {
        return logisticsCenterName;
    }

    public void setLogisticsCenterName(String logisticsCenterName) {
        this.logisticsCenterName = logisticsCenterName == null ? null : logisticsCenterName.trim();
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

    public Date getPreArrivalTime() {
        return preArrivalTime;
    }

    public void setPreArrivalTime(Date preArrivalTime) {
        this.preArrivalTime = preArrivalTime;
    }

    public String getWmsDocumentCode() {
        return wmsDocumentCode;
    }

    public void setWmsDocumentCode(String wmsDocumentCode) {
        this.wmsDocumentCode = wmsDocumentCode == null ? null : wmsDocumentCode.trim();
    }

    public Long getPreOutboundNum() {
        return preOutboundNum;
    }

    public void setPreOutboundNum(Long preOutboundNum) {
        this.preOutboundNum = preOutboundNum;
    }

    public Long getPreMainUnitNum() {
        return preMainUnitNum;
    }

    public void setPreMainUnitNum(Long preMainUnitNum) {
        this.preMainUnitNum = preMainUnitNum;
    }

    public Long getPreTaxAmount() {
        return preTaxAmount;
    }

    public void setPreTaxAmount(Long preTaxAmount) {
        this.preTaxAmount = preTaxAmount;
    }

    public Long getPreAmount() {
        return preAmount;
    }

    public void setPreAmount(Long preAmount) {
        this.preAmount = preAmount;
    }

    public Long getPreTax() {
        return preTax;
    }

    public void setPreTax(Long preTax) {
        this.preTax = preTax;
    }

    public Long getPraOutboundNum() {
        return praOutboundNum;
    }

    public void setPraOutboundNum(Long praOutboundNum) {
        this.praOutboundNum = praOutboundNum;
    }

    public Long getPraMainUnitNum() {
        return praMainUnitNum;
    }

    public void setPraMainUnitNum(Long praMainUnitNum) {
        this.praMainUnitNum = praMainUnitNum;
    }

    public Long getPraTaxAmount() {
        return praTaxAmount;
    }

    public void setPraTaxAmount(Long praTaxAmount) {
        this.praTaxAmount = praTaxAmount;
    }

    public Long getPraAmount() {
        return praAmount;
    }

    public void setPraAmount(Long praAmount) {
        this.praAmount = praAmount;
    }

    public Long getPraTax() {
        return praTax;
    }

    public void setPraTax(Long praTax) {
        this.praTax = praTax;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}