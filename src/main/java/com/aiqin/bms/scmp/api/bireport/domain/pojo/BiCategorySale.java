package com.aiqin.bms.scmp.api.bireport.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("品类促销实体Model")
@Data
public class BiCategorySale {

    @ApiModelProperty("ID")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("月份")
    @JsonProperty("month")
    private int month;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("department_code")
    private String departmentCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("department_name")
    private String departmentName;

    @ApiModelProperty("渠道")
    @JsonProperty("channel_name")
    private String channelName;

    @ApiModelProperty("门店类型")
    @JsonProperty("store_type")
    private String storeType;

    @ApiModelProperty("数据类型")
    @JsonProperty("data_type")
    private String dataType;

    @ApiModelProperty("品类编码")
    @JsonProperty("category_type_code")
    private String categoryTypeCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_type_name")
    private String categoryTypeName;

    @ApiModelProperty("当期销售金额")
    @JsonProperty("current_sale_amount")
    private String currentSaleAmount;

    @ApiModelProperty("当期毛利额")
    @JsonProperty("current_gross_margin")
    private String currentGrossMargin;

    @ApiModelProperty("上期销售金额")
    @JsonProperty("previous_sales_amount")
    private String previousSalesAmount;

    @ApiModelProperty("上期毛利额")
    @JsonProperty("category_type_name")
    private String previousGrossMargin;

    @ApiModelProperty("销售额环比")
    @JsonProperty("sale_link_ratio")
    private String saleLinkRatio;

    @ApiModelProperty("毛利额环比")
    @JsonProperty("gross_margin_link_ratio")
    private String grossMarginLinkRatio;


}
