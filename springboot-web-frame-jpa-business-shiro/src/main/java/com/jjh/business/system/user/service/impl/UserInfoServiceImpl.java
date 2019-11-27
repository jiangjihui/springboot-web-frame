package com.jjh.business.system.user.service.impl;

import cn.hutool.Hutool;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.jjh.business.system.user.controller.form.ResetPasswordForm;
import com.jjh.business.system.user.model.SysPermission;
import com.jjh.business.system.user.model.SysRole;
import com.jjh.business.system.user.model.UserInfo;
import com.jjh.business.system.user.repository.SysPermissionRepository;
import com.jjh.business.system.user.repository.SysRoleRepository;
import com.jjh.business.system.user.repository.UserInfoRepository;
import com.jjh.business.system.user.service.UserInfoService;
import com.jjh.common.exception.BusinessException;
import com.jjh.common.util.EncryptUtils;
import com.jjh.common.util.IdGenerateHelper;
import com.jjh.common.util.PojoUtils;
import com.jjh.common.util.SnowFlake;
import com.jjh.common.web.form.PageRequestForm;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

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

    @Override
    public List<UserInfo> list(PageRequestForm<UserInfo> form) {
        return userInfoRepository.list(form);
    }

    /**
     * 根据用户名查找用户
     * @param username  用户名
     * @return
     */
    @Override
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }

    /**
     * 创建用户
     * @param userInfo  用户信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserInfo add(UserInfo userInfo) {
        userInfo.setId(IdGenerateHelper.nextId());
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();

        // 用户名查重
        UserInfo u = userInfoRepository.findByUsername(username);
        if (u != null) {
            throw new BusinessException("用户名已存在，请检查~");
        }

        String salt = System.currentTimeMillis()+"";
        userInfo.setSalt(salt);
        // 密码加密（与shiro密码校验时的解密机制一致）
        String passwd = EncryptUtils.encryptPassword(username, password, userInfo.getSalt());
        userInfo.setPassword(passwd);
        return userInfoRepository.save(userInfo);
    }

    /**
     * 更新
     * @param entity 用户信息
     * @return 用户信息
     */
    @Override
    public UserInfo update(UserInfo entity) {
        UserInfo userInfo = userInfoRepository.findById(entity.getId()).get();
        if (Objects.isNull(userInfo)) {
            throw new BusinessException("未找到该用户，请检查");
        }
        PojoUtils.copyPropertiesIgnoreNull(entity, userInfo);
        return userInfoRepository.save(userInfo);
    }

    /**
     * 删除用户
     * @param ids 待删除的ID数组
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(@NotNull String ids) {
        userInfoRepository.deleteMany(ids.split(","));
    }

    /**
     *  更新用户密码
     * @param form 密码表单
     */
    @Override
    public UserInfo resetPassword(ResetPasswordForm form) {
        UserInfo userInfo = userInfoRepository.findById(form.getId()).get();
        if (userInfo == null) {
            throw new BusinessException("用户不存在，请检查");
        }
        String passwd = EncryptUtils.encryptPassword(userInfo.getUsername(), form.getNewPassword(), userInfo.getSalt());
        userInfo.setPassword(passwd);
        userInfoRepository.save(userInfo);
        return userInfo;
    }

    /**
     *  获取角色Code
     * @param id    用户ID
     */
    @Override
    public List<String> listSysRoleCode(String id) {
        return userInfoRepository.listSysRoleCode(id);
    }

    /**
     *  获取权限Code
     * @param id    用户ID
     */
    @Override
    public List<String> listSysPermissionCode(String id) {
        return userInfoRepository.listSysPermissionCode(id);
    }

}
