package com.jjh.business.system.user.domain;

import com.jjh.common.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_user_role_mapping")
public class UserInfoRoleMapping extends BaseEntity {
    @Column
    private String userId;//用户编号
    @Column
    private String roleId;//角色编号

    @Transient
    private UserInfo userInfo;
    @Transient
    private SysRole role;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }
}
