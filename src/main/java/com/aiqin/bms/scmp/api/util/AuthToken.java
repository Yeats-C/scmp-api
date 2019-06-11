package com.aiqin.bms.scmp.api.util;

import lombok.Data;

/**
 * Createed by sunx on 2018/11/21.<br/>
 */
@Data
public class AuthToken {

    private String accountId;

    private String ticket;

    private String personId;

    /**
     * 用户名
     */
    private String personName;

    /**
     * 分销机构id
     */
    private String distributorId;

    /* 管理员登录状态   0 ：超级管理员  1：普通管理员*/
    private Integer loginStatus;

    /**
     * 公司编码
     */
    private String companyCode;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 职位名称
     */
    private String positionCode;
}
