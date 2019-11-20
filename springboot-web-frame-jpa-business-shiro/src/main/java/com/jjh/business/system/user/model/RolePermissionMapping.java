package com.jjh.business.system.user.model;

import com.jjh.common.model.AuditBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import static com.jjh.business.system.user.model.RolePermissionMapping.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class RolePermissionMapping extends AuditBaseEntity {

    public static final String TABLE_NAME = TABLE_PREFIX+"sys_role_permission_mapping";

    @Column
    private String roleId;//角色编号
    @Column
    private String permissionId;//权限编号

    @Transient
    private SysRole role;
    @Transient
    private SysPermission permission;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public SysPermission getPermission() {
        return permission;
    }

    public void setPermission(SysPermission permission) {
        this.permission = permission;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }
}
