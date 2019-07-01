package com.aiqin.bms.scmp.api.common;

import lombok.Getter;

/**
 * @author knight.xie
 * @version 1.0
 * @className AllocationTypeEnmu
 * @date 2019/7/1 17:29
 * @description TODO
 */
@Getter
public enum AllocationTypeEnmu {
    ALLOCATION(Byte.valueOf("1"),"调拨"),
    MOVE(Byte.valueOf("2"),"移库"),
    SCRAP(Byte.valueOf("3"),"报废"),
    ;
    private Byte type;
    private String typeName;

    AllocationTypeEnmu(Byte type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
}
