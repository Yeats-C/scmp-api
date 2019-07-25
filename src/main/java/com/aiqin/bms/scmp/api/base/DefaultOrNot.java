package com.aiqin.bms.scmp.api.base;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className QualityAssuranceManagements
 * @date 2019/7/18 17:27
 * @description TODO
 */
@Getter
public enum DefaultOrNot {
    DEFALUT(Byte.valueOf("2"),"是",(byte)1),
    DEFALUT_NOT(Byte.valueOf("3"),"否",(byte)0),
    ;
    private Byte type;
    private String name;
    private Byte value;

    DefaultOrNot(Byte type, String name, Byte value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public static DefaultOrNot getByType(Byte type) {
        for (DefaultOrNot value : DefaultOrNot.values()) {
            if(Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }
    public static Map<String, DefaultOrNot> getAll() {
        return Arrays.stream(values()).collect(Collectors.toMap(DefaultOrNot::getName, Function.identity(),(k1, k2)->k2));
    }
}
