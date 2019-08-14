package com.aiqin.bms.scmp.api.util;

import java.math.BigDecimal;

/**
 * 数字转换类
 * @author null
 */
public class NumberConvertUtils {
    public static Long stringParseLong(String s) {
        BigDecimal temp = new BigDecimal(s);
        temp = temp.multiply(BigDecimal.valueOf(100));
        return temp.longValue();
    }
    public static BigDecimal stringParseBigDecimal(String s) {
        BigDecimal temp = BigDecimal.valueOf(Double.valueOf(s));
        return temp;
    }

    public static BigDecimal bigDecimalMultiplyHundred(BigDecimal divide) {
        divide.multiply(BigDecimal.valueOf(10000));
        return divide;
    }
}