package com.aiqin.bms.scmp.api.product.domain.request;

import lombok.Getter;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-22
 * @time: 10:17
 */
@Getter
public enum Indicator {
    COST_FORM_STATUS_APPROVING(10, "审批中"),
    COST_FORM_STATUS_APPROVED(15, "已审核");
//    PROCESS_BTN_ISSUE("BTN_003", "已阅"),
//    PROCESS_BTN_APPROVAL("BTN_004", "同意"),
//    PROCESS_BTN_AUDIT("BTN_027","审核"),
//    PROCESS_BTN_BATCH_COMPLETE("BTN_033", "批量审批");
    private Integer code;
    private String desc;
    Indicator(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
