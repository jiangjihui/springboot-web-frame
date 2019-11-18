package com.jjh.business.system.user.domain;

import com.jjh.common.model.AuditBaseEntity;
import com.jjh.common.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

import static com.jjh.business.system.user.domain.SysRole.TABLE_NAME;

/**
 * 角色实体类
 */
@ApiModel("角色实体类")
@Data
@Entity
@Table(name = TABLE_NAME)
public class SysRole extends AuditBaseEntity {

    public static final String TABLE_NAME = TABLE_PREFIX+"sys_role";

    /** 角色名称 */
    @ApiModelProperty("角色名称")
    @Column(length = 40)
    private String name;

    /** 角色代码 */
    @ApiModelProperty("角色代码")
    @Column(length = 40)
    private String code;

    /** 状态（启用/停用） */
    @ApiModelProperty("状态（0=启用,1=停用）")
    @Column(length = 12)
    private Integer status;

    /** 权限列表 */
    @ApiModelProperty("权限列表")
    @Transient
    private List<SysPermission> permissionList;

}
