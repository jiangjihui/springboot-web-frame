package com.jjh.framework.shiro;

import com.jjh.business.system.user.model.SysPermission;
import com.jjh.business.system.user.model.SysRole;
import com.jjh.business.system.user.model.UserInfo;
import com.jjh.business.system.user.service.UserInfoService;
import com.jjh.common.exception.BusinessException;
import com.jjh.framework.jwt.JwtToken;
import com.jjh.framework.jwt.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;

@Component
public class MyShiroRealm extends AuthorizingRealm {

    @Lazy   //https://www.jianshu.com/p/8dce8a2e94cf
    @Autowired
    private UserInfoService userInfoService;

    private static final Logger logger = LoggerFactory.getLogger(AuthorizingRealm.class);

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //权限配置
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();

        // 从用户信息获取，不重复调用接口获取角色权限。（直接将权限信息缓存到用户信息，随用户信息走）
        authorizationInfo.setRoles(new HashSet<String>(userInfo.getRoleCode()));
        authorizationInfo.setStringPermissions(new HashSet<String>(userInfo.getPermissionCode()));

        return authorizationInfo;
    }

    //身份配置
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /*JWT校验*/
        return jwtAuth(authenticationToken);

        /*Shiro校验*/
        //获取用户的输入的账号.
 /*       String username = (String)authenticationToken.getPrincipal();
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo = userInfoService.findByUsername(username);
        logger.info("Get userInfo: "+userInfo);
        if(userInfo == null){
            return null;
        }

        //获取用户角色
        List<String> roleList = userInfoService.listSysRoleCode(userInfo.getId());
        // 获取用户权限
        List<String> permissionList = userInfoService.listSysPermissionCode(userInfo.getId());
        userInfo.setRoleCode(roleList);
        userInfo.setPermissionCode(permissionList);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;*/
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }


    /**
     * JWT用户身份认证
     * @return
     */
    public AuthenticationInfo jwtAuth(AuthenticationToken authenticationToken) {
        String token = (String) authenticationToken.getCredentials();
        String username = JwtUtil.getUsername(token);
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo = userInfoService.findByUsername(username);
        if(userInfo == null){
            return null;
        }

        if (username == null || !JwtUtil.verify(token, username, userInfo.getPassword())) {
            throw new AuthenticationException("token认证失败！");
        }
        //获取用户角色
        List<String> roleList = userInfoService.listSysRoleCode(userInfo.getId());
        // 获取用户权限
        List<String> permissionList = userInfoService.listSysPermissionCode(userInfo.getId());
        userInfo.setRoleCode(roleList);
        userInfo.setPermissionCode(permissionList);
        return new SimpleAuthenticationInfo(userInfo, token, "MyRealm");
    }
}
