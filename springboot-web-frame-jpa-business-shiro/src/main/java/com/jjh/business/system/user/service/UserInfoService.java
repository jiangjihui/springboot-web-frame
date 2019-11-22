package com.jjh.business.system.user.service;


import com.jjh.business.system.user.controller.form.ResetPasswordForm;
import com.jjh.business.system.user.model.UserInfo;
import com.jjh.common.web.form.PageRequestForm;

import java.util.List;

/**
 * 用户信息 服务
 *
 * @author jjh
 * @date 2019/9/20
 */
public interface UserInfoService {

    /**
     * 用户列表
     * @param form 分页请求表单
     * @return
     */
    List<UserInfo> list(PageRequestForm<UserInfo> form);

    /**
     * 根据用户名查找用户
     * @param username  用户名
     * @return
     */
    UserInfo findByUsername(String username);

    /**
     * 添加用户
     * @param userInfo 用户信息
     * @return 用户信息
     */
    UserInfo add(UserInfo userInfo);

    /**
     * 更新用户
     * @param entity 用户信息
     * @return 用户信息
     */
    UserInfo update(UserInfo entity);

    /**
     * 删除用户
     * @param ids 待删除的ID数组
     */
    void delete(String ids);

    /**
     *  更新用户密码
     * @param form 密码表单
     */
    UserInfo resetPassword(ResetPasswordForm form);

    /**
     *  获取角色Code
     * @param id    用户ID
     */
    List<String> listSysRoleCode(String id);

    /**
     *  获取权限Code
     * @param id    用户ID
     */
    List<String> listSysPermissionCode(String id);
}
