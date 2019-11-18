package com.jjh.framework.jpa;


import com.jjh.common.web.form.PageRequestForm;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * 自定义jpa扩展类
 * https://blog.csdn.net/biboheart/article/details/80666617
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean       //注解 不实例化该类，否则启动报错
public interface BaseRepository<T,ID> extends JpaRepository<T, ID>,JpaSpecificationExecutor<T> {

    /**
     * 分页获取列表
     * @param form  分页参数
     * @return
     */
    List<T> list(PageRequestForm form);

    /**
     * 分页获取列表
     * @param form  分页参数
     * @param entity  查询条件
     * @return
     */
    List<T> list(PageRequestForm form,T entity);

    /**
     * 分页获取列表
     * @param form  分页参数
     * @return
     */
    Page<T> find(PageRequestForm form);

    /**
     * 执行SQL查询
     * @param sql
     * @param resultClass
     * @return
     */
    List listBySQL(String sql, Class resultClass);

    /**
     * 执行SQL查询
     * @param sql
     * @return
     */
    List<Object[]> listBySQL(String sql);

    /**
     * 批量删除对象
     * @param ids   对象ID数组
     */
    void deleteMany(ID[] ids);

}
