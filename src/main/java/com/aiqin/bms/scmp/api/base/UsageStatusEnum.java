package com.aiqin.bms.scmp.api.base;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum UsageStatusEnum {

    ENABLE(Byte.valueOf("1"),"在用"),
    DISABLE(Byte.valueOf("0"),"禁用"),
    ;
    private Byte type;
    private String name;
    UsageStatusEnum(Byte type,String name){
        this.type = type;
        this.name = name;
    }

    public static UsageStatusEnum getByType(Byte type) {
        for (UsageStatusEnum value : UsageStatusEnum.values()) {
            if(Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }
    public static Map<String, UsageStatusEnum> getAll() {
        return Arrays.stream(values()).collect(Collectors.toMap(UsageStatusEnum::getName, Function.identity(),(k1, k2)->k2));
    }
}
