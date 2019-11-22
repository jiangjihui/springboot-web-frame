package com.jjh.business.system.user.repository;

import com.jjh.business.system.user.model.UserInfo;
import com.jjh.framework.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author jjh
 * @date 2019/9/20
 **/
@Repository
public interface UserInfoRepository extends BaseRepository<UserInfo, String> {

    /**
     * 根据用户名查找用户
     * @param username  用户名
     * @return
     */
    UserInfo findByUsername(String username);

    /**
     *  获取角色Code
     * @param id    用户ID
     */
    @Query("select r.code from SysRole r,UserInfoRoleMapping m where r.id = m.roleId and m.userId = ?1")
    List<String> listSysRoleCode(String id);

    /**
     *  获取权限Code
     * @param id    用户ID
     */
    @Query("select p.code from SysPermission p,RolePermissionMapping pm,UserInfoRoleMapping rm where p.id=pm.permissionId and pm.roleId=rm.roleId and rm.userId= ?1")
    List<String> listSysPermissionCode(String id);
}
