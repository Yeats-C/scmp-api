package com.aiqin.bms.scmp.api.abutment.domain.conts;

import java.util.Objects;
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
    PURCHASE("0", "采购"),
    PURCHASE_BACK("5", "退供"),
    ORDER("10", "订单"),
//    ORDER_ZS("15", "直送订单"),
//    ORDER_ASSIST("20", "辅采订单"),
    ORDER_BACK("25", "售后退货"),
    STORAGE_CHANGE("30", "出入库"),
    ;

    private String code;
    private String desc;

    ScmpOrderEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ScmpOrderEnum getByCode(String code) {
        Optional<ScmpOrderEnum> re = Stream.of(ScmpOrderEnum.values()).filter(a -> Objects.equals(code, a.getCode())).findFirst();
        if (re.isPresent()) {
            return re.get();
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
