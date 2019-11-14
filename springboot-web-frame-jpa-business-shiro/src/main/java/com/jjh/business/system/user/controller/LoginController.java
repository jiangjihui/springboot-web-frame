package com.jjh.business.system.user.controller;

import com.jjh.business.system.user.controller.form.LoginForm;
import com.jjh.business.system.user.domain.UserInfo;
import com.jjh.business.system.user.service.UserInfoService;
import com.jjh.common.web.controller.BaseController;
import com.jjh.common.web.form.SimpleResponseForm;
import com.jjh.framework.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录接口
 * 参考：http://www.ityouknow.com/springboot/2017/06/26/spring-boot-shiro.html
 *
 * @author jjh
 * @date 2019/9/20
 **/
@Api(tags = "登录管理")
@RestController
public class LoginController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);


    /**
     * 用户登录
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public SimpleResponseForm<UserInfo> login(@RequestBody LoginForm form) {
        String username = form.getUsername();
        String password = form.getPassword();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        String msg = "";
        try {
            // 验证用户名密码
            subject.login(token);
            return success(ShiroUtils.getUserInfo());
        }
        catch (IncorrectCredentialsException e) {
            msg = "登录密码错误";
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定";
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用";
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期";
        } catch (UnknownAccountException e) {
            msg = "帐号不存在";
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！";
        } catch (Exception e) {
            logger.info("系统异常：{}", e.getMessage());
        }
        logger.info(msg+" username:{}", username);
        return error(msg);
    }

    /**
     * 用户注册
     * shiro中的密码是如何验证是否匹配的
     * https://blog.csdn.net/chenyidong521/article/details/80245362
     * @return
     * @throws Exception
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public SimpleResponseForm<UserInfo> register(@RequestBody UserInfo userInfo) throws Exception {
        UserInfo user = userInfoService.createUser(userInfo);
        return success(user);
    }
}
