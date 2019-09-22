package com.jjh.business.system.user.service;

import com.jjh.business.system.user.domain.SysPermission;
import com.jjh.business.system.user.domain.SysRole;
import com.jjh.business.system.user.domain.UserInfo;
import com.jjh.business.system.user.repository.SysPermissionRepository;
import com.jjh.business.system.user.repository.SysRoleRepository;
import com.jjh.business.system.user.repository.UserInfoRepository;
import com.jjh.common.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户信息 服务实现
 *
 * @author jjh
 * @date 2019/9/20
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoRepository userInfoRepository;
    @Resource
    private SysRoleRepository roleRepository;
    @Resource
    private SysPermissionRepository permissionRepository;

    /**
     * 根据用户名查找用户
     * @param username  用户名
     * @return
     */
    @Override
    public UserInfo findByUsername(String username) {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        // 获取角色权限信息
        List<SysRole> roleList = roleRepository.findByUserInfoId(userInfo.getId());
        if (CollectionUtils.isNotEmpty(roleList)) {
            for (SysRole sysRole : roleList) {
                List<SysPermission> permissionList = permissionRepository.findBySysRoleId(sysRole.getId());
                if (CollectionUtils.isNotEmpty(permissionList)) {
                    sysRole.setPermissionList(permissionList);
                }
            }
            userInfo.setRoleList(roleList);
        }
        return userInfo;
    }

    /**
     * 列出所有用户
     * @return
     */
    @Override
    public List list() {
        return userInfoRepository.findAll();
    }

    /**
     * 创建用户
     * @param userInfo  用户信息
     * @return
     */
    @Override
    public UserInfo createUser(UserInfo userInfo) {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();

        // 用户名查重
        UserInfo u = userInfoRepository.findByUsername(username);
        if (u != null) {
            throw new BusinessException("用户名已存在，请检查~");
        }

        String salt = System.currentTimeMillis()+"";
        userInfo.setSalt(salt);
        // 密码加密
        String passwd = new SimpleHash("MD5", password, username + salt, 2).toString();
        userInfo.setPassword(passwd);
        userInfoRepository.save(userInfo);
        return userInfo;
    }

}
