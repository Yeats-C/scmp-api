package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;


@ApiModel("品牌促销respVo")
@Data
public class BrandSaleRespVo {
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

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("当期渠道销售金额")
    @JsonProperty("channel_current_sale_amount")
    private String channelCurrentSaleAmount;

    @ApiModelProperty("上期渠道销售金额")
    @JsonProperty("channel_previous_sales_amount")
    private String channelPreviousSalesAmount;

    @ApiModelProperty("当期渠道毛利额")
    @JsonProperty("channel_current_gross_margin")
    private String channelCurrentGrossMargin;

    @ApiModelProperty("上期渠道毛利额")
    @JsonProperty("channel_previous_gross_margin")
    private String channelPreviousGrossMargin;

    @ApiModelProperty("渠道销售额环比增长%")
    @JsonProperty("channel_sale_link_ratio")
    private Double channelSaleLinkRatio;

    @ApiModelProperty("渠道毛利额环比增长%")
    @JsonProperty("channel_gross_margin_link_ratio")
    private Double channelGrossMarginLinkRatio;

    @ApiModelProperty("当期分销销售金额")
    @JsonProperty("distribution_current_sale_amount")
    private String distributionCurrentSaleAmount;

    @ApiModelProperty("上期分销销售金额")
    @JsonProperty("distribution_previous_sales_amount")
    private String distributionPreviousSalesAmount;

    @ApiModelProperty("当期分销毛利额")
    @JsonProperty("distribution_current_gross_margin")
    private String distributionCurrentGrossMargin;

    @ApiModelProperty("上期分销毛利额")
    @JsonProperty("distribution_previous_gross_margin")
    private String distributionPreviousGrossMargin;

    @ApiModelProperty("分销销售额环比增长%")
    @JsonProperty("distribution_sale_link_ratio")
    private Double distributionSaleLinkRatio;

    @ApiModelProperty("分销毛利额环比增长%")
    @JsonProperty("distribution_gross_margin_link_ratio")
    private Double distributionGrossMarginLinkRatio;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;


}
