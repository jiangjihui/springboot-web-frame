package com.business.system.user.controller;

import com.business.system.user.controller.form.LoginForm;
import com.business.system.user.domain.UserInfo;
import com.business.system.user.service.UserInfoService;
import com.common.web.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 登录接口
 * 参考：http://www.ityouknow.com/springboot/2017/06/26/spring-boot-shiro.html
 *
 * @author jjh
 * @date 2019/9/20
 **/
@RestController
public class LoginController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登录接口
     * @return
     */
    @PostMapping("/login")
    public Object login(@RequestBody LoginForm form) {
        String username = form.getUsername();
        String password = form.getPassword();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        String msg = "";
        try {
            // 验证用户名密码
            subject.login(token);
            return "login success";
        }
        catch (IncorrectCredentialsException e) {
            msg = "登录密码错误";
            System.out.println("登录密码错误!!!" + e);
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
            System.out.println("登录失败次数过多!!!" + e);
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定";
            System.out.println("帐号已被锁定!!!" + e);
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用";
            System.out.println("帐号已被禁用!!!" + e);
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期";
            System.out.println("帐号已过期!!!" + e);
        } catch (UnknownAccountException e) {
            msg = "帐号不存在";
            System.out.println("帐号不存在!!!" + e);
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！";
            System.out.println("您没有得到相应的授权！" + e);
        } catch (Exception e) {
            System.out.println("出错！！！" + e);
        }

        return error(msg);
    }

    /**
     * 注册
     * shiro中的密码是如何验证是否匹配的
     * https://blog.csdn.net/chenyidong521/article/details/80245362
     * @return
     * @throws Exception
     */
    @PostMapping("/register")
    public Object register(@RequestBody UserInfo userInfo) throws Exception {
        UserInfo user = userInfoService.createUser(userInfo);
        return success(user);
    }
}
