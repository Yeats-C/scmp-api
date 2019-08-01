package com.aiqin.bms.scmp.api.account.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
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
@ApiModel("账号")
public class AccountRequest extends PagesRequest {

    @ApiModelProperty(value="创建人公司id")
    @JsonProperty("create_by_company_code")
    private String createByCompanyCode;

    @ApiModelProperty(value="创建人公司name")
    @JsonProperty("create_by_company_name")
    private String createByCompanyName;

    @ApiModelProperty(value="姓名")
    @JsonProperty("account_name")
    private String accountName;

    @ApiModelProperty(value="账号")
    @JsonProperty("username")
    private String username;

    @ApiModelProperty(value="密码")
    @JsonProperty("password")
    private String password;

    @ApiModelProperty(value="供应商id")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="0 启用 1禁用")
    @JsonProperty("account_status")
    private Integer accountStatus;

    @ApiModelProperty(value="手机号")
    @JsonProperty("mobile")
    private String mobile;

    @ApiModelProperty(value="1男2女")
    @JsonProperty("gender")
    private Integer gender;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value = "角色")
    @JsonProperty("role_id")
    private String roleId;

    @ApiModelProperty(value = "角色集合")
    @JsonProperty("role_ids")
    private List<String> roleIds;

    public AccountRequest() {
    }

    public AccountRequest(String accountName, String roleId, String supplierCode, Integer accountStatus,String createByCompanyCode) {
        this.accountName = accountName;
        this.roleId = roleId;
        this.supplierCode = supplierCode;
        this.accountStatus = accountStatus;
        this.createByCompanyCode = createByCompanyCode;
    }
}
