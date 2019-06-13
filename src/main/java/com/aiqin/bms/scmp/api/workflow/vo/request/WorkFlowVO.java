package com.aiqin.bms.scmp.api.workflow.vo.request;

import lombok.Data;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-01-15
 * @time: 14:32
 */
@Data
public class WorkFlowVO {
    //签名
    private String sign;
    //账户
    private String username;
    //时间戳
    private String timeStamp;
    //当前登录账户
    private String currentPositionCode;
    //表单编码
    private String formNo;
    //标题
    private String title;
    //备注
    private String remark;
    //
    private String key;
    //回调的接口
    private String updateUrl;
    //
    private String ticket;
    //主机地址
    private String host;
    //审批的接口
    private String formUrl;
    //条件
    private String variables;

}
