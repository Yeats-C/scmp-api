package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("月销售情况respVo")
@Data
public class MonthlyGrossMarginRespVo {

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

    @ApiModelProperty("销售成本")
    @JsonProperty("sales_cost")
    private String salesCost;

    @ApiModelProperty("渠道销售额")
    @JsonProperty("qun_order_amount")
    private String qunOrderAmount;

    @ApiModelProperty("渠道毛利额")
    @JsonProperty("qun_maori")
    private String qunMaori;

    @ApiModelProperty("渠道毛利率")
    @JsonProperty("qun_maori_rate")
    private Double qunMaoriRate;

    @ApiModelProperty("渠道同比")
    @JsonProperty("qun_compared_same")
    private String qunComparedSame;

    @ApiModelProperty("渠道环比")
    @JsonProperty("qun_sequential")
    private String qunSequential;

    @ApiModelProperty("分销销售额")
    @JsonProperty("fen_order_amount")
    private String fenOrderAmount;

    @ApiModelProperty("分销毛利额")
    @JsonProperty("fen_maori")
    private String fenMaori;

    @ApiModelProperty("分销毛利率")
    @JsonProperty("fen_maori_rate")
    private Double fenMaoriRate;

    @ApiModelProperty("分销同比")
    @JsonProperty("fen_compared_same")
    private String fenComparedSame;

    @ApiModelProperty("分销环比")
    @JsonProperty("fen_sequential")
    private String fenSequential;
}
