package com.aiqin.bms.scmp.api.constant;

public interface RejectRecordStatus {

    /**
     * 退供申请单状态: 0  待提交 1 待审核 2.审核中 3.审核通过 4.审核不通过 5. 撤销
     */
    Integer REJECT_APPLY_NO_SUBMIT = 0;
    Integer REJECT_APPLY_TO_REVIEW = 1;
    Integer REJECT_APPLY_UNDER_REVIEW = 2;
    Integer REJECT_APPLY_YES = 3;
    Integer REJECT_APPLY_NO = 4;
    Integer REJECT_APPLY_REVOKE = 5;

    /**
     * 退供申请单状态: 0.待确认 1.待出库 2.出库开始 3.已完成 4.已撤销
     */
    Integer REJECT_NO_SUBMIT = 0;
    Integer REJECT_TO_OUTBOUND = 1;
    Integer REJECT_OUTBOUND = 2;
    Integer REJECT_OUTBOUND_COMPLETE = 3;
    Integer REJECT_REVOKE = 4;

}
