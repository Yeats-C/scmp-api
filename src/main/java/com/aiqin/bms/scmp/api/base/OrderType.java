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
    //变更 2019年7月3日20:19:18 1直送，2配送，3辅采直送
    DIRECT_DELIVERY(1,"直送补货"),
    DISTRIBUTION(2,"配送补货"),
    DIRECT_DELIVERY_FUCAI(3,"3辅采直送");
//    FIRST_ORDER(3,"首单"),
//    FIRST_GIFT(4,"首单赠送");
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
