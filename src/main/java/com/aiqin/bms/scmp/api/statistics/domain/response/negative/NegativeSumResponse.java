package com.aiqin.bms.scmp.api.statistics.domain.response.negative;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-08-26
 **/
@Data
@Api(tags = "负毛利统计总计实体")
public class NegativeSumResponse {

    @ApiModelProperty(value="部门下渠道的集合")
    @JsonProperty("dept_list")
    private List<NegativeDeptResponse> deptList;

    @ApiModelProperty(value="销售数量")
    @JsonProperty("sales_num")
    private Long salesNum;

    @ApiModelProperty(value="渠道销售额")
    @JsonProperty("channel_sales_amount")
    private Long channelSalesAmount;

    @ApiModelProperty(value="渠道销售额同比")
    @JsonProperty("channel_sales_amount_yearonyear")
    private BigDecimal channelSalesAmountYearonyear;

    @ApiModelProperty(value="渠道成本")
    @JsonProperty("channel_sales_cost")
    private Long channelSalesCost;

    @ApiModelProperty(value="渠道毛利")
    @JsonProperty("channel_margin")
    private Long channelMargin;

    @ApiModelProperty(value="渠道毛利同比")
    @JsonProperty("channel_margin_yearonyear")
    private BigDecimal channelMarginYearonyear;

    @ApiModelProperty(value="分销销售额")
    @JsonProperty("distribution_sales_amount")
    private Long distributionSalesAmount;

    @ApiModelProperty(value="分销销售额同比")
    @JsonProperty("distribution_sales_amount_yearonyear")
    private BigDecimal distributionSalesAmountYearonyear;

    @ApiModelProperty(value="分销毛利")
    @JsonProperty("distribution_margin")
    private Long distributionMargin;

    @ApiModelProperty(value="分销毛利同比")
    @JsonProperty("distribution_margin_yearonyear")
    private BigDecimal distributionMarginYearonyear;
}
