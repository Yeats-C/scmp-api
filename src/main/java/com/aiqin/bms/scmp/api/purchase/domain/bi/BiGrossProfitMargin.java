package com.aiqin.bms.scmp.api.purchase.domain.bi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class BiGrossProfitMargin {

    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty(value = "总营业额")
    @JsonProperty("total_turnover")
    private BigDecimal totalTurnover;

    @ApiModelProperty(value = "库存成本")
    @JsonProperty("inventory_cost")
    private BigDecimal inventoryCost;

    @ApiModelProperty(value = "毛利额(总利润)")
    @JsonProperty("gross_profit_margin")
    private BigDecimal grossProfitMargin;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonProperty("update_time")
    private Date updateTime;
}