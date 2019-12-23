package com.aiqin.bms.scmp.api.base;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className PurchaseSaleStockType
 * @date 2019/11/6
 */
@ApiModel("进销存状态枚举")
@Getter
public enum PurchaseSaleStockType {
    /**
     * 进销存状态枚举
     **/
    STOCK((byte) 0, "库存"),
    PURCHASE((byte) 1, "采购"),
    SALE((byte) 2, "销售"),
    STORE_SALE((byte) 3, "门店销售"),;
    private Byte type;
    private String name;

    PurchaseSaleStockType(Byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public static PurchaseSaleStockType getSkuTypeEnumByType(Byte type) {
        for (PurchaseSaleStockType value : PurchaseSaleStockType.values()) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }
}
