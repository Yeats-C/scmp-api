package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("sku库存成本日志")
@Data
public class TaxCostLogStock {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty("昨天日期")
    @JsonProperty("tax_date")
    private String taxDate;

    @ApiModelProperty("SKU编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("SKU名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("仓储类型")
    @JsonProperty("warehous_type")
    private Long warehousType;

    @ApiModelProperty("仓储名称")
    @JsonProperty("warehous_name")
    private String warehousName;

    @ApiModelProperty("成本日期总库存数")
    @JsonProperty("stock_sum_num")
    private Long stockSumNum;

    @ApiModelProperty("成本日期含税总成本")
    @JsonProperty("stock_sum_cost")
    private Long stockSumCost;

    @ApiModelProperty("成本日期含税成本")
    @JsonProperty("stock_tax_cost")
    private Long stockTaxCost;

    @ApiModelProperty("创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaxDate() {
        return taxDate;
    }

    public void setTaxDate(String taxDate) {
        this.taxDate = taxDate;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getWarehousType() {
        return warehousType;
    }

    public void setWarehousType(Long warehousType) {
        this.warehousType = warehousType;
    }

    public String getWarehousName() {
        return warehousName;
    }

    public void setWarehousName(String warehousName) {
        this.warehousName = warehousName;
    }

    public Long getStockSumNum() {
        return stockSumNum;
    }

    public void setStockSumNum(Long stockSumNum) {
        this.stockSumNum = stockSumNum;
    }

    public Long getStockSumCost() {
        return stockSumCost;
    }

    public void setStockSumCost(Long stockSumCost) {
        this.stockSumCost = stockSumCost;
    }

    public Long getStockTaxCost() {
        return stockTaxCost;
    }

    public void setStockTaxCost(Long stockTaxCost) {
        this.stockTaxCost = stockTaxCost;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
