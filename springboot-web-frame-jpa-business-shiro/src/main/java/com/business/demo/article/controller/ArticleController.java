package com.business.demo.article.controller;

import com.common.web.controller.BaseController;
import com.common.web.form.PageRequestForm;
import com.common.web.form.SimpleForm;
import com.common.web.form.SimpleResponseForm;
import com.business.demo.article.model.Article;
import com.business.demo.article.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章 接口
 */
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Autowired
    IArticleService articleService;

    /**
     * 获取列表
     * @param form
     * @return
     */
    @GetMapping("/list")
    public Object list(PageRequestForm form) {
        Page<Article> result = articleService.find(form);
        return page(result);
    }

    /**
     * 保存
     * @param article
     * @return
     */
    @GetMapping("/add")
    public Object add(Article article) {
        articleService.save(article);
        return success("OK");
    }

    /**
     * 查找
     * @param id 主键
     */
    // https://blog.csdn.net/dreamhai/article/details/80642010
    @Cacheable(value = "article",key = "#id")
    @GetMapping("/get")
    public Object add(String id) {
        Article article = articleService.get(id);

        return success(article);
    }

    /**
     * 更新
     * @param article
     * @return
     */
    @GetMapping("/update")
    public Object update(Article article) {
        articleService.update(article);
        return success("OK");
    }

    /**
     * 删除
     * @param form
     * @return
     */
    @GetMapping("/delete")
    public Object delete(SimpleForm form) {
        articleService.delete(form);
        return success("OK");
    }

}
