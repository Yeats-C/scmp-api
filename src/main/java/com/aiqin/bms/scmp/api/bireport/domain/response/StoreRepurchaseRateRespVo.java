package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@ApiModel("门店复购率respVo")
@Data
public class StoreRepurchaseRateRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("渠道code")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("渠道名称")
    @JsonProperty("order_original")
    private String orderOriginal;

    @ApiModelProperty("省区")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty("市")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty("区")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty("品类编码")
    @JsonProperty("category_code")
    private String categoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("category_name")
    private String categoryName;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("销售数量")
    @JsonProperty("product_num")
    private Long productNum;

    @ApiModelProperty("渠道销售金额")
    @JsonProperty("channel_order_amount")
    private Long channelOrderAmount;

    @ApiModelProperty("分销销售金额")
    @JsonProperty("distribution_order_amount")
    private Long distributionOrderAmount;

    @ApiModelProperty("购物频次")
    @JsonProperty("shopping_frequency")
    private Long shoppingFrequency;

    @ApiModelProperty("复购率")
    @JsonProperty("after_buy_rate")
    private BigDecimal afterBuyRate;

    @ApiModelProperty("计算时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("返回列名")
    @JsonProperty("column_list")
    private List<Map> columnList;
}
