package com.aiqin.bms.scmp.api.product.domain.request.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2020-04-08
 **/
@Data
public class ChangeStockRequest {

    @ApiModelProperty("操作类型")
    @JsonProperty(value = "operation_type")
    private Integer operationType;

    @ApiModelProperty("订单编号")
    @JsonProperty(value = "order_code")
    private String orderCode;

    @ApiModelProperty("订单类型")
    @JsonProperty(value = "order_type")
    private Integer orderType;

    @ApiModelProperty("库存集合")
    @JsonProperty(value = "stock_list")
    private List<StockInfoRequest> stockList;

    @ApiModelProperty("批次库存集合")
    @JsonProperty(value = "stock_batch_list")
    private List<StockBatchInfoRequest> stockBatchList;
}
