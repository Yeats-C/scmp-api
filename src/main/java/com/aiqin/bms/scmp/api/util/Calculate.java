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
    public static Long computeNoTaxPrice(Long price,Long taxRate){
        //不含税单价=含税单价/(1+税率)
        return BigDecimal.valueOf(price).divide(BigDecimal.valueOf(1).add(BigDecimal.valueOf(taxRate ).divide(BigDecimal.valueOf(10000),4,BigDecimal.ROUND_UP)), 0, BigDecimal.ROUND_UP).longValue();
    }
}
