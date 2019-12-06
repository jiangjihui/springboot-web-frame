package com.jjh.business.system.user.controller;

import com.jjh.business.common.file.controller.form.UploadFileForm;
import com.jjh.business.system.user.controller.form.QueryUserForm;
import com.jjh.business.system.user.controller.form.ResetPasswordForm;
import com.jjh.business.system.user.controller.form.UserFrozenForm;
import com.jjh.business.system.user.model.SysUser;
import com.jjh.business.system.user.service.SysUserService;
import com.jjh.common.web.controller.BaseController;
import com.jjh.common.web.form.PageRequestForm;
import com.jjh.common.web.form.PageResponseForm;
import com.jjh.common.web.form.SimpleResponseForm;
import com.jjh.framework.plugin.excel.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户管理
 *
 * @author jjh
 * @date 2019/11/14
 **/
@Api(tags = "用户管理")
@RestController
@RequestMapping("/system/user/sys_user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);


    /**
     * 用户列表
     * @param form 分页请求参数
     * @return 用户列表
     */
    @RequiresPermissions("system:sysUser:list")
    @ApiOperation("用户列表")
    @PostMapping("/list")
    public SimpleResponseForm<PageResponseForm<SysUser>> list(@RequestBody PageRequestForm<QueryUserForm> form) {
        List<SysUser> list = sysUserService.list(form);
        return page(form, list);
    }

    /**
     * 新增用户
     * @param entity 用户信息
     * @return 用户信息
     */
    @ApiOperation(value = "保存")
    @PostMapping("/add")
    public SimpleResponseForm<SysUser> add(HttpServletRequest request, @RequestBody SysUser entity) {
        SysUser result = sysUserService.add(entity);
        return success(result);
    }

    /**
     * 更新
     * @param entity 用户信息
     * @return 用户信息
     */
    @ApiOperation(value = "更新")
    @PostMapping("/update")
    public SimpleResponseForm<SysUser> update(HttpServletRequest request, @RequestBody SysUser entity) {
        SysUser result = sysUserService.update(entity);
        return success(result);
    }

    /**
     * 删除
     * @param ids 待删除的ID数组
     */
    @ApiOperation(value = "删除")
    @GetMapping("/delete")
    public SimpleResponseForm<String> delete(String ids) {
        sysUserService.delete(ids);
        return success();
    }

    /**
     * 用户重置（密码）
     * @param form
     * @return
     */
    @ApiOperation(value = "用户重置（密码）")
    @PostMapping(value = "/reset")
    public SimpleResponseForm<String> reset(@Valid @RequestBody ResetPasswordForm form) {
        sysUserService.resetPassword(form);
        return success();
    }

    /**
     * 冻结/解冻
     * @param list
     * @return
     */
    @ApiOperation(value = "冻结/解冻")
    @PostMapping("/frozen")
    public SimpleResponseForm<String> frozen(@Valid @RequestBody List<UserFrozenForm> list) {
        sysUserService.frozen(list);
        return success();
    }

    /**
     * 用户导出
     * @param form 分页请求参数
     * @return 导出文件
     */
    @ApiOperation("用户导出")
    @PostMapping("/export")
    public SimpleResponseForm<String> export(@RequestBody PageRequestForm<QueryUserForm> form) {
        List<SysUser> list = sysUserService.list(form);
        ExcelUtil<SysUser> excelUtil = new ExcelUtil<>(SysUser.class);
        return success(excelUtil.exportExcel(list, "用户列表"));
    }

    /**
     * 用户导入模板
     * @return
     */
    @ApiOperation(value = "用户导入模板")
    @GetMapping("/importTemplate")
    public SimpleResponseForm<String> importTemplate() {
        ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
        return success(util.importTemplateExcel( "用户导入"));
    }

    /**
     * 用户数据导入
     * @return
     */
    @ApiOperation(value = "用户数据导入")
    @PostMapping("/importData")
    public SimpleResponseForm<String> importData(UploadFileForm form, boolean updateSupport) {
        if (form.getFile() != null) {
            ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
            List<SysUser> list = util.importExcel(form.getFile());
            sysUserService.importData(list, updateSupport);
        }
        return success();
    }

}
