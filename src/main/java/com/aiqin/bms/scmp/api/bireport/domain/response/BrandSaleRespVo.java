package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@ApiModel("品牌促销respVo")
@Data
public class BrandSaleRespVo {
    @ApiModelProperty("ID")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("月份")
    @JsonProperty("year_month")
    private String yearMonth;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("渠道编码")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty("渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty("门店类型code")
    @JsonProperty("store_type_code")
    private String storeTypeCode;

    @ApiModelProperty("门店类型名称")
    @JsonProperty("store_type")
    private String storeType;

    @ApiModelProperty("数据类型code")
    @JsonProperty("data_type_code")
    private String dataTypeCode;

    @ApiModelProperty("数据类型name")
    @JsonProperty("data_type")
    private String dataType;

    @ApiModelProperty("品类编码")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("当前销售(出库)成本")
    @JsonProperty("stock_cost")
    private String stockCost;

    @ApiModelProperty("当前渠道销售(出库)金额")
    @JsonProperty("channel_sale_amount")
    private Long channelSaleAmount;

    @ApiModelProperty("上月渠道销售(出库)金额")
    @JsonProperty("last_month_channel_amount_daily")
    private Long lastMonthChannelAmountDaily;

    @ApiModelProperty("当前渠道销售毛利额")
    @JsonProperty("channel_gross_margin")
    private Long channelGrossMargin;

    @ApiModelProperty("上月渠道销售毛利额")
    @JsonProperty("last_month_channel_gross_margin")
    private Long lastMonthChannelGrossMargin;

    @ApiModelProperty("渠道销售(出库)金额环比增长%")
    @JsonProperty("channel_sales_sequential_add")
    private BigDecimal channelSalesSequentialAdd;

    @ApiModelProperty("渠道销售毛利额环比增长%")
    @JsonProperty("channel_maori_sequential_add")
    private BigDecimal channelMaoriSequentialAdd;

    @ApiModelProperty("当前分销销售(出库)金额")
    @JsonProperty("distribution_amount_daily")
    private Long distributionAmountDaily;

    @ApiModelProperty("上月分销销售(出库)金额")
    @JsonProperty("last_month_distribution_amount_daily")
    private Long lastMonthDistributionAmountDaily;

    @ApiModelProperty("当前分销销售毛利额")
    @JsonProperty("distribution_amount")
    private Long distributionAmount;

    @ApiModelProperty("上月分销销售毛利额")
    @JsonProperty("last_month_distribution_amount")
    private Long lastMonthDistributionAmount;

    @ApiModelProperty("分销销售(出库)金额环比增长%")
    @JsonProperty("distribution_sales_sequential_add")
    private BigDecimal distributionSalesSequentialAdd;

    @ApiModelProperty("分销销售毛利额环比增长%")
    @JsonProperty("distribution_maori_sequential_add")
    private BigDecimal distributionMaoriSequentialAdd;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;

    // 下面 暂时 没用
    @ApiModelProperty("当期渠道销售金额合计")
    @JsonProperty("channel_current_sale_amounts")
    private Long channelCurrentSaleAmounts;

    @ApiModelProperty("上期渠道销售金额合计")
    @JsonProperty("channel_previous_sales_amounts")
    private Long channelPreviousSalesAmounts;

    @ApiModelProperty("当期渠道毛利额合计")
    @JsonProperty("channel_current_gross_margins")
    private Long channelCurrentGrossMargins;

    @ApiModelProperty("上期渠道毛利额合计")
    @JsonProperty("channel_previous_gross_margins")
    private Long channelPreviousGrossMargins;

    @ApiModelProperty("渠道销售额环比增长%合计")
    @JsonProperty("channel_sale_link_ratios")
    private BigDecimal channelSaleLinkRatios;

    @ApiModelProperty("渠道毛利额环比增长%合计")
    @JsonProperty("channel_gross_margin_link_ratios")
    private BigDecimal channelGrossMarginLinkRatios;

    @ApiModelProperty("当期分销销售金额合计")
    @JsonProperty("distribution_current_sale_amounts")
    private Long distributionCurrentSaleAmounts;

    @ApiModelProperty("上期分销销售金额合计")
    @JsonProperty("distribution_previous_sales_amounts")
    private Long distributionPreviousSalesAmounts;

    @ApiModelProperty("当期分销毛利额合计")
    @JsonProperty("distribution_current_gross_margins")
    private Long distributionCurrentGrossMargins;

    @ApiModelProperty("上期分销毛利额合计")
    @JsonProperty("distribution_previous_gross_margins")
    private Long distributionPreviousGrossMargins;

    @ApiModelProperty("分销销售额环比增长%合计")
    @JsonProperty("distribution_sale_link_ratios")
    private BigDecimal distributionSaleLinkRatios;

    @ApiModelProperty("分销毛利额环比增长%合计")
    @JsonProperty("distribution_gross_margin_link_ratios")
    private BigDecimal distributionGrossMarginLinkRatios;



}
