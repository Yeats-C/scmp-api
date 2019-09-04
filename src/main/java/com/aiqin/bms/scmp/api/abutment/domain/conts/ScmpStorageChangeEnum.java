package com.aiqin.bms.scmp.api.abutment.domain.conts;

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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
    PURCHASE_IN("0", "采购入库"),
    PURCHASE_BACK_OUT("5", "退供出库"),
    ORDER_OUT("10", "订单出库"),
//    ORDER_ZS_OUT("15", "直送订单出库"),
//    ORDER_ASSIST_OUT("20", "辅采订单出库"),
    ORDER_BACK_IN("25", "售后退货入库"),
    CHANGE_STORAGE_IN("30", "移库入库"),
    CHANGE_STORAGE_OUT("31", "移库出库"),
    TRANSFERS_IN("35", "调拨入库"),
    TRANSFERS_OUT("40", "调拨出库"),
    REPORT_LOSS("45", "报损"),
    REPORT_ADD("50", "报溢"),
    ;

    private String code;
    private String desc;

    /**
     * 入库标识
     */
    public static final int STORAGE_IN = 1;
    /**
     * 出库标识
     */
    public static final int STORAGE_OUT = 0;

    ScmpStorageChangeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ScmpStorageChangeEnum getByCode(String code) {
        Optional<ScmpStorageChangeEnum> re = Stream.of(ScmpStorageChangeEnum.values()).filter(a -> Objects.equals(code, a.getCode())).findFirst();
        if (re.isPresent()) {
            return re.get();
        }
        return null;
    }

    /**
     * 出入库标识
     *
     * @param code
     * @return
     */
    public static int getStorageInOutFlag(String code) {
        Set<String> set1 = Sets.newHashSet("0", "25", "30", "35", "50");
        if (set1.contains(code)) {
            return STORAGE_IN;
        }
        return STORAGE_OUT;
    }

    public static ScmpOrderEnum getByChangeCode(String code) {
        Set<String> set1 = Sets.newHashSet("0", "5", "10", "15", "20", "25");
        if (set1.contains(code)) {
            return ScmpOrderEnum.getByCode(code);
        }
        return ScmpOrderEnum.STORAGE_CHANGE;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
