package com.aiqin.bms.scmp.api.supplier.domain.response.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("库房管理入库主表")
public class Inbound {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty("入库状态编码")
    @JsonProperty("inbound_status_code")
    private Integer inboundStatusCode;

    @ApiModelProperty("入库状态名称")
    @JsonProperty("inbound_status_name")
    private String inboundStatusName;

    @ApiModelProperty("入库单号")
    @JsonProperty("inbound_oder_code")
    private String inboundOderCode;

    @ApiModelProperty("入库类型编码")
    @JsonProperty("inbound_type_code")
    private Integer inboundTypeCode;

    @ApiModelProperty("入库类型名称")
    @JsonProperty("inbound_type_name")
    private String inboundTypeName;

    @ApiModelProperty("来源单号")
    @JsonProperty("source_oder_code")
    private String sourceOderCode;

    @ApiModelProperty("入库时间")
    @JsonProperty("inbound_time")
    private Date inboundTime;

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

    @ApiModelProperty("预计入库数量")
    @JsonProperty("pre_inbound_num")
    private Long preInboundNum;

    @ApiModelProperty("预计主单位数量")
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

    @ApiModelProperty("实际入库数量")
    @JsonProperty("pra_inbound_num")
    private Long praInboundNum;

    @ApiModelProperty("实际主单位数量")
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

    public Integer getInboundStatusCode() {
        return inboundStatusCode;
    }

    public void setInboundStatusCode(Integer inboundStatusCode) {
        this.inboundStatusCode = inboundStatusCode;
    }

    public String getInboundStatusName() {
        return inboundStatusName;
    }

    public void setInboundStatusName(String inboundStatusName) {
        this.inboundStatusName = inboundStatusName == null ? null : inboundStatusName.trim();
    }

    public String getinboundOderCode() {
        return inboundOderCode;
    }

    public void setinboundOderCode(String inboundOderCode) {
        this.inboundOderCode = inboundOderCode;
    }

    public Integer getInboundTypeCode() {
        return inboundTypeCode;
    }

    public void setInboundTypeCode(Integer inboundTypeCode) {
        this.inboundTypeCode = inboundTypeCode;
    }

    public String getInboundTypeName() {
        return inboundTypeName;
    }

    public void setInboundTypeName(String inboundTypeName) {
        this.inboundTypeName = inboundTypeName == null ? null : inboundTypeName.trim();
    }

    public String getSourceOderCode() {
        return sourceOderCode;
    }

    public void setSourceOderCode(String sourceOderCode) {
        this.sourceOderCode = sourceOderCode == null ? null : sourceOderCode.trim();
    }

    public Date getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(Date inboundTime) {
        this.inboundTime = inboundTime;
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

    public Long getPreInboundNum() {
        return preInboundNum;
    }

    public void setPreInboundNum(Long preInboundNum) {
        this.preInboundNum = preInboundNum;
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

    public Long getPraInboundNum() {
        return praInboundNum;
    }

    public void setPraInboundNum(Long praInboundNum) {
        this.praInboundNum = praInboundNum;
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