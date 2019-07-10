package com.aiqin.bms.scmp.api.bireport.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("负毛利实体Model")
@Data
public class BiNegativeMargin {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

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

    @ApiModelProperty("销售数量")
    @JsonProperty("product_num")
    private Integer productNum;

    @ApiModelProperty("销售金额")
    @JsonProperty("order_amount")
    private Integer orderAmount;

    @ApiModelProperty("销售成本")
    @JsonProperty("sales_cost")
    private Integer salesCost;

    @ApiModelProperty("毛利额")
    @JsonProperty("maori")
    private Integer maori;

    @ApiModelProperty("毛利率")
    @JsonProperty("maori_rate")
    private Integer maoriRate;

}
