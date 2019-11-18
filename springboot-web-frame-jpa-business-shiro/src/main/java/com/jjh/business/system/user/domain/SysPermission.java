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
import java.util.ArrayList;
import java.util.List;

import static com.jjh.business.system.user.domain.SysPermission.TABLE_NAME;

/**
 * 权限实体类
 */
@ApiModel("权限实体类")
@Data
@Entity
@Table(name = TABLE_NAME)
public class SysPermission extends AuditBaseEntity {

    public static final String TABLE_NAME = TABLE_PREFIX+"sys_permission";

    /** 名称 */
    @ApiModelProperty("名称")
    @Column(length = 40)
    private String name;//名称

    /** 权限代码（menu例子：role:*，button例子：role:create,role:update,role:delete,role:view） */
    @ApiModelProperty("权限代码（menu例子：role:*，button例子：role:create,role:update,role:delete,role:view）")
    @Column(length = 40)
    private String code;

    /** 资源类型（目录/菜单/操作） */
    @ApiModelProperty("资源类型（目录/菜单/操作）")
//    @Column(columnDefinition="enum('menu','button')")
    @Column(length = 40)
    private String resourceType;

    /** 父编号 */
    @ApiModelProperty("父编号")
    @Column(length = 40)
    private Long parentId; //父编号

    /** 父编号列表 */
    @ApiModelProperty("父编号列表")
    @Column(length = 40)
    private String parentIds; //父编号列表

    /** 资源路径 */
    @ApiModelProperty("资源路径")
    @Column(length = 200)
    private String url;//资源路径.

    /** 状态（0=正常,1=停用） */
    @ApiModelProperty("状态（0=正常,1=停用）")
    @Column(length = 12)
    private Integer status;

    /** children 子权限 */
    @ApiModelProperty("children 子权限")
    @Transient
    private List<SysPermission> children = new ArrayList();

}
