package com.jjh.business.demo.article.service;

import com.jjh.common.web.form.PageRequestForm;
import com.jjh.business.demo.article.model.Article;
import com.jjh.common.web.form.SimpleForm;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IArticleService {

    /**
     * 获取列表
     * @param form
     * @return
     */
    Page<Article> list(PageRequestForm form);

    /**
     * 保存
     * @param article
     */
    void save(Article article);

    /**
     * 查找
     * @param id 主键
     */
    Article get(String id);

    /**
     * 更新
     * @param article
     */
    void update(Article article);

    /**
     * 删除
     * @param form
     */
    void delete(SimpleForm form);


    /**
     * 删除
     * @param form
     */
    void sqlFind(SimpleForm form);

    List<Article> find(Article entity);
}
