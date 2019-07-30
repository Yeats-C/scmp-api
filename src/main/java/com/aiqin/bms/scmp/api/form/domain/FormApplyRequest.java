package com.aiqin.bms.scmp.api.form.domain;

import lombok.Data;

@Data
public class FormApplyRequest {

    /**
     * 业务表单号，必传
     */
    private String formNo;
    /**
     * 发起人登录账号，必传
     */
    private String personId;
    /**
     * 流程定义key 必传
     */
    private String processKey;
    /**
     * 表单访问URL，必传
     */
    private String formUrl;
    /**
     * 发起流程实例的标题，必传
     */
    private String title;
    /**
     * 表单更新回传地址，必传
     */
    private String formUpdateUrl;
    /**
     * // 发起人添加的流程备注，在流程的历史审批记录中可见。如果备注只是在表单中需要展现，不需要传一份到这个参数
     */
    private String remark;
    /**
     * 选择的审批人
     */
    private String auditPersonId;

}
