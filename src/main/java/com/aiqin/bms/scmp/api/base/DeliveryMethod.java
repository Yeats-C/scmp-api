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
 * @date: 2019-03-11
 * @time: 11:13
 */
@Getter
public enum DeliveryMethod {
//    (0:送货上门,1:送货市级市,2:无减免)
    DELIVERY("0","送货上门"),
    DELIVERYCITYLEVELCITY("1","送货市级市"),
    NOREDUCTION("2","无减免");
    private String type;
    private String desc;
    DeliveryMethod(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public static Map<String,DeliveryMethod> getAllStatus(){
        return Arrays.stream(values()).collect(Collectors.toMap(DeliveryMethod::getType, Function.identity(),(k1, k2)->k2));
    }
}
