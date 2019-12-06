package com.jjh.business.system.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.jjh.business.system.user.controller.form.UpdateUserRoleForm;
import com.jjh.business.system.user.model.SysRole;
import com.jjh.business.system.user.model.SysUserRoleMapping;
import com.jjh.business.system.user.repository.SysRolePermissionRepository;
import com.jjh.business.system.user.repository.SysRoleRepository;
import com.jjh.business.system.user.repository.SysUserRoleRepository;
import com.jjh.business.system.user.service.SysRoleService;
import com.jjh.common.constant.BaseConstants;
import com.jjh.common.exception.BusinessException;
import com.jjh.common.util.IdGenerateHelper;
import com.jjh.common.util.PojoUtils;
import com.jjh.common.web.form.PageRequestForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 角色实体类 服务层实现
 *
 * @author jjh
 * @date 2019/11/20
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleRepository sysRoleRepository;
    @Resource
    private SysUserRoleRepository sysUserRoleRepository;
    @Resource
    private SysRolePermissionRepository sysRolePermissionRepository;


    /**
     * 查询角色实体类列表
     *
     * @param form 查询条件
     * @return 角色实体类集合
     */
    @Override
    public List<SysRole> list(PageRequestForm<SysRole> form) {
        return sysRoleRepository.list(form);
    }

    /**
     * 新增角色实体类对象
     *
     * @param entity 待新增对象
     * @return 角色实体类对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRole add(SysRole entity) {
        entity.setId(IdGenerateHelper.nextId());
        return sysRoleRepository.save(entity);
    }

    /**
     * 更新角色实体类对象
     *
     * @param entity 待更新对象
     * @return 角色实体类对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRole update(SysRole entity) {
        SysRole oldEntity = sysRoleRepository.findById(entity.getId()).get();
        if (Objects.isNull(oldEntity)) {
            throw new BusinessException("对象不存在，请检查");
        }
        //对象属性复制
        PojoUtils.copyPropertiesIgnoreNull(entity, oldEntity);
        return sysRoleRepository.save(oldEntity);
    }

    /**
     * 删除角色实体类对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean del(String ids) {
        String[] idArray = ids.split(",");
        for (String roleId : idArray) {
            // 删除用户关联
            sysUserRoleRepository.deleteByRoleId(roleId);
            //删除权限关联
            sysRolePermissionRepository.deleteByRoleId(roleId);
        }

        sysRoleRepository.deleteMany(idArray);
        return true;
    }

    /**
     * 更新用户角色关联
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserRole(UpdateUserRoleForm form) {
        List<SysUserRoleMapping> oldMappings = sysUserRoleRepository.findByUserId(form.getUserId());
        if (CollectionUtil.isNotEmpty(oldMappings)) {
            sysUserRoleRepository.deleteInBatch(oldMappings);
        }
        if (StrUtil.isBlank(form.getRoleIds())) {
            return;
        }
        // 建立新关联
        String[] roleIdArray = form.getRoleIds().split(",");
        ArrayList<SysUserRoleMapping> mappingList = new ArrayList<>(roleIdArray.length);
        for (String roleId : roleIdArray) {
            SysUserRoleMapping mapping = new SysUserRoleMapping();
            mapping.setId(IdGenerateHelper.nextId());
            mapping.setUserId(form.getUserId());
            mapping.setRoleId(roleId);
            mappingList.add(mapping);
        }
        sysUserRoleRepository.saveAll(mappingList);
    }

    /**
     * 查询用户角色关联
     * @param userId    用户ID
     * @return  角色ID数组
     */
    @Override
    public List<String> queryUserRole(String userId) {
        List<SysUserRoleMapping> mappings = sysUserRoleRepository.findByUserId(userId);
        if (CollectionUtil.isEmpty(mappings)) {
            return null;
        }
        List<String> roleIdList = new ArrayList<String>(mappings.size());
        for (SysUserRoleMapping mapping : mappings) {
            roleIdList.add(mapping.getRoleId());
        }
        return roleIdList;
    }

    /**
     * 查询所有角色
     * @return  角色列表
     */
    @Override
    public List<SysRole> queryAll() {
        return sysRoleRepository.findByStatus(BaseConstants.STATUS_NOMAL);
    }

}
