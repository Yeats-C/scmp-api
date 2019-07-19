package com.aiqin.bms.scmp.api.base;

import lombok.Getter;

import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className QualityAssuranceManagements
 * @date 2019/7/18 17:27
 * @description TODO
 */
@Getter
public enum Generals {
    YES(Byte.valueOf("0"),"是"),
    NO(Byte.valueOf("1"),"否"),
    ;
    private Byte type;
    private String name;

    Generals(Byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Generals getByType(Byte type) {
        for (Generals value : Generals.values()) {
            if(Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }
}
