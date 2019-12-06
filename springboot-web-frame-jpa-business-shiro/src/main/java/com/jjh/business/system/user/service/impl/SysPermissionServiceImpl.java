package com.jjh.business.system.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.jjh.business.system.user.controller.form.UpdateRolePermissionForm;
import com.jjh.business.system.user.model.RolePermissionMapping;
import com.jjh.business.system.user.model.SysPermission;
import com.jjh.business.system.user.repository.SysPermissionRepository;
import com.jjh.business.system.user.repository.SysRolePermissionRepository;
import com.jjh.business.system.user.service.SysPermissionService;
import com.jjh.common.constant.BaseConstants;
import com.jjh.common.exception.BusinessException;
import com.jjh.common.util.IdGenerateHelper;
import com.jjh.common.util.PojoUtils;
import com.jjh.common.web.form.PageRequestForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 权限实体类 服务层实现
 *
 * @author jjh
 * @date 2019/11/20
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private SysPermissionRepository sysPermissionRepository;
    @Resource
    private SysRolePermissionRepository sysRolePermissionRepository;


    /**
     * 查询权限实体类列表
     *
     * @param form 查询条件
     * @return 权限实体类集合
     */
    @Override
    public List<SysPermission> list(PageRequestForm<SysPermission> form) {
        return sysPermissionRepository.list(form);
    }

    /**
     * 新增权限实体类对象
     *
     * @param entity 待新增对象
     * @return 权限实体类对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysPermission add(SysPermission entity) {
        entity.setId(IdGenerateHelper.nextId());
        return sysPermissionRepository.save(entity);
    }

    /**
     * 更新权限实体类对象
     *
     * @param entity 待更新对象
     * @return 权限实体类对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysPermission update(SysPermission entity) {
        SysPermission oldEntity = sysPermissionRepository.findById(entity.getId()).get();
        if (Objects.isNull(oldEntity)) {
            throw new BusinessException("对象不存在，请检查");
        }
        //对象属性复制
        PojoUtils.copyPropertiesIgnoreNull(entity, oldEntity);
        return sysPermissionRepository.save(oldEntity);
    }

    /**
     * 删除权限实体类对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean del(String ids) {
        String[] idArray = ids.split(",");
        for (String permissionId : idArray) {
            // 删除角色关联
            sysRolePermissionRepository.deleteByPermissionId(permissionId);
            // 删除上下级关联
            sysPermissionRepository.deleteByParentId(permissionId);
        }

        sysPermissionRepository.deleteMany(idArray);
        return true;
    }

    /**
     * 更新角色权限关联
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRolePermission(UpdateRolePermissionForm form) {
        // 清除原有关联
        List<RolePermissionMapping> oldMappings = sysRolePermissionRepository.findByRoleId(form.getRoleId());
        if (CollectionUtil.isNotEmpty(oldMappings)) {
            sysRolePermissionRepository.deleteInBatch(oldMappings);
        }
        if (StrUtil.isBlank(form.getLastPermissionIds())) {
            return;
        }
        // 建立新关联
        String[] permissionIdArray = form.getLastPermissionIds().split(",");
        ArrayList<RolePermissionMapping> mappingList = new ArrayList<>(permissionIdArray.length);
        for (String permissionId : permissionIdArray) {
            RolePermissionMapping mapping = new RolePermissionMapping();
            mapping.setId(IdGenerateHelper.nextId());
            mapping.setRoleId(form.getRoleId());
            mapping.setPermissionId(permissionId);
            mappingList.add(mapping);
        }
        sysRolePermissionRepository.saveAll(mappingList);
    }

    /**
     * 查询角色权限关联
     * @param roleId    角色ID
     * @return 权限ID数组
     */
    @Override
    public List<String> queryRolePermission(String roleId) {
        List<RolePermissionMapping> mappings = sysRolePermissionRepository.findByRoleId(roleId);
        if (CollectionUtil.isEmpty(mappings)) {
            return null;
        }
        List<String> permissionIdList = new ArrayList<String>(mappings.size());
        for (RolePermissionMapping mapping : mappings) {
            permissionIdList.add(mapping.getPermissionId());
        }
        return permissionIdList;
    }

    /**
     * 查询所有权限（树图关联）
     * @return  权限列表
     */
    @Override
    public List<SysPermission> queryTreeList() {
        List<SysPermission> result = new LinkedList<>();
        List<SysPermission> permissionList = sysPermissionRepository.findByStatusOrderBySortNo(BaseConstants.STATUS_NOMAL);
        for (SysPermission permission : permissionList) {
            // 顶级权限
            if (StrUtil.isBlank(permission.getParentId())) {
                result.add(permission);
            }
        }
//        permissionList.removeAll(result);
        // 串联树结构
        HashMap<String, SysPermission> permissionIdMap = new HashMap<>();
        for (SysPermission permission : permissionList) {
            permissionIdMap.put(permission.getId(), permission);
        }
        for (SysPermission permission : permissionList) {
            if (StrUtil.isNotBlank(permission.getParentId())) {
                SysPermission parent = permissionIdMap.get(permission.getParentId());
                if (parent != null) {
                    List<SysPermission> children = parent.getChildren();
                    if (children == null) {
                        children = new LinkedList<>();
                        parent.setChildren(children);
                    }
                    children.add(permission);
                }
            }
        }
        return result;
    }

}
