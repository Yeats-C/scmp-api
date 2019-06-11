package com.aiqin.bms.scmp.api.supplier.domain.response.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author boyd
 * @since 2018-09-05
 */
@ApiModel(value = "岗位角色")
public class PositionRole implements Serializable {

    private static final long serialVersionUID = 1L;

    public PositionRole() {
    }

    public PositionRole(String positionCode) {
        this.positionCode = positionCode;
    }

    /**
     * 
     */
    private Long id;
    /**
     * 
     */
    @ApiModelProperty(value="岗位编码")
    private String positionCode;
    /**
     * 
     */
    @ApiModelProperty(value="角色id")
    private String roleId;
    /**
     *
     */
    @ApiModelProperty(value="系统编码")
    private String systemCode;
    /**
     * 
     */
    @ApiModelProperty(value="创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 
     */
    @ApiModelProperty(value="更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 
     */
    @ApiModelProperty(value="创建人")
    private String createBy;
    /**
     * 
     */
    @ApiModelProperty(value="更新人")
    private String updateBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
