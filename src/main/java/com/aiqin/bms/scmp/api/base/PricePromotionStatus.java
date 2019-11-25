package com.aiqin.bms.scmp.api.base;

import lombok.Data;
import lombok.Getter;

/**
 * @Auther: mamingze
 * @Date: 2019-11-06 16:29
 * @Description:
 */
@Getter
public enum PricePromotionStatus {

    /*
     * 待审核
     */
    WAIT_CHECK("待审核", (byte) 1),
    /*
     * 审核中
     */
    UNDER_CHECK("审核中", (byte) 2),
    /*
     * 审核通过
     */
    PASS_CHECK("审核通过", (byte) 3),
    /*
     * 审核不通过
     */
    FAIL_CHECK("审核不通过", (byte) 4),
    /*
     * 取消
     */
    CANCEL("取消", (byte) 5);
    private String desc;
    private Byte num;

    PricePromotionStatus(String desc, Byte num) {
        this.desc = desc;
        this.num = num;
    }
}
