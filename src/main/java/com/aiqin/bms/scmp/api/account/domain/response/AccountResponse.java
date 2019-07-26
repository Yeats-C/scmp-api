package com.aiqin.bms.scmp.api.account.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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
public class AccountResponse {

    @ApiModelProperty(value = "姓名")
    @JsonProperty("account_name")
    private String accountName;

    @ApiModelProperty(value = "登录账号")
    @JsonProperty("username")
    private String username;

    @ApiModelProperty(value = "供应商id")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;


    @ApiModelProperty(value = "0 启用 1禁用")
    @JsonProperty("account_status")
    private Integer accountStatus;

    @ApiModelProperty(value = "手机号")
    @JsonProperty("mobile")
    private String mobile;

    @ApiModelProperty(value = "1男2女")
    @JsonProperty("gender")
    private Integer gender;

    @ApiModelProperty(value = "备注")
    @JsonProperty("remark")
    private String remark;


    @ApiModelProperty(value = "角色集合")
    @JsonProperty("role_ids")
    private List<String> roleIds;
}
