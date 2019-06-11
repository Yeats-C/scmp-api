package com.aiqin.bms.scmp.api.supplier.domain.response.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author boyd
 * @since 2018-09-05
 */
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     *
     */
    @JsonProperty(value = "account_id")
    private String accountId;
    /**
     * 登录账号
     */
    private String username;
    /**
     *
     */
    private String password;
    /**
     *
     */
    @JsonProperty(value = "person_id")
    private String personId;
    /**
     * 员工名称
     */
    @JsonProperty(value = "person_name")
    private String personName;
    /**
     *
     */
    @JsonProperty(value = "company_code")
    private String companyCode;
    /**
     *
     */
    @JsonProperty(value = "company_name")
    private String companyName;
    /**
     * 0 启用 1禁用
     */
    @JsonProperty(value = "account_status")
    private Integer accountStatus;
    /**
     * 0正常 1第一次登录修改密码
     */
    @JsonProperty(value = "password_status")
    private Integer passwordStatus;
    /**
     *
     */
    @JsonProperty(value = "password_vaild_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date passwordVaildTime;
    /**
     * 0  md5, 1 salt
     */
    @JsonProperty(value = "encrypt_type")
    private Integer encryptType;
    /**
     *
     */
    private String encryptSalt;
    /**
     *
     */
    @JsonProperty(value = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     *
     */
    @JsonProperty(value = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private List<UserPosition> userPositions;

    public Account() {
    }

    public Account(String accountId) {
        this.accountId = accountId;
    }
}
