package com.aiqin.bms.scmp.api.statistics.domain.response.negative;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: zhao shuai
 * @create: 2019-08-26
 **/
@Data
@Api(tags = "负毛利统计品类实体")
public class NegativeCategoryResponse {

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

    @ApiModelProperty(value="品类code")
    @JsonProperty("lv1")
    private String lv1;

    @ApiModelProperty(value="品类名称")
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
