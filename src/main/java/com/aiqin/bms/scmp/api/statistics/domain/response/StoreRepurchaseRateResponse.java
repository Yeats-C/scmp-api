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
public class StoreRepurchaseRateResponse {

    @ApiModelProperty(value="各部门的复购率")
    @JsonProperty("sum_list")
    private List<StoreRepurchaseRateSubtotalResponse> sumList;

    @ApiModelProperty(value="销售数量合计")
    @JsonProperty("sales_num")
    private Long salesNum;

    @ApiModelProperty(value="渠道销售金额合计")
    @JsonProperty("channel_sales_amount")
    private Long channelSalesAmount;

    @ApiModelProperty(value="分销销售金额合计")
    @JsonProperty("distribution_sales_amount")
    private Long distributionSalesAmount;

    @ApiModelProperty(value="购物频次合计")
    @JsonProperty("shopping_frequency")
    private Long shoppingFrequency;

    @ApiModelProperty(value="连续2次购买次数合计")
    @JsonProperty("repurchase_num")
    private Long repurchaseNum;

    @ApiModelProperty(value="购买总次数合计")
    @JsonProperty("purchase_num")
    private Long purchaseNum;

    @ApiModelProperty(value="复购率合计")
    @JsonProperty("repurchase_rate")
    private BigDecimal repurchaseRate;

}
