package com.aiqin.bms.scmp.api.base;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 *  采购方式
 * @author: zth
 * @date: 2019-03-11
 * @time: 10:50
 */
@Getter
public enum PurchaseType {
//    0,人工创建,1自动创建的直供单
    MANUAL_CREATION((byte)0,"配送"),
    AUTOMATIC_CREATION((byte)1,"直送");
    private Byte num;
    private String desc;

    PurchaseType(Byte num, String desc) {
        this.num = num;
        this.desc = desc;
    }
    public static Map<Byte,PurchaseType> getAllStatus(){
        return Arrays.stream(values()).collect(Collectors.toMap(PurchaseType::getNum, Function.identity(),(k1,k2)->k2));
    }
}
