package com.jjh.framework.config;

import com.jjh.framework.jpa.BaseRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 配置扫描自定义jpa扩展类
 *https://blog.csdn.net/biboheart/article/details/80666617
 *
 * */
@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class,basePackages = {"com.jjh.framework.jpa", "com.jjh.business"})
public class JpaConfig {


}
