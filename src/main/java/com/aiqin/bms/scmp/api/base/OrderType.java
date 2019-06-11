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
public enum OrderType {

    //1:配送补货、2:直送补货、3:首单、4:首单赠送

    DISTRIBUTION(1,"配送补货"),
    DIRECT_DELIVERY(2,"直送补货"),
    FIRST_ORDER(3,"首单"),
    FIRST_GIFT(4,"首单赠送");
    private Integer num;
    private String info;

    OrderType(Integer num, String info) {
        this.num = num;
        this.info = info;
    }
    public static Map<Integer,OrderType> getAllStatus(){
        return Arrays.asList(values()).stream().collect(Collectors.toMap(OrderType::getNum, Function.identity(),(k1,k2)->k2));
    }
}
