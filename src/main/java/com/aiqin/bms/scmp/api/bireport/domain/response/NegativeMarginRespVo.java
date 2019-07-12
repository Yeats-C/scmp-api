package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("负毛利respVo")
@Data
public class NegativeMarginRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("渠道")
    @JsonProperty("order_original")
    private String orderOriginal;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("品类编码")
    @JsonProperty("product_category_code")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    @JsonProperty("product_category_name")
    private String productCategoryName;

    @ApiModelProperty("品牌编码")
    @JsonProperty("product_brand_code")
    private String productBrandCode;

    @ApiModelProperty("品牌")
    @JsonProperty("product_brand_name")
    private String productBrandName;

    @ApiModelProperty("销售数量")
    @JsonProperty("product_num")
    private Integer productNum;

    @ApiModelProperty("销售成本")
    @JsonProperty("sales_cost")
    private Integer salesCost;

    @ApiModelProperty("渠道销售金额")
    @JsonProperty("qun_order_amount")
    private Integer qunOrderAmount;

    @ApiModelProperty("渠道毛利额")
    @JsonProperty("qun_maori")
    private Integer qunMaori;

    @ApiModelProperty("渠道毛利率")
    @JsonProperty("qun_maori_rate")
    private Integer qunMaoriRate;

    @ApiModelProperty("分销销售金额")
    @JsonProperty("fen_order_amount")
    private Integer fenOrderAmount;

    @ApiModelProperty("分销毛利额")
    @JsonProperty("fen_maori")
    private Integer fenMaori;

    @ApiModelProperty("分销毛利率")
    @JsonProperty("fen_maori_rate")
    private Integer fenMaoriRate;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

}
