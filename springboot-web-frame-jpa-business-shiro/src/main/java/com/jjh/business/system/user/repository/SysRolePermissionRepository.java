package com.jjh.business.system.user.repository;

import com.jjh.business.system.user.model.RolePermissionMapping;
import com.jjh.framework.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限关联 数据层
 *
 * @author jjh
 * @date 2019/12/03
 **/
@Repository
public interface SysRolePermissionRepository extends BaseRepository<RolePermissionMapping, String> {

    /**
     * 根据角色ID查找权限
     * @param roleId
     * @return
     */
    List<RolePermissionMapping> findByRoleId(String roleId);

    /**
     * 根据角色ID删除关联
     * @param roleId    角色ID
     */
    void deleteByRoleId(String roleId);

    /**
     * 根据权限ID删除关联
     * @param permissionId    权限ID
     */
    void deleteByPermissionId(String permissionId);
}
