package com.aiqin.bms.scmp.api.bireport.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("月累计品类毛利率情况实体Model")
@Data
public class BiMonthCumulativeGrossProfitMargin {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("月份")
    @JsonProperty("month")
    private String month;

    @ApiModelProperty("月累计")
    @JsonProperty("cumulative_month")
    private Integer cumulativeMonth;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("渠道编码")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty("渠道名称")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty("门店类型")
    @JsonProperty("store_type")
    private Integer storeType;

    @ApiModelProperty("数据类型")
    @JsonProperty("data_type")
    private String dataType;

    @ApiModelProperty("一级品类")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("销售额")
    @JsonProperty("amount")
    private String amount;

    @ApiModelProperty("销售成本")
    @JsonProperty("sales_cost")
    private Integer salesCost;

    @ApiModelProperty("毛利额")
    @JsonProperty("maori")
    private Integer maori;

    @ApiModelProperty("毛利率")
    @JsonProperty("maori_rate")
    private Integer maoriRate;

    @ApiModelProperty("上期毛利率")
    @JsonProperty("last_maori_rate")
    private Integer lastMaoriRate;

    @ApiModelProperty("环比")
    @JsonProperty("sequential")
    private Integer sequential;

    @ApiModelProperty("同比")
    @JsonProperty("compared_same")
    private Integer comparedSame;

}
