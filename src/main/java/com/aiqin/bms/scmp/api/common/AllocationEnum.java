package com.aiqin.bms.scmp.api.common;

/**
 * 描述:  调拨状态枚举类
 *
 * @Author: Kt.w
 * @Date: 2019/1/10
 * @Version 1.0
 * @since 1.0
 */
public enum AllocationEnum {

    ALLOCATION_TYPE_TOCHECK((byte)0,"待审核"),
    ALLOCATION_TYPE_CHECK((byte)1,"审核中"),
    ALLOCATION_TYPE_TO_OUTBOUND((byte)2,"待出库"),
    ALLOCATION_TYPE_OUTBOUND((byte)3,"已出库"),
    ALLOCATION_TYPE_INBOUND((byte)4,"待入库"),
    ALLOCATION_TYPE_FINISHED((byte)5,"已完成"),
    ALLOCATION_TYPE_CANCEL((byte)6,"取消"),
    ALLOCATION_TYPE_REJECTED((byte)7,"审核不通过");
    private Byte status;
    private String name;
    AllocationEnum(Byte status, String name) {
        this.status = status;
        this.name = name;
    }
    public Byte getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }


    /**
     * 根据编码获取状态名称
     * @param codeVal
     * @return
     */
    public static String  getAllocationEnumNameByCode(byte codeVal)
    {
        for (AllocationEnum stockStatusEnum : AllocationEnum.values())
        {
            if (stockStatusEnum.getStatus() == codeVal)
            {
                return stockStatusEnum.getName();
            }
        }
        return null;
    }

}
