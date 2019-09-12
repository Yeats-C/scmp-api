package com.aiqin.bms.scmp.api.statistics.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-09-09
 **/
@Data
public class ProductRequest {

    @ApiModelProperty(value="日期")
    @JsonProperty("date")
    private String date;

    @ApiModelProperty(value="年")
    @JsonProperty("year")
    private Long year;

    @ApiModelProperty(value="月")
    @JsonProperty("month")
    private Long month;

    @ApiModelProperty(value="组织类型: 0 公司 1 部门")
    @JsonProperty("type")
    private Integer type;

    @ApiModelProperty(value = "所属部门")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="品类code")
    @JsonProperty("lv1")
    private String lv1;

    @ApiModelProperty(value="渠道code")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    public ProductRequest(String date, Integer type, String productSortCode) {
        this.date = date;
        this.type = type;
        this.productSortCode = productSortCode;
    }
}
