package com.aiqin.bms.scmp.api.product.domain.request.stock;

import com.aiqin.bms.scmp.api.product.domain.pojo.StockMonthBatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@Api("月份批次转换参数")
public class StockMonthRequest {

    @ApiModelProperty("操作类型 1.德邦 2.京东")
    @JsonProperty(value = "operation_type")
    private Integer operationType;

    @ApiModelProperty("月份批次信息")
    @JsonProperty(value = "stock_list")
    private List<StockMonthBatch> stockList;
}
