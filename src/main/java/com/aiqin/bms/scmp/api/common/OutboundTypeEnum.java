package com.aiqin.bms.scmp.api.common;

import lombok.Getter;

/**
 * @description 出库枚举类型
 */
@Getter
public enum OutboundTypeEnum {

    RETURN_SUPPLY((byte)1,"退供"),
    ALLOCATE((byte)2,"调拨"),
    ORDER((byte)3,"订单"),
    MOVEMENT((byte)4,"移库") ,
//    SUPERVISORY__WAREHOUSE_OUTBOUND((byte)5,"监管仓出库"),
    scrap((byte)6,"报废");
//    PROFIT_LOSS((byte)7,"报损");    // 损溢没有出入库

    private byte code;
    private String name;

    OutboundTypeEnum(byte code,String name){
        this.code = code;
        this.name = name;
    }

    public static OutboundTypeEnum getOutBundTypeEnumByCode(byte codeVal)
    {
        for (OutboundTypeEnum outboundTypeEnum : OutboundTypeEnum.values())
        {
            if (outboundTypeEnum.getCode() == codeVal)
            {
                return outboundTypeEnum;
            }
        }
        return null;
    }

}
