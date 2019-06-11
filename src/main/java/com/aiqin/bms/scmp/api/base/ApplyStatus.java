package com.aiqin.bms.scmp.api.base;

import lombok.Getter;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-18
 * @time: 10:58
 */
@Getter
public enum ApplyStatus {
    //    申请状态(0 ：待审 1，审批中 2 审批通过 ，3 审批失败 4，已撤销 )
    PENDING((byte)0, "待审"),
    APPROVAL((byte)1, "审批中"),
    APPROVAL_SUCCESS((byte)2, "审批通过"),
    APPROVAL_FAILED((byte)3, "审批失败"),
    REVOKED((byte)4, "已撤销");
    private Byte number;
    private String desc;

    ApplyStatus(Byte number, String desc) {
        this.desc = desc;
        this.number = number;
    }
}
