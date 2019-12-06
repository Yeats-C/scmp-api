package com.aiqin.bms.scmp.api.bireport.domain.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("渠道退货金额respVo")
@Data
public class DashboardReturnAmountRespVo {

    @ApiModelProperty("id")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("年月")
    @JsonProperty("year_month")
    private String yearMonth;

    @ApiModelProperty("退货数量")
    @JsonProperty("single_count")
    private Long singleCount;

    @ApiModelProperty("退货金额")
    @JsonProperty("amt")
    private BigDecimal amt;

    @ApiModelProperty("部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("部门名")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("渠道code")
    @JsonProperty("price_channel_code")
    private String priceChannelCode;

    @ApiModelProperty("渠道")
    @JsonProperty("price_channel_name")
    private String priceChannelName;

    @ApiModelProperty("运行时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty("更新时间")
    @JsonProperty("update_time")
    private String updateTime;


}
