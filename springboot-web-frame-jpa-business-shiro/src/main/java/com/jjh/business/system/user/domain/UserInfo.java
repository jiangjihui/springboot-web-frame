package com.jjh.business.system.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jjh.common.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 用户信息实体类
 */
@ApiModel("用户信息实体类")
@Data
@Entity
@Table(name = "user_info",indexes = {@Index(columnList = "username")})
public class UserInfo extends BaseEntity {

    /** 帐号 */
    @ApiModelProperty("帐号")
    @Column(length = 120)
    private String username;

    /** 名称 */
    @ApiModelProperty("名称")
    @Column(length = 120)
    private String name;

    /** 密码 */
    @ApiModelProperty("密码")
    @Column(length = 255)
    private String password;

    /** 加密密码的盐 */
    @ApiModelProperty("加密密码的盐")
    @Column(length = 255)
    private String salt;

    /** 用户状态 0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定 */
    @ApiModelProperty("用户状态 0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定")
    @Column(length = 12)
    private Integer state;

    /** 角色列表 */
    @ApiModelProperty("角色列表")
    @Transient
    private List<SysRole> roleList;


    @JsonIgnore
    public String getCredentialsSalt() {
        return username+salt;
    }
}
