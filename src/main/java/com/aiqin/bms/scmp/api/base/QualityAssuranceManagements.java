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
public enum QualityAssuranceManagements {
    YES(Byte.valueOf("0"),"管理"),
    NO(Byte.valueOf("1"),"不管理"),
    ;
    private Byte type;
    private String name;

    QualityAssuranceManagements(Byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public static QualityAssuranceManagements getByType(Byte type) {
        for (QualityAssuranceManagements value : QualityAssuranceManagements.values()) {
            if(Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }
}
