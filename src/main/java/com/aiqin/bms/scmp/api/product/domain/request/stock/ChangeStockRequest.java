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

    @ApiModelProperty("操作类型 1.锁定库存 2.减库存并解锁 3.解锁库存. 4.减库存 5.加并锁定库存 6.加库存 " +
            "7.加在途 8.减在途 9.锁转移(锁定库存移入/移出)'")
    @JsonProperty(value = "operation_type")
    private Integer operationType;

//    @ApiModelProperty("订单编号")
//    @JsonProperty(value = "order_code")
//    private String orderCode;
//
//    @ApiModelProperty("订单类型")
//    @JsonProperty(value = "order_type")
//    private Integer orderType;

    @ApiModelProperty("库存集合")
    @JsonProperty(value = "stock_list")
    private List<StockInfoRequest> stockList;

    @ApiModelProperty("批次库存集合")
    @JsonProperty(value = "stock_batch_list")
    private List<StockBatchInfoRequest> stockBatchList;
}
