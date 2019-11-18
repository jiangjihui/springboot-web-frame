package com.jjh.business.demo.article.service.impl;

import com.jjh.common.util.JpaVOUtils;
import com.jjh.business.demo.article.vo.UserInfoVO;
import com.jjh.common.util.PojoUtils;
import com.jjh.common.web.form.PageRequestForm;
import com.jjh.business.demo.article.dao.ArticleMapper;
import com.jjh.common.exception.BusinessException;
import com.jjh.business.demo.article.model.Article;
import com.jjh.business.demo.article.service.IArticleService;
import com.jjh.common.web.form.SimpleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 文章 服务层实现
 */
@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取列表
     * @param form
     * @return
     */
    @Override
    public Page<Article> list(PageRequestForm form) {
        return articleMapper.find(form);
    }

    /**
     * 保存
     * @param article
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Article article) {
        if (StringUtils.isEmpty(article.getTitle())) {
            throw new BusinessException("标题不能为空");
        }
        articleMapper.save(article);
    }

    /**
     * 查找
     * @param id 主键
     */
    @Override
    public Article get(String id) {
        return articleMapper.findById(id).get();
    }

    /**
     * 更新
     * @param article
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Article article) {
        Article entity = articleMapper.findById(article.getId()).get();
        if (entity == null) {
            throw new BusinessException("未找到待更新的对象");
        }
        PojoUtils.copyPropertiesIgnoreNull(article,entity);
        articleMapper.save(entity);
    }

    /**
     * 删除
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(SimpleForm form) {
        if (StringUtils.isEmpty(form.getId())) {
            throw new BusinessException("id不能为空");
        }
        articleMapper.deleteById(form.getId());
    }

    @Override
    public void sqlFind(SimpleForm form) {
        List<Object[]> objects = articleMapper.listBySQL("SELECT\n" +
                "A.createTime,\n" +
                "A.username\n" +
                " from sys_user where username='admin'");

        List<UserInfoVO> entity = JpaVOUtils.castEntity(objects, UserInfoVO.class);
        for (UserInfoVO userInfoVO : entity) {
            System.out.println(userInfoVO.toString());
        }
    }

    @Override
    public List<Article> find(Article entity) {
        // OR条件
//        ExampleMatcher matcher = ExampleMatcher.matchingAny();
        Example<Article> example = Example.of(entity);
        List<Article> all = articleMapper.findAll(example);
        return all;
    }
}
