package com.aiqin.bms.scmp.api.base;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className SkuTypeEnum
 * @date 2019/7/6 15:44
 * @description TODO
 */
@ApiModel("SKU类型枚举")
@Getter
public enum SkuTypeEnum {
    SKU(Byte.valueOf("0"),"商品"),
    GIFT(Byte.valueOf("1"),"赠品"),
    COMBINATION(Byte.valueOf("2"),"组合商品"),
    ;
    private Byte type;
    private String name;

    SkuTypeEnum(Byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public static SkuTypeEnum getSkuTypeEnumByType(Byte type) {
        for (SkuTypeEnum value : SkuTypeEnum.values()) {
            if(Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }
}
