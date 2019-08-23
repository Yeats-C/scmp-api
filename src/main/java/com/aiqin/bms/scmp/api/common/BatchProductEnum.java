package com.aiqin.bms.scmp.api.common;

import lombok.Getter;

/**
 * @author knight.xie
 * @version 1.0
 * @className BatchProductEnum
 * @date 2019/6/19 15:59
 */
@Getter
public enum BatchProductEnum {

    BATCH_PRODUCT(Byte.valueOf("0"),"批次产品"),
    UN_BATCH_PRODUCT(Byte.valueOf("1"),"非批次产品");
    private Byte status;
    private String name;
    BatchProductEnum(Byte status, String name) {
        this.status = status;
        this.name = name;
    }
}
