package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("品类促销respVo")
@Data
public class CategorySaleRespVo {
    @ApiModelProperty("ID")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("department_code")
    private String departmentCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("department_name")
    private String departmentName;

    @ApiModelProperty("渠道编码")
    @JsonProperty("channel_code")
    private String channelCode;

    @ApiModelProperty("渠道")
    @JsonProperty("channel_name")
    private String channelName;

    @ApiModelProperty("门店类型code")
    @JsonProperty("store_type_code")
    private String storeTypeCode;

    @ApiModelProperty("门店类型名称")
    @JsonProperty("store_type_name")
    private String storeTypeName;

    @ApiModelProperty("数据类型code")
    @JsonProperty("data_style_code")
    private String dataStyleCode;

    @ApiModelProperty("数据类型name")
    @JsonProperty("data_style_name")
    private String dataStyleName;

    @ApiModelProperty("品类编码")
    @JsonProperty("category_type_code")
    private String categoryTypeCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_type_name")
    private String categoryTypeName;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌名称")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("当期渠道销售金额")
    @JsonProperty("qun_current_sale_amount")
    private String qunCurrentSaleAmount;

    @ApiModelProperty("上期渠道销售金额")
    @JsonProperty("qun_previous_sales_amount")
    private String qunPreviousSalesAmount;

    @ApiModelProperty("当期渠道毛利额")
    @JsonProperty("qun_current_gross_margin")
    private String qunCurrentGrossMargin;

    @ApiModelProperty("上期渠道毛利额")
    @JsonProperty("qun_category_type_name")
    private String qunPreviousGrossMargin;

    @ApiModelProperty("渠道销售额环比增长%")
    @JsonProperty("qun_sale_link_ratio")
    private Double qunSaleLinkRatio;

    @ApiModelProperty("渠道毛利额环比增长%")
    @JsonProperty("qun_gross_margin_link_ratio")
    private Double qunGrossMarginLinkRatio;

    @ApiModelProperty("当期分销销售金额")
    @JsonProperty("fen_current_sale_amount")
    private String fenCurrentSaleAmount;

    @ApiModelProperty("上期分销销售金额")
    @JsonProperty("fen_previous_sales_amount")
    private String fenPreviousSalesAmount;

    @ApiModelProperty("当期分销毛利额")
    @JsonProperty("fen_current_gross_margin")
    private String fenCurrentGrossMargin;

    @ApiModelProperty("上期分销毛利额")
    @JsonProperty("fen_category_type_name")
    private String fenPreviousGrossMargin;

    @ApiModelProperty("分销销售额环比增长%")
    @JsonProperty("fen_sale_link_ratio")
    private Double fenSaleLinkRatio;

    @ApiModelProperty("分销毛利额环比增长%")
    @JsonProperty("fen_gross_margin_link_ratio")
    private Double fenGrossMarginLinkRatio;
}
