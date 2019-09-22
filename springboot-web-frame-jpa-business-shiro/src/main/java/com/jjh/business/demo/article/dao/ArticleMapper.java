package com.jjh.business.demo.article.dao;

import com.jjh.framework.jpa.BaseRepository;
import com.jjh.business.demo.article.model.Article;
import org.springframework.stereotype.Repository;

/**
 * 文章 数据层
 */
@Repository
public interface ArticleMapper extends BaseRepository<Article,String> {


}
