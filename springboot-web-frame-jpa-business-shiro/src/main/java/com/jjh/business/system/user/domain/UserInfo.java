package com.jjh.business.system.user.domain;

import com.jjh.common.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 用户信息实体类
 */
@Entity
@Table(name = "user_info",indexes = {@Index(columnList = "username")})
public class UserInfo extends BaseEntity {
    @Column(unique =true)
    private String username;//帐号
    @Column
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
    @Column
    private String password; //密码;
    @Column
    private String salt;//加密密码的盐
    @Column
    private byte state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
    @Transient
    private List<SysRole> roleList;// 一个用户具有多个角色


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    public String getCredentialsSalt() {
        return username+salt;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", salt='" + salt + '\'' +
                ", state=" + state +
                ", roleList=" + roleList +
                '}';
    }
}
