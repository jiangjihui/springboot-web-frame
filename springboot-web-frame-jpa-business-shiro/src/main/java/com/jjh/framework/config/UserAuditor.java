package com.jjh.framework.config;

import com.jjh.framework.jwt.JWTConstants;
import com.jjh.framework.jwt.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * jpa 审计：获取当前用户名
 *
 */
@Configuration
public class UserAuditor implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        String token = getRequestAttributes().getRequest().getHeader(JWTConstants.X_ACCESS_TOKEN);
        return Optional.of(JwtUtil.getUsername(token));
    }

    /**
     * 获取web请求
     * @return
     */
    public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }
}
