package com.aiqin.bms.scmp.api.statistics.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-09-16
 **/
@Data
public class InventoryStatisticsRequest {

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

    @ApiModelProperty(value="报表类型: 0 年报 2 月报")
    @JsonProperty("report_type")
    private Integer reportType;

    @ApiModelProperty(value = "所属部门")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="采购组code")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    public InventoryStatisticsRequest(String date, Integer type, Integer reportType, String productSortCode) {
        this.date = date;
        this.type = type;
        this.reportType = reportType;
        this.productSortCode = productSortCode;
    }
}
