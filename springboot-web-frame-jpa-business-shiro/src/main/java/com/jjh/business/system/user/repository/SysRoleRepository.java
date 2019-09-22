package com.jjh.business.system.user.repository;

import com.jjh.business.system.user.domain.SysRole;
import com.jjh.framework.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色表 数据层
 *
 * @author jjh
 * @date 2019/9/20
 **/
@Repository
public interface SysRoleRepository extends BaseRepository<SysRole, String> {

    /**
     * 根据用户名查找所拥有的角色
     *
     * @return
     */
    @Query("select r from SysRole r,UserInfoRoleMapping m where r.id = m.roleId and m.userId = ?1")
    List<SysRole> findByUserInfoId(String userInfoId);


}
