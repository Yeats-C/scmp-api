package com.aiqin.bms.scmp.api.supplier.domain.response.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统表
 * </p>
 *
 * @author boyd
 * @since 2018-09-05
 */
@ApiModel(value = "系统对象")
public class System implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Long id;
    /**
     * 项目编码
     */
    @ApiModelProperty(value = "项目编码")
    private String systemCode;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String systemName;
    /**
     * 0启用 1禁用
     */
    @ApiModelProperty(value = "0启用 1禁用")
    private Integer systemStatus;
    /**
     * 同步批次标识
     */
    @ApiModelProperty(value = "同步批次标识")
    private String planMark;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String systemHost;

    private String systemDescribe;

    private List<SystemResource> systemResources;

    private int isPublic;

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSystemDescribe() {
        return systemDescribe;
    }

    public void setSystemDescribe(String systemDescribe) {
        this.systemDescribe = systemDescribe;
    }

    public List<SystemResource> getSystemResources() {
        return systemResources;
    }

    public String getSystemHost() {
        return systemHost;
    }

    public void setSystemHost(String systemHost) {
        this.systemHost = systemHost;
    }

    public void setSystemResources(List<SystemResource> systemResources) {
        this.systemResources = systemResources;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Integer getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(Integer systemStatus) {
        this.systemStatus = systemStatus;
    }

    public String getPlanMark() {
        return planMark;
    }

    public void setPlanMark(String planMark) {
        this.planMark = planMark;
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
