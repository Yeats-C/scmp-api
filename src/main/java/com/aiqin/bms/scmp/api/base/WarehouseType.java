package com.aiqin.bms.scmp.api.base;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
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
public enum WarehouseType {
    BIG(Byte.valueOf("2"),"大货仓位"),
    SMALL(Byte.valueOf("3"),"小货仓位"),
    ;
    private Byte type;
    private String name;

    WarehouseType(Byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Map<String, WarehouseType> getAll() {
        return Arrays.stream(values()).collect(Collectors.toMap(WarehouseType::getName, Function.identity(),(k1, k2)->k2));
    }
}
