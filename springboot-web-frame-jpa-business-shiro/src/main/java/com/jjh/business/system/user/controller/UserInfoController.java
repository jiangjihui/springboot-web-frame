package com.jjh.business.system.user.controller;

import com.jjh.business.system.user.controller.form.ResetPasswordForm;
import com.jjh.business.system.user.domain.UserInfo;
import com.jjh.business.system.user.service.UserInfoService;
import com.jjh.common.web.controller.BaseController;
import com.jjh.common.web.form.PageRequestForm;
import com.jjh.common.web.form.PageResponseForm;
import com.jjh.common.web.form.SimpleForm;
import com.jjh.common.web.form.SimpleResponseForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/system/user_info")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);


    /**
     * 用户列表
     * @param form 分页请求参数
     * @return 用户列表
     */
    @ApiOperation("用户列表")
    @PostMapping("/list")
    public SimpleResponseForm<PageResponseForm<UserInfo>> list(@RequestBody PageRequestForm<UserInfo> form) {
        return page(userInfoService.list(form), form);
    }

    /**
     * 添加用户
     * @param entity 用户信息
     * @return 用户信息
     */
    @ApiOperation(value = "保存")
    @PostMapping("/save")
    public SimpleResponseForm<UserInfo> add(HttpServletRequest request, @RequestBody UserInfo entity) {
        UserInfo result = userInfoService.add(entity);
        return success(result);
    }

    /**
     * 更新
     * @param entity 用户信息
     * @return 用户信息
     */
    @ApiOperation(value = "更新")
    @PostMapping("/update")
    public SimpleResponseForm<UserInfo> update(HttpServletRequest request, @RequestBody UserInfo entity) {
        UserInfo result = userInfoService.update(entity);
        return success(result);
    }

    /**
     * 删除
     * @param ids 待删除的ID数组
     */
    @ApiOperation(value = "删除")
    @GetMapping("/delete")
    public SimpleResponseForm<String> delete(String ids) {
        userInfoService.delete(ids);
        return success();
    }

    /**
     * 用户重置（密码）
     * @param form
     * @return
     */
    @ApiOperation(value = "用户重置（密码）")
    @RequestMapping(value = "/reset",method = RequestMethod.POST)
    public SimpleResponseForm<String> reset(@Valid @RequestBody ResetPasswordForm form) {
        userInfoService.resetPassword(form);
        return success();
    }

}
