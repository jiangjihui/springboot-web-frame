package com.jjh.business.system.user.controller;

import cn.hutool.core.util.StrUtil;
import com.jjh.business.system.user.controller.form.LoginForm;
import com.jjh.business.system.user.model.UserInfo;
import com.jjh.business.system.user.service.UserInfoService;
import com.jjh.common.exception.BusinessException;
import com.jjh.common.util.EncryptUtils;
import com.jjh.common.web.controller.BaseController;
import com.jjh.common.web.form.SimpleResponseForm;
import com.jjh.framework.jwt.JwtUtil;
import com.jjh.framework.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    /**
     * 用户登录
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public SimpleResponseForm<UserInfo> login(@RequestBody LoginForm form) {
        String username = form.getUsername();
        String password = form.getPassword();

        // 用户校验
        checkLoginForm(form);
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (userInfo == null) {
            throw new BusinessException("未找到该用户，请重新再试");
        }
        String passwd = EncryptUtils.encryptPassword(username, password, userInfo.getSalt());
        if (!passwd.equals(userInfo.getPassword())) {
            throw new BusinessException("密码错误，请检查后重新再试");
        }

        // 生成token
        String sign = JwtUtil.sign(username, passwd);
        userInfo.setToken(sign);
        return success(userInfo);

        // Shiro安全验证
/*        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        String msg = "";
        try {
            // 验证用户名密码
            subject.login(token);
            userInfo.getPassword()(ShiroUtils.getUserInfo());
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
        return error(msg);*/
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
    public SimpleResponseForm<UserInfo> register(@Valid @RequestBody UserInfo userInfo) throws Exception {
        UserInfo user = userInfoService.add(userInfo);
        return success(user);
    }

    /**
     * 登录表单校验
     * @param form
     */
    public void checkLoginForm(LoginForm form) {
        if (StrUtil.isBlank(form.getUsername())) {
            throw new BusinessException("用户名不能为空");
        }
        if (StrUtil.isBlank(form.getPassword())) {
            throw new BusinessException("密码不能为空");
        }
    }
}
