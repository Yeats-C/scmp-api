package com.aiqin.bms.scmp.api.statistics.domain.response;

import com.aiqin.bms.scmp.api.statistics.domain.StatComStoreRepurchaseRate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-08-23
 **/
@Data
public class StoreRepurchaseRateSubtotalResponse {

    @ApiModelProperty(value="各省的复购率")
    @JsonProperty("subtotal_list")
    private List<StatComStoreRepurchaseRate> subtotalList;

    @ApiModelProperty(value="部门code")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty(value="部门名")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty(value="销售数量小计")
    @JsonProperty("sales_num")
    private Long salesNum;

    @ApiModelProperty(value="渠道销售金额小计")
    @JsonProperty("channel_sales_amount")
    private Long channelSalesAmount;

    @ApiModelProperty(value="分销销售金额小计")
    @JsonProperty("distribution_sales_amount")
    private Long distributionSalesAmount;

    @ApiModelProperty(value="购物频次小计")
    @JsonProperty("shopping_frequency")
    private Long shoppingFrequency;

    @ApiModelProperty(value="复购率小计")
    @JsonProperty("repurchase_rate")
    private BigDecimal repurchaseRate;

}
