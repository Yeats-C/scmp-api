package com.aiqin.bms.scmp.api.purchase.domain.bi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class BiStockoutRate {

    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty(value="总的库存数")
    @JsonProperty("total_stock_num")
    private Long totalStockNum;

    @ApiModelProperty(value="缺货的数量")
    @JsonProperty("out_stock_num")
    private Long outStockNum;

    @ApiModelProperty(value="缺货占比")
    @JsonProperty("out_stock_rate")
    private BigDecimal outStockRate;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="更新时间")
    @JsonProperty("update_time")
    private Date updateTime;
}