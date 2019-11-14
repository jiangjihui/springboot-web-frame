package com.jjh.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 业务基础实体
 *
 * @EntityListeners 开启jpa审计 https://www.cnblogs.com/niceyoo/p/10908647.html
 */
@ApiModel("业务基础实体")
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    /** 唯一编号（ID）*/
    @ApiModelProperty("唯一编号（ID）")
    @Id
    @Column(length = 40)
    private String id = UUID.randomUUID().toString();

    /** 创建者*/
    @ApiModelProperty("创建者")
    @Column(length = 40)
    private String createBy;

    /** 创建日期（yyyy-MM-dd HH:mm:ss）*/
    @ApiModelProperty("创建日期（yyyy-MM-dd HH:mm:ss）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @CreatedDate
    private Date createTime;

    /** 更新者*/
    @ApiModelProperty("更新者")
    @Column(length = 40)
    private String updateBy;

    /** 更新日期（yyyy-MM-dd HH:mm:ss）*/
    @ApiModelProperty("更新日期（yyyy-MM-dd HH:mm:ss）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @LastModifiedDate
    private Date updateTime;


}
