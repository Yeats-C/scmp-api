package com.aiqin.bms.scmp.api.statistics.domain.response.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: zhao shuai
 * @create: 2019-09-04
 **/
@Data
@Api(tags = "销售统计门店类型实体")
public class SaleStoreResponse {

    @ApiModelProperty(value="部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value="渠道code")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty(value="渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty(value="商品属性code(1代表A品，2代表B品，3代表C品，5代表D品，6代表其他)")
    @JsonProperty("product_property_code")
    private Long productPropertyCode;

    @ApiModelProperty(value="商品属性")
    @JsonProperty("product_property_name")
    private String productPropertyName;

    @ApiModelProperty(value="门店类型code(0代表直营，1代表萌贝树，2代表小红马，3代表加盟）")
    @JsonProperty("store_type_code")
    private Long storeTypeCode;

    @ApiModelProperty(value="门店类型")
    @JsonProperty("store_type")
    private String storeType;

    @ApiModelProperty(value="数据类型code(0 代表 经营数据,1 代表 部门数据)")
    @JsonProperty("data_type_code")
    private Long dataTypeCode;

    @ApiModelProperty(value="数据类型")
    @JsonProperty("data_type")
    private String dataType;

    @ApiModelProperty(value="渠道成本")
    @JsonProperty("channel_sales_cost")
    private BigDecimal channelSalesCost;

    @ApiModelProperty(value="渠道销售额")
    @JsonProperty("channel_sales_amount")
    private BigDecimal channelSalesAmount;

    @ApiModelProperty(value="渠道销售额同比")
    @JsonProperty("channel_sales_amount_yearonyear")
    private BigDecimal channelSalesAmountYearonyear;

    @ApiModelProperty(value="渠道销售额环比")
    @JsonProperty("channel_sales_amount_link_rela")
    private BigDecimal channelSalesAmountLinkRela;

    @ApiModelProperty(value="渠道毛利")
    @JsonProperty("channel_margin")
    private BigDecimal channelMargin;

    @ApiModelProperty(value="渠道毛利同比")
    @JsonProperty("channel_margin_yearonyear")
    private BigDecimal channelMarginYearonyear;

    @ApiModelProperty(value="渠道毛利环比")
    @JsonProperty("channel_margin_link_rela")
    private BigDecimal channelMarginLinkRela;

    @ApiModelProperty(value="渠道毛利率")
    @JsonProperty("channel_margin_rate")
    private BigDecimal channelMarginRate;

    @ApiModelProperty(value="分销成本")
    @JsonProperty("distribution_sales_cost")
    private BigDecimal distributionSalesCost;

    @ApiModelProperty(value="分销销售额")
    @JsonProperty("distribution_sales_amount")
    private BigDecimal distributionSalesAmount;

    @ApiModelProperty(value="分销销售额同比")
    @JsonProperty("distribution_sales_amount_yearonyear")
    private BigDecimal distributionSalesAmountYearonyear;

    @ApiModelProperty(value="分销销售额环比")
    @JsonProperty("distribution_sales_amount_link_rela")
    private BigDecimal distributionSalesAmountLinkRela;

    @ApiModelProperty(value="分销毛利")
    @JsonProperty("distribution_margin")
    private BigDecimal distributionMargin;

    @ApiModelProperty(value="分销毛利同比")
    @JsonProperty("distribution_margin_yearonyear")
    private BigDecimal distributionMarginYearonyear;

    @ApiModelProperty(value="分销毛利环比")
    @JsonProperty("distribution_margin_link_rela")
    private BigDecimal distributionMarginLinkRela;

    @ApiModelProperty(value="分销毛利率")
    @JsonProperty("distribution_margin_rate")
    private BigDecimal distributionMarginRate;

    @ApiModelProperty(value="渠道占比")
    @JsonProperty("channe_rate")
    private BigDecimal channeRate;

    @ApiModelProperty(value="分销占比")
    @JsonProperty("distribution_rate")
    private BigDecimal distributionRate;

    @ApiModelProperty(value="渠道同比销售金额")
    @JsonProperty("channel_amount_last_year")
    private BigDecimal channelAmountLastYear;

    @ApiModelProperty(value="渠道环比销售金额")
    @JsonProperty("channel_amount_last_month")
    private BigDecimal channelAmountLastMonth;

    @ApiModelProperty(value="渠道同比毛利额")
    @JsonProperty("channel_margin_last_year")
    private BigDecimal channelMarginLastYear;

    @ApiModelProperty(value="渠道环比毛利额")
    @JsonProperty("channel_margin_last_month")
    private BigDecimal channelMarginLastMonth;

    @ApiModelProperty(value="分销同比销售金额")
    @JsonProperty("distribution_amount_last_year")
    private BigDecimal distributionAmountLastYear;

    @ApiModelProperty(value="分销环比销售金额")
    @JsonProperty("distribution_amount_last_month")
    private BigDecimal distributionAmountLastMonth;

    @ApiModelProperty(value="分销同比毛利额")
    @JsonProperty("distribution_margin_last_year")
    private BigDecimal distributionMarginLastYear;

    @ApiModelProperty(value="分销环比毛利额")
    @JsonProperty("distribution_margin_last_month")
    private BigDecimal distributionMarginLastMonth;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
