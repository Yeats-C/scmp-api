package com.aiqin.bms.scmp.api.common;

import lombok.Getter;

/**
 * @author knight.xie
 * @version 1.0
 * @className OutboundTypeEnum
 * @date 2019/1/8 19:52
 * @description 出库类型
 */
@Getter
public enum OutboundTypeEnum {
    RETURN_SUPPLY((byte)1,"退供"),
    ALLOCATE((byte)2,"调拨"),
    ORDER((byte)3,"订单"),
    MOVEMENT((byte)4,"移库") ,
    SUPERVISORY__WAREHOUSE_OUTBOUND((byte)5,"监管仓出库"),
    scrap((byte)6,"报废");
    private byte code;
    private String name;
    OutboundTypeEnum(byte code,String name){
        this.code = code;
        this.name = name;
    }

    /**
     * 获取OutboundTypeEnum根据code
     * @param codeVal
     * @return
     */
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
