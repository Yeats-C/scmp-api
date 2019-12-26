package com.aiqin.bms.scmp.api.util;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-03-07
 * @time: 21:52
 */
public class Calculate {
    /**
     * 根据含税单价和税率计算不含税单价
     * @param price
     * @param taxRate
     * @return
     */
    public static BigDecimal computeNoTaxPrice(BigDecimal price,BigDecimal taxRate){
        //不含税单价=含税单价/(1+税率)
        BigDecimal tax = BigDecimal.valueOf(100).add(taxRate);
        return price.divide(tax.divide(BigDecimal.valueOf(100),4,BigDecimal.ROUND_HALF_UP), 4, BigDecimal.ROUND_HALF_UP);

        //return price.divide(BigDecimal.valueOf(1).add(taxRate).divide(BigDecimal.valueOf(100),4,BigDecimal.ROUND_HALF_UP), 4, BigDecimal.ROUND_HALF_UP);
    }
}
