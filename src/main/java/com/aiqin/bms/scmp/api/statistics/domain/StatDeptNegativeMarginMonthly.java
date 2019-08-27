package com.aiqin.bms.scmp.api.statistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel
public class StatDeptNegativeMarginMonthly {
    @ApiModelProperty(value="id")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value="年")
    @JsonProperty("stat_year")
    private Long statYear;

    @ApiModelProperty(value="月")
    @JsonProperty("stat_month")
    private Long statMonth;

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
    @JsonProperty("sales_num")
    private Long salesNum;

    @ApiModelProperty(value="渠道销售额")
    @JsonProperty("channel_sales_amount")
    private Long channelSalesAmount;

    @ApiModelProperty(value="渠道销售额同比")
    @JsonProperty("channel_sales_amount_yearonyear")
    private BigDecimal channelSalesAmountYearonyear;

    @ApiModelProperty(value="渠道成本")
    @JsonProperty("channel_sales_cost")
    private Long channelSalesCost;

    @ApiModelProperty(value="渠道毛利")
    @JsonProperty("channel_margin")
    private Long channelMargin;

    @ApiModelProperty(value="渠道毛利同比")
    @JsonProperty("channel_margin_yearonyear")
    private BigDecimal channelMarginYearonyear;

    @ApiModelProperty(value="分销销售额")
    @JsonProperty("distribution_sales_amount")
    private Long distributionSalesAmount;

    @ApiModelProperty(value="分销销售额同比")
    @JsonProperty("distribution_sales_amount_yearonyear")
    private BigDecimal distributionSalesAmountYearonyear;

    @ApiModelProperty(value="分销毛利")
    @JsonProperty("distribution_margin")
    private Long distributionMargin;

    @ApiModelProperty(value="分销毛利同比")
    @JsonProperty("distribution_margin_yearonyear")
    private BigDecimal distributionMarginYearonyear;

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

    public Long getStatMonth() {
        return statMonth;
    }

    public void setStatMonth(Long statMonth) {
        this.statMonth = statMonth;
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

    public Long getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(Long salesNum) {
        this.salesNum = salesNum;
    }

    public Long getChannelSalesAmount() {
        return channelSalesAmount;
    }

    public void setChannelSalesAmount(Long channelSalesAmount) {
        this.channelSalesAmount = channelSalesAmount;
    }

    public BigDecimal getChannelSalesAmountYearonyear() {
        return channelSalesAmountYearonyear;
    }

    public void setChannelSalesAmountYearonyear(BigDecimal channelSalesAmountYearonyear) {
        this.channelSalesAmountYearonyear = channelSalesAmountYearonyear;
    }

    public Long getChannelSalesCost() {
        return channelSalesCost;
    }

    public void setChannelSalesCost(Long channelSalesCost) {
        this.channelSalesCost = channelSalesCost;
    }

    public Long getChannelMargin() {
        return channelMargin;
    }

    public void setChannelMargin(Long channelMargin) {
        this.channelMargin = channelMargin;
    }

    public BigDecimal getChannelMarginYearonyear() {
        return channelMarginYearonyear;
    }

    public void setChannelMarginYearonyear(BigDecimal channelMarginYearonyear) {
        this.channelMarginYearonyear = channelMarginYearonyear;
    }

    public Long getDistributionSalesAmount() {
        return distributionSalesAmount;
    }

    public void setDistributionSalesAmount(Long distributionSalesAmount) {
        this.distributionSalesAmount = distributionSalesAmount;
    }

    public BigDecimal getDistributionSalesAmountYearonyear() {
        return distributionSalesAmountYearonyear;
    }

    public void setDistributionSalesAmountYearonyear(BigDecimal distributionSalesAmountYearonyear) {
        this.distributionSalesAmountYearonyear = distributionSalesAmountYearonyear;
    }

    public Long getDistributionMargin() {
        return distributionMargin;
    }

    public void setDistributionMargin(Long distributionMargin) {
        this.distributionMargin = distributionMargin;
    }

    public BigDecimal getDistributionMarginYearonyear() {
        return distributionMarginYearonyear;
    }

    public void setDistributionMarginYearonyear(BigDecimal distributionMarginYearonyear) {
        this.distributionMarginYearonyear = distributionMarginYearonyear;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}