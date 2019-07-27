package com.aiqin.bms.scmp.api.account.domain;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class Account  {
    @ApiModelProperty(value="")
    @JsonProperty("id")
    private Long id;

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

    @ApiModelProperty(value="公司id")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty(value="公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty(value="部门id")
    @JsonProperty("department_code")
    private String departmentCode;

    @ApiModelProperty(value="部门名称")
    @JsonProperty("department_name")
    private String departmentName;

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

    @ApiModelProperty(value="")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="")
    @JsonProperty("update_time")
    private Date updateTime;

}