package com.aiqin.bms.scmp.api.supplier.domain.response.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author boyd
 * @since 2018-09-05
 */
@ApiModel(value = "角色")
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;
    @ApiModelProperty(value = "角色Id")
    @JsonProperty(value = "role_id")
    private String roleId;
    @ApiModelProperty(value = "角色名称")
    @JsonProperty(value = "role_name")
    private String roleName;
    @ApiModelProperty(value = "系统id")
    @JsonProperty(value = "system_code")
    private String systemCode;
    @ApiModelProperty(value = "系统名称", required = true)
    @JsonProperty(value = "system_name")
    private String systemName;
    /**
     * 角色状态 0启用 1禁用
     */
    @ApiModelProperty(value = "角色状态 0启用 1禁用")
    @JsonProperty(value = "role_status")
    private Integer roleStatus;

    @JsonProperty(value = "begin_time")
    private Date beginTime;
    @JsonProperty(value = "finish_time")
    private Date finishTime;
    /**
     * 创建时间
     */
    @JsonIgnore
    @JsonProperty(value = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonProperty(value = "update_time")
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
    /**
     * 备注
     */
    @JsonProperty(value = "remark")
    private String remark;


    public Role() {
    }

    public Role(String roleId) {
        this.roleId = roleId;
    }

    public Role(String roleName, String systemCode) {
        this.roleName = roleName;
        this.systemCode = systemCode;
    }
}
