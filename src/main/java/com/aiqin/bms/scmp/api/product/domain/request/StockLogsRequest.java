package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StockLogsRequest extends PagesRequest {
    @ApiModelProperty("库存编码")
    @JsonProperty(value = "stock_code")
    private String stockCode;
}
