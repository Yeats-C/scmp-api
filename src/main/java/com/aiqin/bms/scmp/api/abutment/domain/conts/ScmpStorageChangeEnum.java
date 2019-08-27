package com.aiqin.bms.scmp.api.abutment.domain.conts;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author sunx
 * @date 2019-08-26
 * @description 出入库类型
 */
public enum ScmpStorageChangeEnum {
    /**
     *
     */
    PURCHASE_IN(0, "采购入库"),
    PURCHASE_BACK_OUT(5, "退供出库"),
    ORDER_OUT(10, "配送订单出库"),
    ORDER_ZS_OUT(15, "直送订单出库"),
    ORDER_ASSIST_OUT(20, "辅采订单出库"),
    ORDER_BACK_IN(25, "售后退货入库"),
    CHANGE_STORAGE(30, "移库"),
    TRANSFERS_IN(35, "调拨入库"),
    TRANSFERS_OUT(40, "调拨出库"),
    REPORT_LOSS(45, "报损"),
    REPORT_ADD(50, "报溢"),
    ;

    private int code;
    private String desc;

    ScmpStorageChangeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ScmpStorageChangeEnum getByCode(int code) {
        Optional<ScmpStorageChangeEnum> re = Stream.of(ScmpStorageChangeEnum.values()).filter(a -> a.getCode() == code).findFirst();
        if (re.isPresent()) {
            return re.get();
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
