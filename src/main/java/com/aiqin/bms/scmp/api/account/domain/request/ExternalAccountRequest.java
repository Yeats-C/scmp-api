package com.aiqin.bms.scmp.api.account.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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
public class ExternalAccountRequest {

    @ApiModelProperty(value="姓名")
    @JsonProperty("account_name")
    private String accountName;

    @ApiModelProperty(value="密码")
    @JsonProperty("password")
    private String password;

    @ApiModelProperty(value="登录账号")
    @JsonProperty("username")
    private String username;

    @ApiModelProperty(value="")
    @JsonProperty("person_id")
    private String personId;

    @ApiModelProperty(value="")
    @JsonProperty("role_ids")
    private String roleIds;

    @ApiModelProperty(value="供应商id")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="岗位id")
    @JsonProperty("position_code")
    private String positionCode;

    @ApiModelProperty(value="岗位名称")
    @JsonProperty("position_name")
    private String positionName;

    @ApiModelProperty(value="公司id")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty(value="公司名称")
    @JsonProperty("company_name")
    private String companyName;

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

    @ApiModelProperty(value = "人员类型")
    @JsonProperty(value = "person_type")
    private Integer personType;

    @ApiModelProperty(value="部门名称")
    @JsonProperty("department_name")
    private String departmentName;

    @ApiModelProperty(value="部门id")
    @JsonProperty("department_code")
    private String departmentCode;

    @ApiModelProperty(value="业务人员所属名称,用于创建部门名称,岗位名称")
    @JsonProperty("business_name")
    private String businessName;

    @ApiModelProperty(value="业务人员所属(后续使用自己添加),1 供应商平台")
    @JsonProperty("business_type")
    private Integer businessType;

}
