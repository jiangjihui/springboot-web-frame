package com.jjh.business.system.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jjh.common.model.AuditBaseEntity;
import com.jjh.framework.plugin.excel.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

import static com.jjh.business.system.user.model.SysUser.TABLE_NAME;

/**
 * 用户信息实体类
 *
 * @author jjh
 * @date 2019/11/19
 */
@ApiModel("用户信息实体类")
@Data
@Entity
@Table(name = TABLE_NAME,indexes = {@Index(columnList = "username")})
public class SysUser extends AuditBaseEntity {

    public static final String TABLE_NAME = TABLE_PREFIX+"sys_user";

    /** 帐号 */
    @Excel(name = "帐号")
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("帐号")
    @Column(length = 120)
    private String username;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    @Column(length = 120)
    private String name;

    /** 密码 */
    @Excel(name = "密码", type = Excel.Type.IMPORT)
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 255)
    private String password;

    /** 加密密码的盐 */
    @JsonIgnore
    @ApiModelProperty("加密密码的盐")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 255)
    private String salt;

    /** 头像 */
    @ApiModelProperty("头像")
    @Column(length = 255)
    private String avatar;

    /** 生日 */
    @Excel(name = "生日", dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Column
    private Date birthday;

    /** 性别 */
    @ApiModelProperty("性别")
    @Column
    private Integer sex;

    /** 电子邮件 */
    @ApiModelProperty("电子邮件")
    @Column(length = 255)
    private String email;

    /** 电话 */
    @ApiModelProperty("电话")
    @Column(length = 40)
    private String phone;

    /** 部门code */
    @ApiModelProperty("部门code")
    @Column(length = 40)
    private String orgCode;

    /** 状态  */
    @ApiModelProperty("状态")
    @Column(length = 12)
    private Integer status;

    /** 工号，唯一键  */
    @ApiModelProperty("工号，唯一键")
    @Column(length = 40)
    private String workNo;

    /** token */
    @ApiModelProperty("token")
    @Transient
    private String token;

    /** 角色Code */
    @ApiModelProperty("角色Code")
    @Transient
    private List<String> roleCode;

    /** 权限Code */
    @ApiModelProperty("权限Code")
    @Transient
    private List<String> permissionCode;

    /** 角色列表 */
    @ApiModelProperty("角色列表")
    @Transient
    private List<SysRole> roleList;


    @JsonIgnore
    public String getCredentialsSalt() {
        return username+salt;
    }
}
