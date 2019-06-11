package com.aiqin.bms.scmp.api.supplier.domain.response.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author boyd
 * @since 2018-09-05
 */
@ApiModel(value = "角色菜单")
public class RoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Long id;
    /**
     * 
     */
    @ApiModelProperty(value = "角色id")
    @JsonProperty(value="role_id")
    private String roleId;
    /**
     * 
     */
    @ApiModelProperty(value = "菜单编码")
    @JsonProperty(value="resource_code")
    private String resourceCode;
    /**
     * 菜单链接
     */
    @ApiModelProperty(value = "菜单链接")
    @JsonProperty(value="resource_link")
    private String resourceLink;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    @JsonProperty(value="resource_name")
    private String resourceName;
    /**
     * 菜单等级
     */
    @ApiModelProperty(value = "菜单级别")
    @JsonProperty(value="resource_level")
    private Integer resourceLevel;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    @JsonProperty(value="resource_order")
    private Integer resourceOrder;
    /**
     * 
     */
    @ApiModelProperty(value = "菜单图标")
    @JsonProperty(value="resource_icon")
    private String resourceIcon;
    /**
     * 0禁用1启用
     */
    @ApiModelProperty(value = "0禁用1启用")
    @JsonProperty(value="resource_status")
    private Integer resourceStatus;
    /**
     * 
     */
    @ApiModelProperty(value = "父级菜单编码")
    @JsonProperty(value="parent_code")
    private String parentCode;
    /**
     * 所属系统
     */
    @ApiModelProperty(value = "所属系统编码")
    @JsonProperty(value="system_code")
    private String systemCode;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonProperty(value="create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonProperty(value="update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceLink() {
        return resourceLink;
    }

    public void setResourceLink(String resourceLink) {
        this.resourceLink = resourceLink;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getResourceLevel() {
        return resourceLevel;
    }

    public void setResourceLevel(Integer resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    public Integer getResourceOrder() {
        return resourceOrder;
    }

    public void setResourceOrder(Integer resourceOrder) {
        this.resourceOrder = resourceOrder;
    }

    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon;
    }

    public Integer getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(Integer resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
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

}
