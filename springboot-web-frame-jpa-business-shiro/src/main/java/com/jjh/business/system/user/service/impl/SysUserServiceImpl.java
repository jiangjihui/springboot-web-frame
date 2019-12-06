package com.jjh.business.system.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.jjh.business.system.user.controller.form.QueryUserForm;
import com.jjh.business.system.user.controller.form.ResetPasswordForm;
import com.jjh.business.system.user.controller.form.UserFrozenForm;
import com.jjh.business.system.user.model.SysUser;
import com.jjh.business.system.user.repository.SysUserRepository;
import com.jjh.business.system.user.repository.SysUserRoleRepository;
import com.jjh.business.system.user.service.SysUserService;
import com.jjh.common.constant.BaseConstants;
import com.jjh.common.exception.BusinessException;
import com.jjh.common.key.CacheConstants;
import com.jjh.common.util.EncryptUtils;
import com.jjh.common.util.IdGenerateHelper;
import com.jjh.common.util.PojoUtils;
import com.jjh.common.web.form.PageRequestForm;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 用户信息 服务实现
 *
 * @author jjh
 * @date 2019/9/20
 **/
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private SysUserRoleRepository sysUserRoleRepository;

    @Override
    public List<SysUser> list(PageRequestForm<QueryUserForm> form) {
        return sysUserRepository.listSpecific(form);
//        Specification specification = new Specification<Object>() {
//            @Override
//            public Predicate toPredicate(Root<Object> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> predicateList = new ArrayList<>();
//                QueryUserForm filter = form.getFilter();
//                if (filter.getCreateTimeWithStartDate() != null) {
//                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), filter.getCreateTimeWithStartDate()));
//                }
//                if (filter.getCreateTimeWithEndDate() != null) {
//                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime"), filter.getCreateTimeWithEndDate()));
//                }
//                if (StrUtil.isNotBlank(filter.getUsername())) {
//                    predicateList.add(criteriaBuilder.like(root.get("username"), filter.getUsername()));
//                }
//                if (StrUtil.isNotBlank(filter.getEmail())) {
//                    predicateList.add(criteriaBuilder.like(root.get("email"), filter.getEmail()));
//                }
//                if (StrUtil.isNotBlank(filter.getPhone())) {
//                    predicateList.add(criteriaBuilder.like(root.get("email"), filter.getEmail()));
//                }
//                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
//                return null;
//            }
//        };
//        return sysUserRepository.listSpecific(form, specification);
    }

    /**
     * 根据用户名查找用户
     * @param username  用户名
     * @return
     */
    @Override
    @Cacheable(cacheNames= CacheConstants.SYS_USER_NAME, key="#username")
    public SysUser findByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }

    /**
     * 创建用户
     * @param sysUser  用户信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysUser add(SysUser sysUser) {
        sysUser.setId(IdGenerateHelper.nextId());
        String username = sysUser.getUsername();
        String password = sysUser.getPassword();

        // 用户名查重
        SysUser u = sysUserRepository.findByUsername(username);
        if (u != null) {
            throw new BusinessException("用户名已存在，请检查~");
        }

        String salt = String.valueOf(System.currentTimeMillis());
        sysUser.setSalt(salt);
        // 密码加密（与shiro密码校验时的解密机制一致）
        String passwd = EncryptUtils.encryptPassword(username, password, sysUser.getSalt());
        sysUser.setPassword(passwd);
        return sysUserRepository.save(sysUser);
    }

    /**
     * 更新
     * @param entity 用户信息
     * @return 用户信息
     */
    @Override
    @CacheEvict(value = {CacheConstants.SYS_USER_NAME}, allEntries = true)
    public SysUser update(SysUser entity) {
        SysUser sysUser = sysUserRepository.findById(entity.getId()).orElseThrow(() -> new BusinessException("未找到该用户，请检查"));
        PojoUtils.copyPropertiesIgnoreNull(entity, sysUser);
        return sysUserRepository.save(sysUser);
    }

    /**
     * 删除用户
     * @param ids 待删除的ID数组
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @CacheEvict(value = {CacheConstants.SYS_USER_NAME}, allEntries = true)
    public void delete(@NotNull String ids) {
        // 删除角色关联
        String[] idArray = ids.split(",");
        for (String userId : idArray) {
            sysUserRoleRepository.deleteByUserId(userId);
        }

        sysUserRepository.deleteMany(ids.split(","));
    }

    /**
     *  更新用户密码
     * @param form 密码表单
     */
    @Override
    @CacheEvict(value = {CacheConstants.SYS_USER_NAME}, allEntries = true)
    public SysUser resetPassword(ResetPasswordForm form) {
        SysUser sysUser = sysUserRepository.findById(form.getId()).orElseThrow(() -> new BusinessException("用户不存在，请检查"));
        String passwd = EncryptUtils.encryptPassword(sysUser.getUsername(), form.getNewPassword(), sysUser.getSalt());
        sysUser.setPassword(passwd);
        sysUserRepository.save(sysUser);
        return sysUser;
    }

    /**
     *  获取角色Code
     * @param id    用户ID
     */
    @Override
    public List<String> listSysRoleCode(String id) {
        return sysUserRepository.listSysRoleCode(id);
    }

    /**
     *  获取权限Code
     * @param id    用户ID
     */
    @Override
    public List<String> listSysPermissionCode(String id) {
        return sysUserRepository.listSysPermissionCode(id);
    }

    /**
     * 冻结/解冻
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @CacheEvict(value = {CacheConstants.SYS_USER_NAME}, allEntries = true)
    public void frozen(List<UserFrozenForm> list) {
        for (UserFrozenForm form : list) {
            Optional<SysUser> ops = sysUserRepository.findById(form.getId());
            SysUser sysUser = ops.orElseThrow(() -> new BusinessException("未找到该用户 [ID]="+form.getId()));
            sysUser.setStatus(form.getStatus());
            sysUserRepository.save(sysUser);
        }
    }

    /**
     * 导入数据
     * @param list
     * @param updateSupport 是否更新现有数据
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @CacheEvict(value = {CacheConstants.SYS_USER_NAME}, allEntries = true)
    public void importData(List<SysUser> list, Boolean updateSupport) {
        if (CollectionUtil.isEmpty(list)) {
            throw new BusinessException("待导出数据为空");
        }
        List<SysUser> saveList = new ArrayList<SysUser>();
        for (SysUser sysUser : list) {
            if (StrUtil.isBlank(sysUser.getUsername())) {
                throw new BusinessException("导入失败，用户名为空。请检查");
            }
            SysUser oldSysUser = this.findByUsername(sysUser.getUsername());
            if (oldSysUser != null) {
                if (Boolean.TRUE.equals(updateSupport)) {
                    PojoUtils.copyPropertiesIgnoreNull(sysUser, oldSysUser);
                    saveList.add(oldSysUser);
//                    sysUserRepository.save(oldSysUser);
                }
            }
            else {
                sysUser.setId(IdGenerateHelper.nextId());
                // 密码重置
                sysUser.setSalt(String.valueOf(System.currentTimeMillis()));
                String password = EncryptUtils.encryptPassword(sysUser.getUsername(), sysUser.getPassword(), sysUser.getSalt());
                sysUser.setPassword(password);
                if (sysUser.getStatus() == null) {
                    sysUser.setStatus(BaseConstants.STATUS_NOMAL);
                }
                saveList.add(sysUser);
//                sysUserRepository.save(oldSysUser);
            }
        }
        sysUserRepository.saveAll(saveList);
    }

}
