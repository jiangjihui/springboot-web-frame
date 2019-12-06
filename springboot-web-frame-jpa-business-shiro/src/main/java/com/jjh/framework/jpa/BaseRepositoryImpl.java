package com.jjh.framework.jpa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jjh.common.exception.BusinessException;
import com.jjh.common.web.form.PageRequestForm;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        return this.listMatcher(form, null);
    }

    /**
     * 分页获取列表
     * @param form  分页参数
     * @param matcher  匹配器
     * @return
     */
    @Override
    public List<T> listMatcher(PageRequestForm<T> form, ExampleMatcher matcher) {
        Page<T> all = null;
        if (form.getFilter() == null) {
            all = super.findAll(buildPage(form));
        }
        else {
            // 按条件查询
            Example<T> example = null;
            if (matcher == null) {
                example = Example.of(form.getFilter());
            }
            else {
                example = Example.of(form.getFilter(), matcher);
            }
            all = super.findAll(example, buildPage(form));
        }
        // 获取查询到的总数
        form.setTotal(all.getTotalElements());
        // 返回所有查询到的数据
        return all.getContent();
    }

    /**
     * 分页获取列表
     * 范围查询（时间）
     * @param form  分页参数
     * @return
     */
    @Override
    public List<T> listSpecific(PageRequestForm form) {
        Specification specification = new Specification<Object>() {
            @Override
            public Predicate toPredicate(Root<Object> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                JSONObject filter = JSON.parseObject(JSON.toJSONString(form.getFilter()));
                Set<String> keySet = filter.keySet();
                String paramKey = null;
                for (String key : keySet) {
                    if (key.endsWith("_WithStartDate")) {
                        paramKey = key.replace("_WithStartDate", "");
                        predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get(paramKey), filter.getDate(key)));
                    }
                    else if (key.endsWith("_WithEndDate")) {
                        paramKey = key.replace("_WithEndDate", "");
                        predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(paramKey), filter.getDate(key)));
                    }
                    else {
                        if (filter.get(key) instanceof Integer){
                            predicateList.add(criteriaBuilder.equal(root.get(key), filter.getInteger(key)));
                        }
                        else if (filter.get(key) instanceof Long) {
                            predicateList.add(criteriaBuilder.equal(root.get(key), filter.getLong(key)));
                        }
                        else if (filter.get(key) instanceof Double) {
                            predicateList.add(criteriaBuilder.equal(root.get(key), filter.getDouble(key)));
                        }
                        else {
                            if (key.endsWith("_WithLike")){
                                paramKey = key.replace("_WithLike", "");
                                predicateList.add(criteriaBuilder.like(root.get(paramKey), filter.getString(key) + "%"));
                            }
                            else {
                                predicateList.add(criteriaBuilder.equal(root.get(key), filter.getString(key)));
                            }
                        }
                    }
                }

                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
//                return criteriaBuilder.and();
                return null;
            }
        };

        Page all = this.findAll(specification, BaseRepositoryImpl.buildPage(form));
        form.setTotal(all.getTotalElements());
        return all.getContent();
    }

    /**
     * 分页获取列表
     * 自定义范围查询
     * @param form  分页参数
     * @param specification  查询匹配
     * @return
     */
    @Override
    public List<T> listSpecific(PageRequestForm form, Specification specification) {
        Page all = this.findAll(specification, buildPage(form));
        form.setTotal(all.getTotalElements());
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
