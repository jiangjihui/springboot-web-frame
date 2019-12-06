package com.jjh.business.system.user.repository;

import com.jjh.business.system.user.model.SysUserRoleMapping;
import com.jjh.framework.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色关联 数据层
 *
 * @author jjh
 * @date 2019/12/03
 **/
@Repository
public interface SysUserRoleRepository extends BaseRepository<SysUserRoleMapping, String> {

    /**
     * 根据用户ID查找角色
     * @param userId    用户ID
     * @return
     */
    List<SysUserRoleMapping> findByUserId(String userId);

    /**
     * 根据用户ID删除关联
     * @param userId    用户ID
     */
    void deleteByUserId(String userId);

    /**
     * 根据角色ID删除关联
     * @param roleId    角色ID
     */
    void deleteByRoleId(String roleId);

}
