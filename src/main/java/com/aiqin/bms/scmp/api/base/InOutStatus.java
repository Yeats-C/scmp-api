package com.aiqin.bms.scmp.api.base;

/**
 * @Classname: InOutStatus
 * 描述: 出入库状态枚举类型
 * @Author: Kt.w
 * @Date: 2019/2/26
 * @Version 1.0
 * @Since 1.0
 */
public enum InOutStatus {
    CREATE_INOUT((byte)1,"新建"),
    SEND_INOUT((byte)2,"已发送"),
    RECEIVE_INOUT((byte)3,"开始作业"),
    COMPLETE_INOUT((byte)4,"完成"),
    CALL_OFF((byte)5,"取消");
    private byte code;
    private String name;
    InOutStatus(byte code, String name){
        this.code = code;
        this.name = name;
    }

    public byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 获取StockStatusEnum根据code
     * @param codeVal
     * @return
     */
    public static InOutStatus getStockStatusEnumByCode(byte codeVal)
    {
        for (InOutStatus stockStatusEnum : InOutStatus.values())
        {
            if (stockStatusEnum.getCode() == codeVal)
            {
                return stockStatusEnum;
            }
        }
        return null;
    }

}
