package com.aiqin.bms.scmp.api.base;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-17
 * @time: 17:58
 */
@Getter
public enum ReturnOrderType {

    //1.客户退货、2.缺货退货、3.售后退货
    CUSTOMER__RETURN(1,"客户退货"),
    OUT_OF_STOCK_RETURN(2,"缺货退货"),
    AFTER_SALES_RETURN(3,"售后退货");
    private Integer num;
    private String info;

    ReturnOrderType(Integer num, String info) {
        this.num = num;
        this.info = info;
    }

    public static Map<Integer, ReturnOrderType> getAllStatus(){
        return Arrays.asList(values()).stream().collect(Collectors.toMap(ReturnOrderType::getNum, Function.identity(),(k1, k2)->k2));
    }
}
