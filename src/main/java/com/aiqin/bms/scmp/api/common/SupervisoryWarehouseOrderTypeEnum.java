package com.aiqin.bms.scmp.api.common;

import lombok.Getter;

import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className SupervisoryWarehouseOrderTypeEnum
 * @date 2019/6/29 18:55
 * @description 监管仓订单类型
 */
@Getter
public enum SupervisoryWarehouseOrderTypeEnum {
    INBOUND(Byte.valueOf("1"),"入库"),
    OUTBOUND(Byte.valueOf("2"),"出库");

    private Byte type;
    private String name;
    SupervisoryWarehouseOrderTypeEnum(Byte type,String name){
        this.type = type;
        this.name = name;
    }
    /**
     * 根据编码获取状态名称
     * @param type
     * @return
     */
    public static String  getName(Byte type)
    {
        for (SupervisoryWarehouseOrderTypeEnum typeEnum : SupervisoryWarehouseOrderTypeEnum.values())
        {
            if (Objects.equals(typeEnum.getType(),type))
            {
                return typeEnum.getName();
            }
        }
        return null;
    }
}
