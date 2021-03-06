package com.jjh.business.system.user.repository;

import com.jjh.business.system.user.model.SysPermission;
import com.jjh.framework.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限表 数据层
 *
 * @author jjh
 * @date 2019/9/20
 **/
@Repository
public interface SysPermissionRepository extends BaseRepository<SysPermission, String> {

    /**
     * 根据角色ID查找所拥有的权限
     *
     * @return
     */
    @Query("select p from SysPermission p,RolePermissionMapping m where p.id = m.permissionId and m.roleId = ?1")
    List<SysPermission> findBySysRoleId(String sysRoleId);

    /**
     * 根据权限ID删除权限
     * @param permissionId  权限ID
     */
    void deleteByParentId(String permissionId);

    /**
     * 根据权限ID删除权限
     * @param status  状态
     * @return 角色列表
     */
    List<SysPermission> findByStatusOrderBySortNo(Integer status);

}
