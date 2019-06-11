package com.aiqin.bms.scmp.api.common;

import lombok.Getter;

/**
 * @author knight.xie
 * @version 1.0
 * @className StockStatusEnum
 * @date 2019/1/8 19:52
 * @description 商品库存状态
 */
@Getter
public enum StockStatusEnum {
    ADD_STOCK((byte)1,"加库存"),
    LOCK_STOCK((byte)2,"锁定库存"),
    REDUCE_UNLOCK_STOCK((byte)3,"减库存并解锁"),
    UNLOCK_STOCK((byte)4,"解锁库存"),
    REDUCE_STOCK((byte)5,"减库存"),
    LOCK_TRANSFER_STOCK((byte)6,"锁定库存转移"),
    ADD_UNLOCK_STOCK((byte)7,"加并锁定库存");
    private byte code;
    private String name;
    StockStatusEnum(byte code, String name){
        this.code = code;
        this.name = name;
    }

    /**
     * 获取StockStatusEnum根据code
     * @param codeVal
     * @return
     */
    public static StockStatusEnum getStockStatusEnumByCode(byte codeVal)
    {
        for (StockStatusEnum stockStatusEnum : StockStatusEnum.values())
        {
            if (stockStatusEnum.getCode() == codeVal)
            {
                return stockStatusEnum;
            }
        }
        return null;
    }

}
