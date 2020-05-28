package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AllocationDetailRequest {

    @ApiModelProperty(value="wms 传回来的实际数量")
    @JsonProperty("actual_count")
    private Long actualCount;

    @ApiModelProperty(value="wms 传回来的实际金额")
    @JsonProperty("actual_amount")
    private BigDecimal actualAmount;

    @ApiModelProperty(value="行号")
    @JsonProperty("line_code")
    private Integer lineCode;
}
