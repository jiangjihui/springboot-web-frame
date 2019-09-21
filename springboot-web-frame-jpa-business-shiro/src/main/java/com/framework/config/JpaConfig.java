package com.framework.config;

import com.framework.jpa.BaseRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 配置扫描自定义jpa扩展类
 *https://blog.csdn.net/biboheart/article/details/80666617
 *
 * */
@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class,basePackages = {"com.framework.jpa", "com.business"})
public class JpaConfig {


}
