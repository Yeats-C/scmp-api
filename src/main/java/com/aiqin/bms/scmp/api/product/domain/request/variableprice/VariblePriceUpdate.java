package com.aiqin.bms.scmp.api.product.domain.request.variableprice;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("批量更新价格信息")
@Data
public class VariblePriceUpdate {

    private String skuCode;

    private BigDecimal newPrice;

    private String priceTypeCode;

    private String updateBy;
}
