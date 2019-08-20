package com.aiqin.bms.scmp.api.abutment.domain.conts;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author sunx
 * @description scmp业务单据类型
 * @date 2019-08-02
 */
public enum ScmpOrderEnum {
    /**
     *
     */
    PURCHASE(0, "采购"),
    PURCHASE_BACK(5, "退供"),
    ORDER(10, "配送订单"),
    ORDER_ZS(15, "直送订单"),
    ORDER_ASSIST(20, "辅采订单"),
    ORDER_BACK(25, "售后退货"),
    STORAGE_CHANGE(30, "同仓库内移库"),
    STORAGE_TRANSFER_OUT(35, "调拨出库"),
    STORAGE_TRANSFER_IN(36, "调拨入库"),
    STORAGE_OTHER(40, "其他库存变动"),
    ;

    private int code;
    private String desc;

    ScmpOrderEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ScmpOrderEnum getByCode(int code) {
        Optional<ScmpOrderEnum> re = Stream.of(ScmpOrderEnum.values()).filter(a -> a.getCode() == code).findFirst();
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
