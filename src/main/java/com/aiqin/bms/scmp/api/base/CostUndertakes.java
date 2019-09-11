package com.aiqin.bms.scmp.api.base;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className CostUndertakes
 * @date 2019/9/3 16:15
 */
@Getter
public enum CostUndertakes {
    YES(Byte.valueOf("0"),"甲方"),
    NO(Byte.valueOf("1"),"乙方"),
    ;
    private Byte type;
    private String name;

    CostUndertakes(Byte type, String name) {
        this.type = type;
        this.name = name;
    }
    public static Map<String,CostUndertakes> getAll() {
        return Arrays.stream(values()).collect(Collectors.toMap(CostUndertakes::getName, Function.identity(),(k1, k2)->k2));
    }
}
