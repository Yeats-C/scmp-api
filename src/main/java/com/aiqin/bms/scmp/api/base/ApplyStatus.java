package com.aiqin.bms.scmp.api.base;

import lombok.Getter;

import java.util.Objects;

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
    PENDING((byte)0, "待审","CREATEBY发起APPLYTYPE申请"),
    APPROVAL((byte)1, "审批中","CREATEBY发起APPLYTYPE申请"),
    APPROVAL_SUCCESS((byte)2, "审批通过","CREATEBY申请AUDITORBY审核通过"),
    APPROVAL_FAILED((byte)3, "审批失败","CREATEBY申请AUDITORBY审核不通过"),
    REVOKED((byte)4, "已撤销","CREATEBY申请被AUDITORBY撤销"),
    PENDING_SUBMISSION((byte)5, "待提交","CREATEBY提交数据");
    private Byte number;
    private String desc;
    private String content;

    ApplyStatus(Byte number, String desc, String content) {
        this.desc = desc;
        this.number = number;
        this.content = content;
    }

    public static ApplyStatus getApplyStatusByNumber(Byte number){
        for (ApplyStatus value : ApplyStatus.values()) {
            if (Objects.equals(value.getNumber(),number)) {
                return value;
            }
        }
        return null;
    }
}
