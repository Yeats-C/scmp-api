package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("月累计品类销售情况respVo")
@Data
public class MonthCumulativeGrossProfitMarginRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("时间begin")
    @JsonProperty("begin_create_time")
    private String beginCreateTime;

    @ApiModelProperty("时间finish")
    @JsonProperty("finish_create_time")
    private String finishCreateTime;

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

    @ApiModelProperty("门店类型")
    @JsonProperty("store_type")
    private String storeType;

    @ApiModelProperty("数据类型")
    @JsonProperty("data_style")
    private String dataStyle;

    @ApiModelProperty("品类编码")
    @JsonProperty("category_type_code")
    private String categoryTypeCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_type_name")
    private String categoryTypeName;

    @ApiModelProperty("销售成本")
    @JsonProperty("sales_cost")
    private String salesCost;

    @ApiModelProperty("渠道销售额")
    @JsonProperty("qun_amount")
    private String qunAmount;

    @ApiModelProperty("渠道毛利额")
    @JsonProperty("qun_maori")
    private String qunMaori;

    @ApiModelProperty("渠道毛利率")
    @JsonProperty("qun_maori_rate")
    private String qunMaoriRate;

    @ApiModelProperty("上期毛利率")
    @JsonProperty("qun_last_maori_rate")
    private String qunLastMaoriRate;

    @ApiModelProperty("渠道同比")
    @JsonProperty("qun_compared_same")
    private String qunComparedSame;

    @ApiModelProperty("渠道环比")
    @JsonProperty("qun_sequential")
    private String qunSequential;

    @ApiModelProperty("分销销售额")
    @JsonProperty("fen_amount")
    private String fenAmount;

    @ApiModelProperty("分销毛利额")
    @JsonProperty("fen_maori")
    private String fenMaori;

    @ApiModelProperty("分销毛利率")
    @JsonProperty("fen_maori_rate")
    private String fenMaoriRate;

    @ApiModelProperty("上期分销毛利率")
    @JsonProperty("fen_last_maori_rate")
    private String fenLastMaoriRate;

    @ApiModelProperty("分销同比")
    @JsonProperty("fen_compared_same")
    private String fenComparedSame;

    @ApiModelProperty("分销环比")
    @JsonProperty("fen_sequential")
    private String fenSequential;

}
