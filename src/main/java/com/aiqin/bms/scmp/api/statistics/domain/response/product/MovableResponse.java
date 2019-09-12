package com.aiqin.bms.scmp.api.statistics.domain.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-09-11
 **/
@Data
public class MovableResponse {

    @ApiModelProperty(value="部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value="品类code")
    @JsonProperty("lv1")
    private String lv1;

    @ApiModelProperty(value="品类")
    @JsonProperty("lv1_category_name")
    private String lv1CategoryName;

    @ApiModelProperty(value="渠道code")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty(value="渠道名")
    @JsonProperty("price_channel_name")
    private String priceChannelName;
}
