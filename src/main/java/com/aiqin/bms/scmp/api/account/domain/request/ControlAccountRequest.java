package com.aiqin.bms.scmp.api.account.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@Data
public class ControlAccountRequest {

    /**
     * 登录账号
     */
    private String username;

    private String password;

    @JsonProperty(value = "person_id")
    private String personId;
    /**
     * 员工名称
     */
    @JsonProperty(value = "person_name")
    private String personName;
    @JsonProperty(value = "company_code")
    private String companyCode;
    @JsonProperty(value = "company_name")
    private String companyName;
    /**
     * 0 启用 1禁用
     */
    @JsonProperty(value = "account_status")
    private Integer accountStatus;

}
