package com.aiqin.bms.scmp.api.supplier.domain.response.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门
 * </p>
 *
 * @author boyd
 * @since 2018-09-05
 */
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 部门编码
     */
    @ApiModelProperty("部门编码")
    @JsonProperty(value = "department_code")
    private String departmentCode;
    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    @JsonProperty(value = "department_name")
    private String departmentName;
    /**
     * 公司编码
     */
    @ApiModelProperty("公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;
    /**
     *
     */
    @ApiModelProperty("公司名称")
    @JsonProperty(value = "company_name")
    private String companyName;
    /**
     * 上级部门
     */
    @ApiModelProperty("父部门编码")
    @JsonProperty(value = "parent_code")
    private String parentCode;
    /**
     *
     */
    @ApiModelProperty("父部门名称")
    @JsonProperty(value = "parent_name")
    private String parentName;
    /**
     * 0启用1禁用
     */
    @Ignore
    private Integer departmentStatus;
    /**
     * 创建时间
     */
    @JsonProperty(value = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonProperty(value = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 创建人
     */
    @JsonProperty(value = "create_by")
    private String createBy;
    /**
     * 更新人
     */
    @JsonProperty(value = "update_by")
    private String updateBy;

    private List<Department> departments;

    /**
     * 有效开始时间
     */
    @ApiModelProperty("开始时间")
    @JsonProperty(value = "begin_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    /**
     * 有效结束时间
     */
    @ApiModelProperty("结束时间")
    @JsonProperty(value = "finish_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;
    /**
     * 备注
     */
    private String remark;

    /**
     * 公司级别
     */
    @ApiModelProperty("部门级别")
    @JsonProperty(value = "department_level")
    private Integer departmentLevel;

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getDepartmentStatus() {
        return departmentStatus;
    }

    public void setDepartmentStatus(Integer departmentStatus) {
        this.departmentStatus = departmentStatus;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDepartmentLevel() {
        return departmentLevel;
    }

    public void setDepartmentLevel(Integer departmentLevel) {
        this.departmentLevel = departmentLevel;
    }
}
