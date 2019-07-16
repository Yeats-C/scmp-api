package com.aiqin.bms.scmp.api.common;

/**
 * 处理类型
 */
public enum  HandleTypeCoce {

    ADD((byte)0,"新增"),
    UPDATE((byte)1,"修改"),
    DOWNLOAD((byte)2,"下载"),
    PENDING((byte)4, "待审"),
    APPROVAL((byte)5, "审批中"),
    APPROVAL_SUCCESS((byte)6, "审批通过"),
    APPROVAL_FAILED((byte)7, "审批失败"),
    REVOKED((byte)8, "已撤销"),
    PENDING_SUBMISSION((byte)9, "待提交");
    private Byte status;
    private String name;
    HandleTypeCoce(Byte status, String name) {
        this.status = status;
        this.name = name;
    }
    public Byte getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
