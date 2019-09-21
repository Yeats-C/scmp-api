package com.aiqin.bms.scmp.api.statistics.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-09-18
 **/
@Data
public class OemSaleRequest {
    @ApiModelProperty(value="日期")
    @JsonProperty("date")
    private String date;

    @ApiModelProperty(value="年")
    @JsonProperty("year")
    private Long year;

    @ApiModelProperty(value="月")
    @JsonProperty("month")
    private Long month;

    @ApiModelProperty(value="销售类型 0 分类 1 品牌")
    @JsonProperty("sale_type")
    private Integer saleType;

    @ApiModelProperty(value="报表类型: 0 年报 1 季报 2 月报 3 周报")
    @JsonProperty("report_type")
    private Integer reportType;

    @ApiModelProperty(value="一级品类编码")
    @JsonProperty("lv1")
    private String lv1;

    public OemSaleRequest(String date, Integer saleType, Integer reportType) {
        this.date = date;
        this.saleType = saleType;
        this.reportType = reportType;
    }
}
