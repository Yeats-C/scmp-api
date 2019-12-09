package com.aiqin.bms.scmp.api.product.domain.dto.changeprice;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleCountDTO {
    private String skuCode;
    private BigDecimal saleNum = BigDecimal.ZERO;
}
