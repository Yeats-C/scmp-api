package com.aiqin.bms.scmp.api.statistics.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-09-04
 **/
@Data
@Api(tags = "销售统计请求实体")
public class SaleRequest {

    @ApiModelProperty(value="日期")
    @JsonProperty("date")
    private String date;

    @ApiModelProperty(value="年")
    @JsonProperty("year")
    private Long year;

    @ApiModelProperty(value="月")
    @JsonProperty("month")
    private Long month;

    @ApiModelProperty(value="部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="渠道code")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty(value="组织类型: 0 公司 1 部门")
    @JsonProperty("type")
    private Integer type;

    @ApiModelProperty(value="报表类型: 0 年报 2 月报")
    @JsonProperty("report_type")
    private Integer reportType;

    @ApiModelProperty(value="数据类型 0 经营数据,1 部门数据")
    @JsonProperty("data_type_code")
    private Integer dataTypeCode;

    @ApiModelProperty(value="商品属性 1 A品，2 B品，3 C品，5 D品，6 其他")
    @JsonProperty("product_property_code")
    private Integer productPropertyCode;

    public SaleRequest(String date, Integer type, Integer reportType, Integer dataTypeCode, Integer productPropertyCode, String productSortCode) {
        this.date = date;
        this.type = type;
        this.reportType = reportType;
        this.dataTypeCode = dataTypeCode;
        this.productPropertyCode = productPropertyCode;
        this.productSortCode = productSortCode;
    }
}
