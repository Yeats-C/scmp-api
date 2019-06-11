package com.aiqin.bms.scmp.api.base;

import lombok.Getter;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-22
 * @time: 10:28
 */
@Getter
public enum IndicatorStr {
    PROCESS_BTN_FILE_SELECT("BTN_001", "选择文件"),
    PROCESS_BTN_FILE_UPLOAD("BTN_002", "确定上传"),
    PROCESS_BTN_ISSUE("BTN_003", "已阅"),
    PROCESS_BTN_APPROVAL("BTN_004", "同意"),
    PROCESS_BTN_REJECT_LAST("BTN_005", "回退"),
    PROCESS_BTN_REJECT_FIRST("BTN_006", "驳回发起人"),
    PROCESS_BTN_SAVE("BTN_007", "暂存"),
    PROCESS_BTN_SAVE_TODO("BTN_008", "暂存代办"),
    PROCESS_BTN_FORWARD("BTN_009", "转发"),
    PROCESS_BTN_TURN("BTN_010", "转办"),
    PROCESS_BTN_DISTRIBUTE("BTN_011", "分配"),
    PROCESS_BTN_ADD("BTN_012", "加签"),
    PROCESS_BTN_GETBACK("BTN_013", "取回"),
    PROCESS_BTN_START("BTN_014", "发起"),
    PROCESS_BTN_END("BTN_015", "回滚终止"),
    PROCESS_BTN_SUBMIT("BTN_016", "提交申请"),
    PROCESS_BTN_CANCEL("BTN_017", "撤销"),
    PROCESS_BTN_INFORM("BTN_018", "知会"),
    PROCESS_BTN_RELATIVE("BTN_019", "关联文档"),
    PROCESS_BTN_JOINT("BTN_020", "发起会签"),
    PROCESS_BTN_NOPASS("BTN_021", "不通过"),
    PROCESS_BTN_REJECT_END("BTN_022", "驳回并结束"),
    PROCESS_BTN_CONFIRM("BTN_023","确认"),
    PROCESS_BTN_INTERVENE_POINT("BTN_024","指向干预"),
    PROCESS_BTN_ADD_MSG("BTN_025","追加"),
    PROCESS_BTN_REPLAY_MSG("BTN_026","回复"),
    PROCESS_BTN_AUDIT("BTN_027","审核"),
    PROCESS_BTN_KNOWN("BTN_028", "重置发起岗"),
    PROCESS_BTN_RELATIVE_DOC("BTN_029","关联附件"),
    PROCESS_BTN_KILL("BTN_030", "终止"),PROCESS_BTN_PRINT("BTN_031", "打印"),
    PROCESS_BTN_RESUBMIT("BTN_032", "修改并发起"),
    PROCESS_BTN_BATCH_COMPLETE("BTN_033", "批量审批"),

    FORM_TYPE_APPLY("apply","申报"),
    FORM_TYPE_REGIST("regist","登记"),
    FORM_TYPE_PAY("pay","核报"),
    FORM_TYPE_REPORTED("reported","临时报备"),
    FORM_TYPE_SFA("sfa","访销"),
    FORM_TYPE_PACT_AUDIT("pactAudit","合同鉴审"),
    FORM_TYPE_IMS("ims","进销存"),

    COST_TYPE_LEADER_JY("1051","领导人家宴"),
    COST_TYPE_COMMON_JY("1052","普通家宴"),
    COST_TYPE_PUBLICITY_JY("1031","宣传物料"),

    SFA_SERVER_MANAGE("sfamanage","访销管理服务器"),
    SFA_SERVER_MDM("eispmdm","访销人区客品服务器"),

    MSG_TYPE_PROCESS_TASK_NOTICE("task_notice", "流程节点通知"),
    MSG_TYPE_PROCESS_REPLAY("process_replay", "流程消息回复"),
    MSG_TYPE_PROCESS_ADD("process_add", "发起者追加消息"),
    MSG_TYPE_PROCESS_NOTICE("pro_notice", "流程流转通知"),
    MSG_TYPE_FORM_NOTICE("form_notice", "表单消息通知");
    private String code;
    private String desc;

    IndicatorStr(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
