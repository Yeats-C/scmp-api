package com.aiqin.bms.scmp.api.supplier.domain.response.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 职位表
 * </p>
 *
 * @author boyd
 * @since 2018-09-05
 */
@Data
public class GrantRoleData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Long id;
    /**
     * 
     */
    private String roleId;
    /**
     * 职位名称
     */
    private String roleName;
    /**
     * 
     */
    private String systemCode;
    /**
     * 
     */
    private String systemName;
    /**
     * 
     */
    private Integer grantType;
    /**
     * 0 自定义数据权限
     */
    private Integer grantValue;
    /**
     * 
     */
    private String grantCustom;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 更新人
     */
    private String updateBy;



}
