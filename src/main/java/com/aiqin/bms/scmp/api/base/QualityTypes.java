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
public enum QualityTypes {
    DAY(Byte.valueOf("1"),"天"),
    MONTH(Byte.valueOf("2"),"月"),
    YEAR(Byte.valueOf("3"),"年"),
    ;
    private Byte type;
    private String name;

    QualityTypes(Byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public static QualityTypes getByType(Byte type) {
        for (QualityTypes value : QualityTypes.values()) {
            if(Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }
    public static Map<String,QualityTypes> getAll(){
            return Arrays.stream(values()).collect(Collectors.toMap(QualityTypes::getName, Function.identity()));
    }
}
