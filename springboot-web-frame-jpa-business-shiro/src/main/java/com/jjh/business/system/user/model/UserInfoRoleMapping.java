package com.jjh.business.system.user.model;

import com.jjh.common.model.AuditBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import static com.jjh.business.system.user.model.UserInfoRoleMapping.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class UserInfoRoleMapping extends AuditBaseEntity {

    public static final String TABLE_NAME = TABLE_PREFIX+"sys_user_role_mapping";

    /** 用户编号 */
    @Column
    private String userId;

    /** 角色编号 */
    @Column
    private String roleId;

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
