package com.aiqin.bms.scmp.api.common;

/**
 * @author knight.xie
 * @version 1.0
 * @className TagTypeCode
 * @date 2019/5/5 17:14
 * @description 标签编码类型与字典表对应
 */
public enum TagTypeCode {
    SUPPLIER("1", "供应商"),
    SKU("2", "SKU"),
    CONTRACT("3", "合同");

    private String status;
    private String name;
    TagTypeCode(String status, String name) {
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
