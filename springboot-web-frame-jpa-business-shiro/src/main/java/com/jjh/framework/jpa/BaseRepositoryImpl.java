package com.jjh.framework.jpa;

import com.jjh.common.exception.BusinessException;
import com.jjh.common.web.form.PageRequestForm;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final EntityManager em;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

    public static Pageable buildPage(PageRequestForm form) {
        return PageRequest.of(form.getPageNum(), form.getPageSize());
    }

    /**
     * 分页获取列表
     * @param form  分页参数
     * @return
     */
    @Override
    public List<T> list(PageRequestForm<T> form) {
        Page<T> all = null;
        if (form.getFilter() == null) {
            all = super.findAll(buildPage(form));
        }
        else {
            // 按条件查询
            Example<T> example = Example.of(form.getFilter());
            all = super.findAll(example, buildPage(form));
        }
        // 获取查询到的总数
        form.setTotal(all.getTotalElements());
        // 返回所有查询到的数据
        return all.getContent();
    }

    /**
     * 分页获取列表
     * @param form  分页参数
     * @param entity  查询条件
     * @return
     */
    @Override
    public List<T> list(PageRequestForm form, T entity) {
        Example<T> example = Example.of(entity);
        return this.findAll(example, buildPage(form)).getContent();
    }

    /**
     * 分页获取列表
     * @param form  分页参数
     * @return
     */
    @Override
    public Page<T> find(PageRequestForm form) {
        return super.findAll(PageRequest.of(form.getPageNum(),form.getPageSize()));
    }

    /**
     * 执行SQL查询
     * @param sql
     * @return
     */
    @Override
    public List listBySQL(String sql, Class resultClass) {
        return em.createNativeQuery(sql, resultClass).getResultList();
    }

    /**
     * 执行SQL查询
     * @param sql
     * @return
     */
    @Override
    public List<Object[]> listBySQL(String sql) {
        return em.createNativeQuery(sql).getResultList();
    }

    /**
     * 批量删除对象
     * @param ids   对象ID数组
     */
    @Override
    @Transactional
    public void deleteMany(ID[] ids) {
        if (ids == null || ids.length == 0) {
            throw new BusinessException("待删除对象不能为空");
        }
        for (ID id : ids) {
            if (StringUtils.isEmpty(id)) {
                throw new BusinessException("待删除对象ID不能为空");
            }
            try {
                super.deleteById(id);
            } catch (EmptyResultDataAccessException e) {
                throw new BusinessException("待删除对象未找到");
            }
        }
    }
}
