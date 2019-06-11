package com.aiqin.bms.scmp.api.base;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-03-11
 * @time: 10:27
 */
@Getter
public enum PurchaseStatus {

    /*
     * 未提交
     */
    UN_SUBMIT("未提交", (byte) 0),
    /*
     * 待审核
     */
    WAIT_CHECK("待审核", (byte) 1),
    /*
     * 审核中
     */
    UNDER_CHECK("审核中", (byte) 2),
    /*
     * 待供应商确认
     */
    WAIT_SUPPLIER_CONFIRM("待供应商确认", (byte) 3),
    /*
     * 待入库
     */
    WAIT_STORAGE("待入库", (byte) 4),
    /*
     * 已入库
     */
    ALREADY_STORAGE("已入库", (byte) 5),
    /*
     * 完成
     */
    COMPLETE("完成", (byte) 6),
    /*
     * 取消
     */
    CANCEL("取消", (byte) 7),
    /*
     * 审核不通过
     */
    FAIL_CHECK("审核不通过", (byte) 8);

    private String desc;
    private Byte num;

    PurchaseStatus(String desc, Byte num) {
        this.desc = desc;
        this.num = num;
    }
    public static Map<Byte, PurchaseStatus> getAllStatus() {
      return Arrays.stream(values()).collect(Collectors.toMap(PurchaseStatus::getNum, Function.identity(), (k1, k2) -> k2));
    }
}