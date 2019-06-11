package com.aiqin.bms.scmp.api.supplier.domain.response.variableprice;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("聚合价格详情")
@Data
public class LogPriceList {
    private PriceDetailedResponse priceDetailedResponse;

    private PriceLogResponse priceLogResponse;

}
