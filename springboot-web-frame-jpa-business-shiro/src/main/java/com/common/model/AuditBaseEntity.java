package com.common.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * 业务对象基础类（包含审计功能）
 *
 * JPA Audit
 * https://www.jianshu.com/p/14cb69646195
 * 首先申明实体类，需要在类上加上注解@EntityListeners(AuditingEntityListener.class)，
 * 其次在application启动类中加上注解EnableJpaAuditing，
 * 同时在需要的字段上加上@CreatedDate、@CreatedBy、@LastModifiedDate、@LastModifiedBy等注解。
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditBaseEntity extends BaseEntity {

    protected static final String TABLE_PREFIX = "b_";

    @CreatedBy
    @Column(length = 40)
    private String createBy;

    @LastModifiedBy
    @Column(length = 40)
    private String updateBy;



}
