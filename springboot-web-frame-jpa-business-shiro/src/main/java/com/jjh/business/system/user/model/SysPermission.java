package com.jjh.business.system.user.model;

import com.jjh.common.model.AuditBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

import static com.jjh.business.system.user.model.SysPermission.TABLE_NAME;

/**
 * 权限实体类
 */
@ApiModel("权限实体类")
@Data
@Entity
@Table(name = TABLE_NAME)
public class SysPermission extends AuditBaseEntity {

    public static final String TABLE_NAME = TABLE_PREFIX+"sys_permission";

    /** 父编号 */
    @ApiModelProperty("父编号")
    @Column(length = 40)
    private String parentId;

    /** 名称 */
    @ApiModelProperty("名称")
    @Column(length = 40)
    private String name;

    /** 权限代码（menu例子：role:*，button例子：role:create,role:update,role:delete,role:view） */
    @ApiModelProperty("权限代码（menu例子：role:*，button例子：role:create,role:update,role:delete,role:view）")
    @Column(length = 40)
    private String code;

    /** 类型（0：一级菜单；1：子菜单 ；2：按钮权限） */
    @ApiModelProperty("类型（0：一级菜单；1：子菜单 ；2：按钮权限）")
    @Column(length = 40)
    private String menuType;

    /** 菜单图标 */
    @ApiModelProperty("菜单图标")
    @Column(length = 200)
    private String icon;

    /** 组件 */
    @ApiModelProperty("组件")
    @Column(length = 100)
    private String component;

    /** 路径 */
    @ApiModelProperty("路径")
    @Column(length = 100)
    private String url;

    /** 菜单排序 */
    @ApiModelProperty("菜单排序")
    @Column
    private Integer sortNo;

    /** 描述 */
    @ApiModelProperty("描述")
    @Column(length = 200)
    private String description;

    /** 状态（0=正常,1=停用） */
    @ApiModelProperty("状态（0=正常,1=停用）")
    @Column(length = 12)
    private Integer status;

    /** children 子权限 */
    @ApiModelProperty("children 子权限")
    @Transient
    private List<SysPermission> children = new ArrayList();

}
