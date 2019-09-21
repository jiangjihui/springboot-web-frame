package com.framework.jpa;


import com.common.web.form.PageRequestForm;
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

    List<T> list(PageRequestForm form);

    Page<T> find(PageRequestForm form);

    List<Object[]> listBySQL(String sql);

}
