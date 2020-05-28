package com.jjh.business.demo.customer.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jjh.business.demo.customer.domain.Customer;
import com.jjh.business.demo.customer.service.CustomerService;
import com.jjh.business.demo.customer.repository.CustomerRepository;
import com.jjh.common.exception.BusinessException;
import com.jjh.common.util.PojoUtils;
import com.jjh.common.web.form.PageRequestForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 客户 服务层实现
 *
 * @author jjh
 * @date 2019/11/18
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerRepository customerRepository;


    /**
     * 查询客户列表
     *
     * @param form 查询条件
     * @return 客户集合
     */
    @Override
    public List<Customer> list(PageRequestForm<Customer> form) {
        return customerRepository.list(form);
    }

    /**
     * 新增客户对象
     *
     * @param entity 待新增对象
     * @return 客户对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Customer add(Customer entity) {
        customerRepository.save(entity);
        return entity;
    }

    /**
     * 更新客户对象
     *
     * @param entity 待更新对象
     * @return 客户对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Customer update(Customer entity) {
        Customer oldEntity = customerRepository.findById(entity.getId()).get();
        if (Objects.isNull(oldEntity)) {
            throw new BusinessException("对象不存在，请检查");
        }
        //对象属性复制
        PojoUtils.copyPropertiesIgnoreNull(entity, oldEntity);
        customerRepository.save(oldEntity);
        return entity;
    }

    /**
     * 删除客户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean del(String ids) {
        customerRepository.deleteMany(ids.split(","));
        return true;
    }

}
