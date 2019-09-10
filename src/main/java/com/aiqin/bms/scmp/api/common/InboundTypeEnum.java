package com.aiqin.bms.scmp.api.common;

import lombok.Getter;

/**
 * @author knight.xie
 * @version 1.0
 * @className In+boundTypeEnum
 * @date 2019/1/8 19:52
 * @description 入库类型
 */
@Getter
public enum InboundTypeEnum {
    RETURN_SUPPLY((byte)1,"采购"),
    ALLOCATE((byte)2,"调拨"),
    ORDER((byte)3,"退货"),
    MOVEMENT((byte)4,"移库"),
    SUPERVISORY_WAREHOUSE_INBOUND((byte)5,"监管仓入库"),
    SCRAP((byte)6,"报废");
    private byte code;
    private String name;
    InboundTypeEnum(byte code, String name){
        this.code = code;
        this.name = name;
    }

    /**
     * 获取OutboundTypeEnum根据code
     * @param codeVal
     * @return
     */
    public static InboundTypeEnum getInbundTypeEnumByCode(byte codeVal)
    {
        for (InboundTypeEnum inboundTypeEnum : InboundTypeEnum.values())
        {
            if (inboundTypeEnum.getCode() == codeVal)
            {
                return inboundTypeEnum;
            }
        }
        return null;
    }

}
