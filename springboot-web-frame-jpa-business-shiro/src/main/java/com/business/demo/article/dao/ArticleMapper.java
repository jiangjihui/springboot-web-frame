package com.business.demo.article.dao;

import com.framework.jpa.BaseRepository;
import com.business.demo.article.model.Article;
import org.springframework.stereotype.Repository;

/**
 * 文章 数据层
 */
@Repository
public interface ArticleMapper extends BaseRepository<Article,String> {


}
