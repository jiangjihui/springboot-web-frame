package com.jjh.business.demo.customer.repository;

import com.jjh.business.demo.customer.domain.Customer;
import com.jjh.framework.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 客户 数据层
 *
 * @author jjh
 * @date 2019/11/18
 */
@Repository
public interface CustomerRepository extends BaseRepository<Customer, String> {

}
