package com.jjh.business.demo.customer.service;

import com.jjh.business.demo.customer.domain.Customer;
import com.jjh.common.web.form.PageRequestForm;
import com.jjh.common.web.form.SimpleForm;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户 服务层
 *
 * @author jjh
 * @date 2019/11/18
 */
public interface CustomerService {

    /**
     * 查询客户列表
     *
     * @param form 查询条件
     * @return 客户集合
     */
    public List<Customer> list(PageRequestForm<Customer> form);

    /**
     * 新增客户对象
     *
     * @param entity 待新增对象
     * @return 客户对象
     */
    @Transactional(rollbackFor = Exception.class)
    public Customer add(Customer entity);


    /**
     * 更新客户对象
     *
     * @param entity 待更新对象
     * @return 客户对象
     */
    @Transactional(rollbackFor = Exception.class)
    public Customer update(Customer entity);


    /**
     * 删除客户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean del(String ids);
}
