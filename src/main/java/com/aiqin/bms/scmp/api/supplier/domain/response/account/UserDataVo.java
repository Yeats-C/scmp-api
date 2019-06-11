package com.aiqin.bms.scmp.api.supplier.domain.response.account;

import lombok.Data;

import java.util.List;

/**
 * Created by boyd on 2018/9/1.
 */
@Data
public class UserDataVo {
    /**
     * 登录账号信息
     */
    private Account account;
    /**
     * 人员信息
     */
    private Person person;
    /**
     * 公司
     */
    private List<Company> companies;
    /**
     * 角色
     */
    private List<Role> roles;
    /**
     * 部门
     */
    private List<Department> departments;
    /**
     * 职位
     */
    private List<Positions> positions;
    /**
     * 系统
     */
    private List<System> systems;
    /**
     * 用户岗位
     */
    private List<UserPosition> userPositions;
    /**
     * 角色资源
     */
    private List<RoleResource> roleResources;
    /**
     * 数据权限
     */
    private List<GrantRoleData> grantRoleData;
    /**
     * 岗位权限
     */
    private List<PositionRole> positionRoles;
}
