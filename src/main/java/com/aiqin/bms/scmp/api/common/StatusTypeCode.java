package com.aiqin.bms.scmp.api.common;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @功能说明: 各种状态值设置
 * @author: wangxu
 * @date: 2018/12/28 0028 17:24
 */
public enum StatusTypeCode {

    ADD_APPLY((byte) 1,"新增申请类型"),
    UPDATE_APPLY((byte)2,"修改申请类型"),
    CANCEL_APPLY((byte)4,"撤销申请类型"),

    DEL_FLAG((byte)1,"删除"),
    UN_DEL_FLAG((byte)0,"未删除"),

    EN_ABLE((byte)0,"启用"),
    DIS_ABLE((byte)1,"禁用"),

    PENDING_STATUS((byte)0,"待审"),
    REVIEW_STATUS((byte)1,"审核中"),
    PASSED_STATUS((byte)2,"审核通过"),
    NOT_PASSED_STATUS((byte)3,"审核未通过"),
    REVOKED_STATUS((byte)4,"已撤销"),

    ADD_ACCOUNT((byte)0,"新增账户信息"),
    NOT_ADD_ACCOUNT((byte)1,"不新增账户信息"),

    SHOW_ACCOUNT((byte)0,"显示账户信息"),
    NOT_SHOW_ACCOUNT((byte)1,"不显示账户信息"),

    SHOW_ACCOUNT_SKU((byte)0,"显示账户和SKU信息"),
    UN_SHOW_ACCOUNT_SKU((byte)1,"不显示账户和SKU信息"),

    UN_USE((byte)0,"未使用"),
    USE((byte)1,"使用"),

    GOOD((byte)0,"商品"),
    Gift((byte)1,"赠品"),

    STOCK((byte)0,"库存"),
    PURCHASE((byte)1,"采购"),
    SALE((byte)2,"销售"),
    STORE_SALE((byte)3,"门店销售");

    private Byte status;
    private String name;
    StatusTypeCode(Byte status, String name) {
        this.status = status;
        this.name = name;
    }

    public Byte getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public static Map<String,StatusTypeCode> getAll(){
        return Arrays.stream(values()).collect(Collectors.toMap(StatusTypeCode::getName, Function.identity(),(k1,k2)->k2));
    }
}
