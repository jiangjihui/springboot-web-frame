package com.business.demo.article.model;

import com.common.model.AuditBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.business.demo.article.model.Article.TABLE_NAME;

@Data
@Entity
@Table(name = TABLE_NAME)
public class Article extends AuditBaseEntity {

    public static final String TABLE_NAME = TABLE_PREFIX+"article";

    @Column(length = 40)
    private String title;

    @Column(length = 250)
    private String content;

}
