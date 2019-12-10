package com.aiqin.bms.scmp.api.product.domain.response.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-12-06
 **/
@Data
public class StockTransportResponse {

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="仓库名称")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty("仓库库存数量")
    @JsonProperty("stock_count")
    private Long stockCount;

    @ApiModelProperty(value="仓库类型 0.主仓 1.备仓")
    @JsonProperty("transport_center_type")
    private Integer transportCenterType;

}
