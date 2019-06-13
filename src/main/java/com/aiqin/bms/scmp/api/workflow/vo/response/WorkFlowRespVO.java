/**
  * Copyright 2019 bejson.com 
  */
package com.aiqin.bms.scmp.api.workflow.vo.response;

import lombok.Data;

/**
 * 审批流回传数据
 * @author zth
 * @date 2019/1/15
 */
@Data
public class WorkFlowRespVO {
    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 返回的消息（失败原因等等）
     */
    private String msg;

    private String attributes;
    private String obj;
    private String type;
    private String flagId;
}