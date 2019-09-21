package com.business.system.user.domain;

import com.common.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 角色实体类
 */
@Entity
@Table(name = "sys_role")
public class SysRole extends BaseEntity {
    @Column
    private String role;//角色标识程序中判断使用,如"admin",这个是唯一的:
    @Column
    private String description;//角色描述,UI界面显示使用
    @Column
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

    @Transient
    private List<UserInfo> userInfoList;

    @Transient
    private List<SysPermission> permissionList;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<UserInfo> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public List<SysPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<SysPermission> permissionList) {
        this.permissionList = permissionList;
    }
}
