package com.aiqin.bms.scmp.api.bireport.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("月销售情况实体Model")
@Data
public class BiMonthlySales {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("月份")
    @JsonProperty("month")
    private String month;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("销售额")
    @JsonProperty("amount")
    private Integer amount;

    @ApiModelProperty("预算额")
    @JsonProperty("budget")
    private Integer budget;

    @ApiModelProperty("达成率")
    @JsonProperty("yield_rate")
    private Integer yieldRate;

}
