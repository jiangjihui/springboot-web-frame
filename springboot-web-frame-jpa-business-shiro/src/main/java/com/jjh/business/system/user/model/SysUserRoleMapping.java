package com.jjh.business.system.user.model;

import com.jjh.common.model.AuditBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import static com.jjh.business.system.user.model.SysUserRoleMapping.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class SysUserRoleMapping extends AuditBaseEntity {

    public static final String TABLE_NAME = TABLE_PREFIX+"sys_user_role_mapping";

    /** 用户编号 */
    @Column
    private String userId;

    /** 角色编号 */
    @Column
    private String roleId;

    @Transient
    private SysUser sysUser;
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

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }
}
