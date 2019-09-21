package com.business.system.user.service;


import com.business.system.user.domain.UserInfo;

import java.util.List;

/**
 * 用户信息 服务
 *
 * @author jjh
 * @date 2019/9/20
 */
public interface UserInfoService {

    /**
     * 根据用户名查找用户
     * @param username  用户名
     * @return
     */
    public UserInfo findByUsername(String username);

    /**
     * 获取用户列表
     * @return
     */
    public List list();

    /**
     * 创建用户
     * @param userInfo  用户信息
     * @return
     */
    public UserInfo createUser(UserInfo userInfo);

}
