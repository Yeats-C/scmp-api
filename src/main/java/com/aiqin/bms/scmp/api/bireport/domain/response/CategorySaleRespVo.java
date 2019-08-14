package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@ApiModel("品类促销respVo")
@Data
public class CategorySaleRespVo {
    @ApiModelProperty("ID")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("月份")
    @JsonProperty("month")
    private String month;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("department_code")
    private String departmentCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("department_name")
    private String departmentName;

    @ApiModelProperty("渠道编码")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("渠道")
    @JsonProperty("order_original")
    private String orderOriginal;

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

    @ApiModelProperty("当期渠道销售金额")
    @JsonProperty("channel_current_sale_amount")
    private Long channelCurrentSaleAmount;

    @ApiModelProperty("上期渠道销售金额")
    @JsonProperty("channel_previous_sales_amount")
    private Long channelPreviousSalesAmount;

    @ApiModelProperty("当期渠道毛利额")
    @JsonProperty("channel_current_gross_margin")
    private Long channelCurrentGrossMargin;

    @ApiModelProperty("上期渠道毛利额")
    @JsonProperty("channel_previous_gross_margin")
    private Long channelPreviousGrossMargin;

    @ApiModelProperty("渠道销售额环比增长%")
    @JsonProperty("channel_sale_link_ratio")
    private Double channelSaleLinkRatio;

    @ApiModelProperty("渠道毛利额环比增长%")
    @JsonProperty("channel_gross_margin_link_ratio")
    private Double channelGrossMarginLinkRatio;

    @ApiModelProperty("当期分销销售金额")
    @JsonProperty("distribution_current_sale_amount")
    private Long distributionCurrentSaleAmount;

    @ApiModelProperty("上期分销销售金额")
    @JsonProperty("distribution_previous_sales_amount")
    private Long distributionPreviousSalesAmount;

    @ApiModelProperty("当期分销毛利额")
    @JsonProperty("distribution_current_gross_margin")
    private Long distributionCurrentGrossMargin;

    @ApiModelProperty("上期分销毛利额")
    @JsonProperty("distribution_previous_gross_margin")
    private Long distributionPreviousGrossMargin;

    @ApiModelProperty("分销销售额环比增长%")
    @JsonProperty("distribution_sale_link_ratio")
    private Double distributionSaleLinkRatio;

    @ApiModelProperty("分销毛利额环比增长%")
    @JsonProperty("distribution_gross_margin_link_ratio")
    private Double distributionGrossMarginLinkRatio;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

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
    private Double channelSaleLinkRatios;

    @ApiModelProperty("渠道毛利额环比增长%合计")
    @JsonProperty("channel_gross_margin_link_ratios")
    private Double channelGrossMarginLinkRatios;

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
    private Double distributionSaleLinkRatios;

    @ApiModelProperty("分销毛利额环比增长%合计")
    @JsonProperty("distribution_gross_margin_link_ratios")
    private Double distributionGrossMarginLinkRatios;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;

}