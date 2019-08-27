package com.aiqin.bms.scmp.api.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel
public class StatDeptNegativeMarginQuarterly {
    @ApiModelProperty(value="id")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty(value="季度")
    @JsonProperty("quarter")
    private Long quarter;

    @ApiModelProperty(value="所属部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value="渠道code")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty(value="渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty(value="品类code")
    @JsonProperty("lv1")
    private String lv1;

    @ApiModelProperty(value="品类")
    @JsonProperty("lv1_category_name")
    private String lv1CategoryName;

    @ApiModelProperty(value="销售数量")
    @JsonProperty("quarter_sales_num")
    private Long quarterSalesNum;

    @ApiModelProperty(value="渠道销售额")
    @JsonProperty("quarter_channel_sales_amount")
    private Long quarterChannelSalesAmount;

    @ApiModelProperty(value="渠道销售额同比")
    @JsonProperty("q_channel_sales_amount_yearonyear")
    private BigDecimal qChannelSalesAmountYearonyear;

    @ApiModelProperty(value="渠道成本")
    @JsonProperty("quarter_channel_sales_cost")
    private Long quarterChannelSalesCost;

    @ApiModelProperty(value="渠道毛利")
    @JsonProperty("quarter_channel_margin")
    private Long quarterChannelMargin;

    @ApiModelProperty(value="渠道毛利同比")
    @JsonProperty("q_channel_margin_yearonyear")
    private BigDecimal qChannelMarginYearonyear;

    @ApiModelProperty(value="分销销售额")
    @JsonProperty("quarter_distribution_sales_amount")
    private Long quarterDistributionSalesAmount;

    @ApiModelProperty(value="分销销售额同比")
    @JsonProperty("q_distribution_sales_amount_yearonyear")
    private BigDecimal qDistributionSalesAmountYearonyear;

    @ApiModelProperty(value="分销毛利")
    @JsonProperty("quarter_distribution_margin")
    private Long quarterDistributionMargin;

    @ApiModelProperty(value="分销毛利同比")
    @JsonProperty("q_distribution_margin_yearonyear")
    private BigDecimal qDistributionMarginYearonyear;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatYear() {
        return statYear;
    }

    public void setStatYear(Long statYear) {
        this.statYear = statYear;
    }

    public Long getQuarter() {
        return quarter;
    }

    public void setQuarter(Long quarter) {
        this.quarter = quarter;
    }

    public String getProductSortCode() {
        return productSortCode;
    }

    public void setProductSortCode(String productSortCode) {
        this.productSortCode = productSortCode;
    }

    public String getProductSortName() {
        return productSortName;
    }

    public void setProductSortName(String productSortName) {
        this.productSortName = productSortName;
    }

    public String getPriceChannelCode() {
        return priceChannelCode;
    }

    public void setPriceChannelCode(String priceChannelCode) {
        this.priceChannelCode = priceChannelCode;
    }

    public String getPriceChannelName() {
        return priceChannelName;
    }

    public void setPriceChannelName(String priceChannelName) {
        this.priceChannelName = priceChannelName;
    }

    public String getLv1() {
        return lv1;
    }

    public void setLv1(String lv1) {
        this.lv1 = lv1;
    }

    public String getLv1CategoryName() {
        return lv1CategoryName;
    }

    public void setLv1CategoryName(String lv1CategoryName) {
        this.lv1CategoryName = lv1CategoryName;
    }

    public Long getQuarterSalesNum() {
        return quarterSalesNum;
    }

    public void setQuarterSalesNum(Long quarterSalesNum) {
        this.quarterSalesNum = quarterSalesNum;
    }

    public Long getQuarterChannelSalesAmount() {
        return quarterChannelSalesAmount;
    }

    public void setQuarterChannelSalesAmount(Long quarterChannelSalesAmount) {
        this.quarterChannelSalesAmount = quarterChannelSalesAmount;
    }

    public BigDecimal getqChannelSalesAmountYearonyear() {
        return qChannelSalesAmountYearonyear;
    }

    public void setqChannelSalesAmountYearonyear(BigDecimal qChannelSalesAmountYearonyear) {
        this.qChannelSalesAmountYearonyear = qChannelSalesAmountYearonyear;
    }

    public Long getQuarterChannelSalesCost() {
        return quarterChannelSalesCost;
    }

    public void setQuarterChannelSalesCost(Long quarterChannelSalesCost) {
        this.quarterChannelSalesCost = quarterChannelSalesCost;
    }

    public Long getQuarterChannelMargin() {
        return quarterChannelMargin;
    }

    public void setQuarterChannelMargin(Long quarterChannelMargin) {
        this.quarterChannelMargin = quarterChannelMargin;
    }

    public BigDecimal getqChannelMarginYearonyear() {
        return qChannelMarginYearonyear;
    }

    public void setqChannelMarginYearonyear(BigDecimal qChannelMarginYearonyear) {
        this.qChannelMarginYearonyear = qChannelMarginYearonyear;
    }

    public Long getQuarterDistributionSalesAmount() {
        return quarterDistributionSalesAmount;
    }

    public void setQuarterDistributionSalesAmount(Long quarterDistributionSalesAmount) {
        this.quarterDistributionSalesAmount = quarterDistributionSalesAmount;
    }

    public BigDecimal getqDistributionSalesAmountYearonyear() {
        return qDistributionSalesAmountYearonyear;
    }

    public void setqDistributionSalesAmountYearonyear(BigDecimal qDistributionSalesAmountYearonyear) {
        this.qDistributionSalesAmountYearonyear = qDistributionSalesAmountYearonyear;
    }

    public Long getQuarterDistributionMargin() {
        return quarterDistributionMargin;
    }

    public void setQuarterDistributionMargin(Long quarterDistributionMargin) {
        this.quarterDistributionMargin = quarterDistributionMargin;
    }

    public BigDecimal getqDistributionMarginYearonyear() {
        return qDistributionMarginYearonyear;
    }

    public void setqDistributionMarginYearonyear(BigDecimal qDistributionMarginYearonyear) {
        this.qDistributionMarginYearonyear = qDistributionMarginYearonyear;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}