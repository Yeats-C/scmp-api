package com.aiqin.bms.scmp.api.supplier.domain.response.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户职位表
 * </p>
 *
 * @author boyd
 * @since 2018-09-05
 */
@ApiModel(value = "人员岗位")
public class UserPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    public UserPosition() {
    }

    public UserPosition(String positionCode) {
        this.positionCode = positionCode;
    }

    /**
     *
     */
    private Long id;
    /**
     *
     */
    @ApiModelProperty(value = "员工编码")
    @JsonProperty(value = "account_id")
    private String accountId;
    /**
     *
     */
    @ApiModelProperty(value = "登录名")
    private String username;
    /**
     *
     */
    @ApiModelProperty(value = "人员编号")
    @JsonProperty(value = "person_id")
    private String personId;
    /**
     *
     */
    @ApiModelProperty(value = "人员名称")
    @JsonProperty(value = "person_name")
    private String personName;
    /**
     * 职位编码
     */
    @ApiModelProperty(value = "职位编码")
    @JsonProperty(value = "position_code")
    private String positionCode;
    /**
     *
     */
    @ApiModelProperty(value = "职位名称")
    @JsonProperty(value = "position_name")
    private String positionName;
    /**
     *
     */
    @ApiModelProperty(value = "所属公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;
    /**
     *
     */
    @ApiModelProperty(value = "所属公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;
    /**
     *
     */
    @ApiModelProperty(value = "所属部门编码")
    @JsonProperty(value = "department_code")
    private String departmentCode;
    /**
     *
     */
    @ApiModelProperty(value = "所属编码名称")
    @JsonProperty(value = "department_name")
    private String departmentName;
    /**
     * 0禁用1启用
     */
    @ApiModelProperty(value = "员工状态0禁用1启用")
    @JsonProperty(value = "user_position_status")
    private Integer userPositionStatus;
    /**
     *
     */
    @ApiModelProperty(value = "生效时间")
    @JsonProperty(value = "begin_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    /**
     *
     */
    @ApiModelProperty(value = "失效时间")
    @JsonProperty(value = "finish_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonProperty(value = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonProperty(value = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @JsonProperty(value = "create_by")
    private String createBy;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    @JsonProperty(value = "update_by")
    private String updateBy;

    @ApiModelProperty(value = "岗位级别编码")
    @JsonProperty(value = "position_level_code")
    private String positionLevelCode;

    @ApiModelProperty(value = "岗位级别名称")
    @JsonProperty(value = "position_level_name")
    private String positionLevelName;

    private String parentPositionName;

    private String parentPositionUser;


    public String getParentPositionName() {
        return parentPositionName;
    }

    public void setParentPositionName(String parentPositionName) {
        this.parentPositionName = parentPositionName;
    }

    public String getParentPositionUser() {
        return parentPositionUser;
    }

    public void setParentPositionUser(String parentPositionUser) {
        this.parentPositionUser = parentPositionUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getUserPositionStatus() {
        return userPositionStatus;
    }

    public void setUserPositionStatus(Integer userPositionStatus) {
        this.userPositionStatus = userPositionStatus;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getPositionLevelCode() {
        return positionLevelCode;
    }

    public void setPositionLevelCode(String positionLevelCode) {
        this.positionLevelCode = positionLevelCode;
    }

    public String getPositionLevelName() {
        return positionLevelName;
    }

    public void setPositionLevelName(String positionLevelName) {
        this.positionLevelName = positionLevelName;
    }
}
