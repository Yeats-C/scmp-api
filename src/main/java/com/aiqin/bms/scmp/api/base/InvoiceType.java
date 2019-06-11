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
 * @time: 11:05
 */
@Getter
public enum InvoiceType {
    //1不开、2增普、3增专
    NOTOPEN("1","不开"),
    ZENGPU("2","增普"),
    ADDITIONAL("3","增专");
    private String type;
    private String desc;
    InvoiceType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public static Map<String,InvoiceType> getAllStatus(){
        return Arrays.stream(values()).collect(Collectors.toMap(InvoiceType::getType, Function.identity(),(k1, k2)->k2));
    }
}
