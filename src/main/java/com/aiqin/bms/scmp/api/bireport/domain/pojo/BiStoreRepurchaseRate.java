package com.aiqin.bms.scmp.api.bireport.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("门店复购率实体Model")
@Data
public class BiStoreRepurchaseRate {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("省区")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty("市")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty("区")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty("渠道")
    @JsonProperty("order_original")
    private String orderOriginal;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("销售数量")
    @JsonProperty("product_num")
    private Integer productNum;

    @ApiModelProperty("渠道销售金额")
    @JsonProperty("fen_order_amount")
    private Integer fenOrderAmount;

    @ApiModelProperty("分销销售金额")
    @JsonProperty("qun_order_amount")
    private Integer qunOrderAmount;

    @ApiModelProperty("购物频次")
    @JsonProperty("shopping_frequency")
    private Integer shoppingFrequency;

    @ApiModelProperty("复购率")
    @JsonProperty("after_buy_rate")
    private Integer afterBuyRate;

    @ApiModelProperty("入库时间")
    @JsonProperty("create_time")
    private Integer createTime;
}
