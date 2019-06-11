package com.aiqin.bms.scmp.api.supplier.domain;

/**
 * 描述:
 *
 * @Author: Kt.w
 * @Date: 2019/1/2
 * @Version 1.0
 * @since 1.0
 */
public enum LogisticsCenterEnum {

    CHINA("100","全国"),
    PROVINCE("99","全省"),
    BEI_JING("110000", "北京"),
    BEI_JING_TOTAL("99", "北京市"),
    TIAN_JIN("120000", "天津"),
    TIAN_JIN_TOTAL("99", "天津市"),
    SHANG_HAI("310000", "上海"),
    SHANG_HAI_TOTAL("99", "上海市"),
    CHONG_QING("500000", "重庆"),
    CHONG_QING_TOTAL("99", "重庆市");

    private String status;
    private String name;
    LogisticsCenterEnum(String status, String name) {
        this.status = status;
        this.name = name;
    }
    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
