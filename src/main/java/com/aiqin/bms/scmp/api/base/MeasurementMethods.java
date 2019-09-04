package com.aiqin.bms.scmp.api.base;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className MeasurementMethods
 * @date 2019/9/3 17:21
 */
@ApiModel("计量方式")
@Getter
public enum  MeasurementMethods {

    NO_TAX(Byte.valueOf("0"),"未税"),
    TAX(Byte.valueOf("1"),"含税"),
    ;
    private Byte type;
    private String name;

    MeasurementMethods(Byte type, String name) {
        this.type = type;
        this.name = name;
    }
    public static Map<String,MeasurementMethods> getAll() {
        return Arrays.stream(values()).collect(Collectors.toMap(MeasurementMethods::getName, Function.identity(),(k1, k2)->k2));
    }
}
